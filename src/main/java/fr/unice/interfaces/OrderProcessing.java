package fr.unice.interfaces;

import fr.unice.Exceptions.BadUserInformationsException;
import fr.unice.Exceptions.ErrorPaymentOrderExcpetion;
import fr.unice.Exceptions.MissOrderInformationException;
import fr.unice.Exceptions.StoreNotFoundException;
import fr.unice.enums.OrderStatus;
import fr.unice.model.Bill;
import fr.unice.model.Order;
import fr.unice.model.OrderLine;
import fr.unice.model.cooks.Cook;

import java.util.Optional;

public interface OrderProcessing {
    boolean trySelectPickupTime(Order order, int year, int month, int day, int hour, int minute) throws StoreNotFoundException;
    void validateOrder(Order order) throws MissOrderInformationException, ErrorPaymentOrderExcpetion;
    double processPrice(Order order);
    Bill generateBill(Order order);
    boolean tryAddOrderLineWithStockManagement(Order order, OrderLine orderLine);
    void addOrderLine(Order order ,OrderLine orderLine);
    boolean cancelOrder(Order order);
    void associateUserInfoInOrder(Order order, String email, String phoneNumber) throws BadUserInformationsException;
    void associateLoyalMemberInOrder(Order order, String email, String password) throws BadUserInformationsException;
}
