package walking_web.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import walking_web.models.Shoe;
import walking_web.repositories.ShoeRepository;

@Service
public class ShoeService {

    @Autowired
    ShoeRepository shoeRepo;

    public List<Shoe> listAllShoes() {
        return shoeRepo.findAll();
    }

    public Shoe addShoe(Shoe shoe) {
        return shoeRepo.save(shoe);
    }

    public Optional<Shoe> findById(Long id) {
        return shoeRepo.findById(id);
    }

    public Shoe updateShoe(Shoe shoe) {
        return shoeRepo.save(shoe);
    }

    public void removeShoeById(Long id) {
        shoeRepo.deleteById(id);
    }

    public List<Shoe> searchShoesByName(String name) {
        return shoeRepo.findShoesByName(name);
    }

}
