package fr.unice.components;

import fr.unice.Exceptions.BadUserInformationsException;
import fr.unice.Exceptions.ErrorPaymentOrderExcpetion;
import fr.unice.Exceptions.MissOrderInformationException;
import fr.unice.Exceptions.StoreNotFoundException;
import fr.unice.enums.MailMessageType;
import fr.unice.enums.MailSubjectsType;
import fr.unice.enums.OrderStatus;
import fr.unice.interfaces.*;
import fr.unice.interfaces.OrderCreator;
import fr.unice.interfaces.RecipeCalculator;
import fr.unice.interfaces.StoreOrderProcessing;
import fr.unice.model.*;
import fr.unice.model.cooks.Cook;
import fr.unice.repositories.OrderRepository;
import fr.unice.services.BasicMailService;
import fr.unice.services.BasicPaymentService;
import fr.unice.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class OrderController implements OrderCreator, OrderProcessing, OrderUtilities {
    StoreOrderProcessing storeOrderProcessing;
    BasicMailService mailService;
    StoreIngredientsLocker storeIngredientsLocker;
    RecipeCalculator recipeCalculator;
    OrderRepository orderRepository;
    UserRegisterer userRegisterer;
    UserFinder userFinder;
    PaymentService paymentService;


    @Autowired
    public OrderController(StoreOrderProcessing storeOrderProcessing,
                           BasicMailService mailService,
                           RecipeCalculator recipeCalculator,
                           StoreIngredientsLocker storeIngredientsLocker,
                           OrderRepository orderRepository,
                           UserRegisterer userRegisterer,
                           UserFinder userFinder,
                           PaymentService paymentService) {
        this.storeOrderProcessing = storeOrderProcessing;
        this.mailService = mailService;
        this.storeIngredientsLocker = storeIngredientsLocker;
        this.recipeCalculator = recipeCalculator;
        this.orderRepository = orderRepository;
        this.userFinder = userFinder;
        this.userRegisterer = userRegisterer;
        this.paymentService = paymentService;
    }

    /**
     * Select a time to pickup for the order and add this order to the schedule of one cook in the store
     *
     * @param year   int
     * @param month  int
     * @param day    int
     * @param hour   int
     * @param minute int
     * @return true if the pickup time have been set, false otherwise
     */
    @Override
    public boolean trySelectPickupTime(Order order, int year, int month, int day, int hour, int minute) throws StoreNotFoundException {
        if (minute % 15 != 0)
            return false;

        LocalDateTime pickupTime = LocalDateTime.of(year, month, day, hour, minute);
        order.setPickupTime(pickupTime);

        if (order.getStore() == null)
            throw new StoreNotFoundException();


        if (storeOrderProcessing.tryAssociateOrderWithScheduler(order.getStore(), order))
            return true;

        // reset pickup time in the order
        order.setPickupTime(null);
        return false;
    }

    @Override
    public void updateStatus(Order order, OrderStatus status) {
        if (!order.getPossibleNextStatuses().get(order.getStatus()).contains(status))
            throw new IllegalArgumentException(status.name() + "is no legal next step of current status " + status.name());
        order.setStatus(status);
    }

    @Override
    public void validateOrder(Order order) throws MissOrderInformationException, ErrorPaymentOrderExcpetion {
        if (order.getBuyer() == null || order.getPickupTime() == null)
            throw new MissOrderInformationException();

        order.setPrice(processPrice(order));
        if (!paymentService.PayOrder(order.getBuyer(), order.getPrice()))
            throw new ErrorPaymentOrderExcpetion();

        order.setStatus(OrderStatus.PAYED);
        if (mailService == null)
            System.out.println("Important non fatal Mail Service is not available");
        else
            mailService.sendMail(order.getBuyer().getMail(), MailSubjectsType.ORDER_CONFIRMATION, MailMessageType.CONFIRMATION, generateBill(order));
    }

    @Override
    public double processPrice(Order order) {
        double amountWithoutTVA = 0;
        for (OrderLine orderLine :  order.getOrderLines().values()) {
            System.out.println(orderLine.getQuantity());
            amountWithoutTVA += recipeCalculator.getPrice(orderLine.getRecipe()) * orderLine.getQuantity();
        }
        // add store taxe to the amount
        double price = (amountWithoutTVA * (1 + order.getStore().getTaxRate() / 100));

        if (!(order.getBuyer() instanceof LoyalMember member)) return price;

        if (member.canUseDiscount()) {
            price *= 0.9;
            member.resetCookiesSinceLastLoyalty();
        } else {
            //sum the quantity of the order lines
            int numberOfCookieOrdered = order.getOrderLines().values().stream().mapToInt(OrderLine::getQuantity).sum();
            member.incrementCookiesNumberBy(numberOfCookieOrdered);
        }
        return price;
    }

    @Override
    public Bill generateBill(Order order) {
        return new Bill(LocalDateTime.now(), order);
    }

    /**
     * Create a new order in the list orders with the given store
     *
     * @param store where the order will be sent
     */
    public Order createOrder(Store store, int id) {
        Order order = new Order(id);
        order.setStore(store);
        orderRepository.save(order,id);
        return order;
    }

    /**
     * Add an order line in the order if there is enough available ingredients in the store
     *
     * @param order
     * @param orderLine
     * @return true if the order line was added
     */
    public boolean tryAddOrderLineWithStockManagement(Order order, OrderLine orderLine) {
        if (!storeIngredientsLocker.orderLineIngredientsCanBeLocked(order.getStore(), orderLine))
            return false;

        storeIngredientsLocker.lockOrderLineIngredients(order.getStore(), orderLine);
        order.getOrderLines().put(orderLine.getRecipe().getName(), orderLine);
        return true;
    }

    /**
     * Cancel the order if cancelable and remove the order from the cook agenda and unlock the ingredients
     * @param order
     * @return boolean
     */
    public boolean cancelOrder(Order order) {
        if (order.getStatus() != OrderStatus.PREPARATION) {
            updateStatus(order, OrderStatus.CANCELLED);
            Optional<Cook> cook = storeOrderProcessing.getCookByOrder(order);
            if (cook.isPresent()) {
                storeOrderProcessing.deleteTimeSlot(cook.get(), order);
            }
            else throw new IllegalStateException("Cook of Order " + order + "not found");

            storeIngredientsLocker.unlockOrderIngredients(order.getStore(), order);
            return true;

        } else return false;
    }

    /**
     * Add the given order line in the order line list
     * @param orderLine OrderLine
     */
    @Override
    public void addOrderLine(Order order ,OrderLine orderLine) {
        order.getOrderLines().put(orderLine.getRecipe().getName(), orderLine);
    }

    @Override
    public void associateUserInfoInOrder(Order order, String email, String phoneNumber) throws BadUserInformationsException {
        User user = userFinder.findUserByMail(email);
        if (user == null)
            user = userRegisterer.registerUser(email, phoneNumber);

        if (user.getPassword() != null)
            throw new BadUserInformationsException();
        order.setBuyer(user);
    }

    @Override
    public void associateLoyalMemberInOrder(Order order, String email, String password) throws BadUserInformationsException {
        User user = userFinder.findUserByMail(email);
        if (user == null || !user.getPassword().equals(password))
            throw new BadUserInformationsException();
        order.setBuyer(user);
    }
}
