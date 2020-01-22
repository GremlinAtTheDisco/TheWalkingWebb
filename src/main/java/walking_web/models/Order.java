package walking_web.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long order_id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreated;

    private boolean statusDelivered = false;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ordernumber")
    private List<Orderline> orderlines = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer")
    private Customer customer;

    public Order() {
    }

    public Double sumOfOrder() {
        double sum = 0;
        for (Orderline orderline : orderlines) {
            Shoe shoe = orderline.getShoe();
            sum = shoe.getPrice() + sum;
        }

        return sum;

    }

    public int getNumberOfShoes() {
        return this.orderlines.size();
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void addOrderline(Orderline orderline) {
        orderlines.add(orderline);
    }

    public List<Orderline> getOrderlines() {
        return orderlines;
    }

    public void setOrderlines(List<Orderline> orderlines) {
        this.orderlines = orderlines;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isStatusDelivered() {
        return statusDelivered;
    }

    public void setStatusDelivered(boolean statusDelivered) {
        this.statusDelivered = statusDelivered;
    }

}
