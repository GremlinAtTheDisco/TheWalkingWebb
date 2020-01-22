
package walking_web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import walking_web.models.Orderline;

public interface OrderLineRepository extends JpaRepository<Orderline, Long> {

}
