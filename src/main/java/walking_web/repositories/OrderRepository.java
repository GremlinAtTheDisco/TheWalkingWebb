

package walking_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import walking_web.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
