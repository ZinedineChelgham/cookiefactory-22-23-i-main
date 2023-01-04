package fr.unice.components;

import fr.unice.Exceptions.OrderDeliveredErrorException;
import fr.unice.Exceptions.OrderIdErrorException;
import fr.unice.Exceptions.OrderNotFoundException;
import fr.unice.Exceptions.OrderNotPreparedException;
import fr.unice.enums.OrderStatus;
import fr.unice.interfaces.OrderRetriever;
import fr.unice.interfaces.OrderUtilities;
import fr.unice.interfaces.SmsService;
import fr.unice.model.Order;
import fr.unice.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class OrderRetrieverController implements SmsService, OrderRetriever {

    static final String MESSAGE = " Hello, this is a remind, dont forget to come take your order";

    private OrderRepository orderRepository;
    OrderUtilities orderUtilities;
    TGTGNotificationController tgtgNotificationController;

    @Autowired
    public OrderRetrieverController(OrderRepository repository, OrderUtilities orderUtilities, TGTGNotificationController tgtgNotificationController){ // ici prendre l'order du store
        orderRepository = repository;
        this.orderUtilities = orderUtilities;
        this.tgtgNotificationController = tgtgNotificationController;
    }
    /**
     * check if an inputed id match with the order id
     * stop the timer for this order
     * @param id id of an order given by client
     */
    @Override
    public void deliverOrderById(int id) throws OrderNotFoundException, OrderNotPreparedException {
        Optional<Order> orderTrouve = orderRepository.findById(id);
        if(orderTrouve.isEmpty()){
            throw new OrderNotFoundException();
        }else{
            if(orderTrouve.get().getStatus().equals(OrderStatus.READY)){
                orderUtilities.updateStatus(orderTrouve.get(), OrderStatus.DELIVERED);
                orderRepository.save(orderTrouve.get(),id);
            }
            else
                throw new OrderNotPreparedException();

        }
    }

    /**
     * @param idOrder the id of an giver order
     * @param time LocalDateTime given
     */
    @Override
    public void manageTime(int idOrder, LocalDateTime time) throws OrderIdErrorException, OrderDeliveredErrorException {
        if(orderRepository.findById(idOrder).isPresent()){
            if(orderRepository.findById(idOrder).get().getStatus().equals(OrderStatus.READY)){
                orderRepository.findById(idOrder).get().setSendMessage(checkToSendMessagePing(orderRepository.findById(idOrder).get(),time));
            }
            else
                throw new OrderDeliveredErrorException();
        }else
            throw new OrderIdErrorException();
    }

    /**
     * compare les minutes du temps de retrait d'une commande + 5 min au minute du temps passer en parametre.
     * @return true or false
     */
    private boolean overFive(Order order, LocalDateTime time){
         return order.getPickupTime().getMinute() + 5 == time.getMinute();
    }
    private boolean overOne(Order order, LocalDateTime time){
        return order.getPickupTime().getHour() + 1 == time.getHour();
    }

    private boolean checkToSendMessagePing(Order order, LocalDateTime time){
        if (overFive(order,time)){
            return true;
        }
        else return overOne(order,time);
    }
}
