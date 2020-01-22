

package walking_web.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import walking_web.models.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long>{
    
    List<Staff> findByUsername(String username);

}


