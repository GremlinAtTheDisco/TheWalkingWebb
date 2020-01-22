package walking_web.webb;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import walking_web.models.Order;
import walking_web.models.Shoe;
import walking_web.services.OrderService;
import walking_web.services.ShoeService;
import walking_web.services.StaffService;

@Controller
public class AdminWebController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private ShoeService shoeService;

    @Autowired
    private OrderService orderService;

    Logger logger = LoggerFactory.getLogger(AdminWebController.class);

    @GetMapping("/admin/login")
    public String loginForm(Model model) {
        model.addAttribute("loginFormBean", new LoginFormBean());
        model.addAttribute("message", "Please login:");
        return "login-admin";
    }

    @PostMapping("/admin/login")
    public String loginSubmit(@ModelAttribute LoginFormBean loginFormBean, Model model) {
        if (staffService.login(loginFormBean.getUsername())) {
            model.addAttribute("username", loginFormBean.getUsername());
            return "admin-main";
        } else {
            return "login-admin";
        }
    }

    @GetMapping("/admin/main")
    public String mainForm() {
        return "admin-main";
    }

    @PostMapping("/admin/main")
    public String main() {
        return "admin-main";
    }

    @GetMapping("/admin/orders")
    public String showOrders(Model model) {
        model.addAttribute("listAllOrders", orderService.getAllOrders());
        return "orders";
    }

    @GetMapping("/admin/order/viewOrder/{id}")
    public String showOrderDetails(@PathVariable Long id, Model model) {
        Order order = orderService.getOrder(id).get();
        model.addAttribute("orderlines", order.getOrderlines());
        model.addAttribute("order", orderService.getOrder(id).get());
        return "expedite";
    }

    @PostMapping("/admin/order/viewOrder/shipOrder/{id}")
    public String shipOrder(@PathVariable Long id) {
        Order order = orderService.getOrder(id).get();
        order.setStatusDelivered(true);
        orderService.editOrder(order);
        return "redirect:/admin/orders";
    }

    @PostMapping("/admin/order/viewOrder/deleteOrder/{id}")
    public String deleteOrder(@PathVariable Long id) {
        Order order = orderService.getOrder(id).get();
        if (order.isStatusDelivered()) {
            System.out.println("Nope");
            return "redirect:/admin/orders";
        } else {
            orderService.removeOrder(order);
        }
        return "redirect:/admin/orders";
    }

    @GetMapping("/admin/products")
    public String showShoes(Model model) {
        model.addAttribute("listAllShoes", shoeService.listAllShoes());
        return "shoes";
    }

    @GetMapping("/admin/products/create")
    public String createShoeForm(Model model) {
        Shoe shoe = new Shoe();
        model.addAttribute("shoe", shoe);
        return "new-shoe";
    }

    @PostMapping("/admin/products/create")
    public String createShoe(@ModelAttribute("shoe") Shoe shoe) {
        shoeService.addShoe(shoe);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/edit/{id}")
    public ModelAndView editShoeForm(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("edit-shoe");
        Optional<Shoe> shoe = shoeService.findById(id);
        mav.addObject("shoe", shoe);
        return mav;

    }

    @RequestMapping("/admin/products/delete/{id}")
    public String removeShoe(@PathVariable Long id) {
        shoeService.removeShoeById(id);
        return "redirect:/admin/products";

    }

}
