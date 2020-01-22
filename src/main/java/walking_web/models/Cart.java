package walking_web.models;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart {

    private Customer customer;

    private List<Orderline> orderlines = new ArrayList<>();

    public void removeOrderline(Orderline orderline) {
        orderlines.remove(orderline);
    }

    public Orderline findOrderline(int index) {
        return orderlines.get(index);
    }

    public List<Orderline> getOrderlines() {
        return orderlines;
    }

    public void setOrderlines(List<Orderline> orderlines) {
        this.orderlines = orderlines;
    }

    public Double sumOfCart() {
        double sum = 0;

        for (Orderline orderline : orderlines) {
            Shoe shoe = orderline.getShoe();
            sum = shoe.getPrice() + sum;
        }
        return sum;
    }

    public int numberOfItemsInCart() {
        return orderlines.size();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Cart{" + "customer=" + customer + '}';
    }

}
