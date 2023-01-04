package fr.unice.interfaces;
import fr.unice.Exceptions.OrderNotFoundException;
import fr.unice.Exceptions.OrderNotPreparedException;

//

public interface OrderRetriever {
    void deliverOrderById(int id) throws OrderNotFoundException, OrderNotPreparedException;
}
