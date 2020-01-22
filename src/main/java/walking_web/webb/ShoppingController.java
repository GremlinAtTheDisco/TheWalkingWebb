package walking_web.webb;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import walking_web.models.Cart;
import walking_web.models.Customer;
import walking_web.models.Order;
import walking_web.models.Orderline;
import walking_web.models.Shoe;
import walking_web.services.CustomerService;
import walking_web.services.ShoeService;
import walking_web.services.OrderService;

@Controller
public class ShoppingController {

    @Autowired
    OrderService orderService;

    @Autowired
    ShoeService shoeService;

    @Autowired
    CustomerService customerService;

    @Autowired
    Cart cart;

    ModelAndView mav;

    Logger logger = LoggerFactory.getLogger(ShoppingController.class);

    @GetMapping("/main")
    public String mainShoesForm(Model model) {
        model.addAttribute("sumOfCart", cart.sumOfCart());
        model.addAttribute("itemsInCart", cart.numberOfItemsInCart());
        model.addAttribute("shoes", shoeService.listAllShoes());
        model.addAttribute("cart", cart.getOrderlines());
        return "main";
    }

    @PostMapping("/main")
    public ModelAndView mainForm() {
        mav = new ModelAndView("/main");
        mav.addObject("sumOfCart", cart.sumOfCart());
        mav.addObject("itemsInCart", cart.numberOfItemsInCart());
        mav.addObject("cart", cart.getOrderlines());
        mav.addObject("shoes", shoeService.listAllShoes());
        return mav;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginFormBean", new LoginFormBean());
        return "login-customer";
    }

    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute LoginFormBean loginFormBean, Model model) {
        Customer customer = null;
        if ((customer = customerService.login(loginFormBean.getUsername())) != null) {
            cart.setCustomer(customer);
            model.addAttribute("username", loginFormBean.getUsername());
            System.out.println("worked");
            return "redirect:/main";
        } else {
            System.out.println("nope");
            return "login-customer";
        }
    }

    @GetMapping("/login/createCustomer")
    public String createCustomerForm(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "new-customer";
    }

    @PostMapping("/login/createCustomer")
    public String createCustomer(@ModelAttribute Customer customer) {
        customerService.addCustomer(customer);
        return "redirect:/login";
    }

    @GetMapping("/cart/addShoe/{shoe_id}")
    public String addShoeToCart(@PathVariable("shoe_id") Long id, Model model) {
        Orderline orderline = new Orderline();
        Optional<Shoe> optShoe = shoeService.findById(id);

        if (optShoe.isPresent()) {
            orderline.setShoe(optShoe.get());
            cart.getOrderlines().add(orderline);
        } else {
            return "redirect:/main";

        }
        model.addAttribute("cart", cart.getOrderlines());
        model.addAttribute("shoes", shoeService.listAllShoes());
        model.addAttribute("sumOfCart", cart.sumOfCart());
        model.addAttribute("itemsInCart", cart.numberOfItemsInCart());
        return "main";
    }

    @RequestMapping("/cart/delete/{index}")
    public String removeOrderline(@PathVariable int index, Model model) {
        Orderline item = cart.findOrderline(index);
        cart.removeOrderline(item);
        return "redirect:/main";
    }

    @GetMapping("/checkout")
    public ModelAndView checkoutForm(Model model) {
        mav = new ModelAndView("/checkout");
        mav.addObject("cart", cart.getOrderlines());
        mav.addObject("shoes", shoeService.listAllShoes());
        model.addAttribute("sumOfCart", cart.sumOfCart());
        model.addAttribute("itemsInCart", cart.numberOfItemsInCart());
        return mav;
    }

    @GetMapping("/checkout/confirmation")
    public String confirmationForm(Order order) {
        List<Orderline> lineItems = cart.getOrderlines();

        order = new Order();
        for (Orderline lineItem : lineItems) {
            order.addOrderline(lineItem);
        }
        order.setDateCreated(LocalDate.now());
        order.setCustomer(cart.getCustomer());

        orderService.createOrder(order);

        return "confirmation";
    }

    @GetMapping("/main/search")
    public String searchForm(Model model) {
        model.addAttribute("nameBean", new SearchProductBean());
        model.addAttribute("cart", cart.getOrderlines());
        model.addAttribute("shoes", shoeService.listAllShoes());
        model.addAttribute("sumOfCart", cart.sumOfCart());
        model.addAttribute("itemsInCart", cart.numberOfItemsInCart());
        return "main";
    }

    @PostMapping("/main/search")
    public String showShoeByName(@ModelAttribute SearchProductBean nameBean, Model model) {

        if (nameBean.getName().isEmpty()) {
            System.out.println("is isEmpty");
            return "main";
        } else {
            model.addAttribute("cart", cart.getOrderlines());
            model.addAttribute("shoes", shoeService.listAllShoes());
            model.addAttribute("sumOfCart", cart.sumOfCart());
            model.addAttribute("itemsInCart", cart.numberOfItemsInCart());
            model.addAttribute("shoeByName", shoeService.searchShoesByName(nameBean.getName()));
        }
        return "main";
    }

}
