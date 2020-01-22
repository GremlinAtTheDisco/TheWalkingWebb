package walking_web.controllers;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import walking_web.exception.MyResourceNotFoundException;
import walking_web.models.Shoe;
import walking_web.services.ShoeService;
import walking_web.services.StaffService;

@RestController
@Validated
public class AdminRestController {

    @Autowired
    ShoeService shoeService;

    @Autowired
    StaffService staffService;

    Logger logger = LoggerFactory.getLogger(AdminRestController.class);

    @GetMapping("admin/login/{username}")
    public ResponseEntity<String> login(@PathVariable("username") String username) {
        if (staffService.login(username)) {
            return ResponseEntity.accepted().body("Logged in!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not allowed");
        }
    }

    @GetMapping("admin/products/all")
    public List<Shoe> allShoes() {
        return shoeService.listAllShoes();
    }

    @GetMapping("admin/products/{id}")
    public Optional<Shoe> getShoeById(@PathVariable Long id, HttpServletResponse response) {

        if (!shoeService.findById(id).isPresent()) {
            throw new MyResourceNotFoundException("ID: " + id + " not found");
        }

        return shoeService.findById(id);

    }

    @PostMapping("admin/products/create/shoe")
    public ResponseEntity<String> createShoe(@RequestBody Shoe shoe, HttpServletResponse response) {
        shoeService.addShoe(shoe);

        return ResponseEntity.status(HttpStatus.CREATED).body("Shoe created");

    }

    @PutMapping("admin/products/edit/shoe/{id}")
    public ResponseEntity<String> editShoe(@RequestBody Shoe newShoe, @PathVariable Long id, HttpServletResponse response) {

        shoeService.findById(id).map(shoe -> {
            shoe.setPrice(newShoe.getPrice());
            return shoeService.updateShoe(shoe);

        }).orElseThrow(() -> new MyResourceNotFoundException("ID: " + id + " not found"));

        return new ResponseEntity<>("Shoe updated!", HttpStatus.OK);
    }

    @DeleteMapping("admin/products/delete/shoe/{id}")
    public ResponseEntity<String> removeShoe(@PathVariable Long id, HttpServletResponse response) {
        if (shoeService.findById(id).isPresent()) {
            shoeService.removeShoeById(id);
        } else {
            throw new MyResourceNotFoundException("ID: " + id + " not found");
        }

        return new ResponseEntity<>("Shoe removed!", HttpStatus.OK);
    }
}
