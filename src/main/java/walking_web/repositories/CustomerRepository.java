
package walking_web.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import walking_web.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

    List<Customer> findByUsername(String username);
}
