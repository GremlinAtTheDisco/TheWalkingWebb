package walking_web.services;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import walking_web.models.Customer;
import walking_web.models.Order;
import walking_web.models.Orderline;
import walking_web.repositories.OrderLineRepository;
import walking_web.repositories.OrderRepository;

@Service
@Transactional
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepo;
    
    @Autowired
    private OrderLineRepository orderlineRepo;
    
    Logger logger = LoggerFactory.getLogger(OrderService.class);
    
    public Order createOrder(Order order) {
        return orderRepo.save(order);
    }
    
    public Order editOrder(Order order) {
        return orderRepo.save(order);
    }
    
    public void removeOrder(Order order) {
        orderRepo.delete(order);
    }
    
    public Orderline getOrderline(Long id) {
        return orderlineRepo.getOne(id);
    }
    
    public Optional<Order> getOrder(Long id) {
        return orderRepo.findById(id);
    }
    
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }
    
    public Customer getCustomerForOrder(Long id) {
        Order order = getOrder(id).get();
        return order.getCustomer();
    }
}
