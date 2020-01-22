package walking_web.repositories;

import java.util.List;
import walking_web.models.Shoe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoeRepository extends JpaRepository<Shoe, Long> {
    
    public List<Shoe> findShoesByName(String name);
    

}
