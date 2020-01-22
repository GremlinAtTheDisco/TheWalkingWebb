package walking_web.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import org.hibernate.annotations.Type;

@Entity
public class Shoe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long shoe_id;

    private double price;
    private int size;
    private String name;
    private String category;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] image;

    public Shoe() {
    }

    public Long getShoe_id() {
        return shoe_id;
    }

    public void setShoe_id(Long shoe_id) {
        this.shoe_id = shoe_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
