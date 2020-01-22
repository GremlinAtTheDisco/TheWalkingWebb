package walking_web.services;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import walking_web.models.Customer;
import walking_web.repositories.CustomerRepository;
import walking_web.repositories.ShoeRepository;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    ShoeRepository shoeRepo;

    Customer customer;
    boolean isLoggedIn;

    Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public Customer login(String username) {
        List<Customer> customerLoggedInList = customerRepo.findByUsername(username);
        if (customerLoggedInList.size() > 0) {
            customer = customerLoggedInList.get(0);
            isLoggedIn = true;
            return customer;
        } else {
            return null;
        }

    }

    public Customer addCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

}
