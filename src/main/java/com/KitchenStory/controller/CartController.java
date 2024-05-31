package com.KitchenStory.controller;

import com.KitchenStory.entity.Cart;
import com.KitchenStory.entity.CartItem;
import com.KitchenStory.entity.FoodItem;
import com.KitchenStory.entity.User;
import com.KitchenStory.service.CartService;
import com.KitchenStory.service.FoodItemService;
import com.KitchenStory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private FoodItemService foodItemService;

    @Autowired
    private UserService userService;

    @GetMapping("/cart")
    public String viewCart(Model model) {
        User user = getCurrentUser();
        Cart cart = cartService.getCartByUser(user);
        List<CartItem> items = cartService.getCartItems(cart);
        double totalValue = items.stream().mapToDouble(item -> item.getQuantity() * item.getFoodItem().getPrice()).sum();
        model.addAttribute("cart", cart);
        model.addAttribute("items", items);
        model.addAttribute("totalValue", totalValue);
        return "cart";
    }

    @PostMapping("/cart/add")
    @ResponseBody
    public String addItemToCart(@RequestParam("itemId") Long itemId, @RequestParam("quantity") int quantity) {
        User user = getCurrentUser();
        FoodItem foodItem = foodItemService.getFoodItemById(itemId);
        cartService.addItemToCart(user, foodItem, quantity);
        return "{\"status\": \"success\"}";
    }

    @PostMapping("/cart/remove")
    public String removeItemFromCart(@RequestParam("cartItemId") Long cartItemId) {
        cartService.removeItemFromCart(cartItemId);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        User user = getCurrentUser();
        Cart cart = cartService.getCartByUser(user);
        List<CartItem> cartItems = cartService.getCartItems(cart);
        double totalValue = cartItems.stream().mapToDouble(item -> item.getQuantity() * item.getFoodItem().getPrice()).sum();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalValue", totalValue);
        return "checkout";
    }

    @PostMapping("/order/confirm")
    public String confirmOrder(
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("city") String city,
            @RequestParam("state") String state,
            @RequestParam("zip") String zip,
            @RequestParam("total") double total,
            @RequestParam("paymentMethod") String paymentMethod,
            @RequestParam(value = "cardNumber", required = false) String cardNumber,
            @RequestParam(value = "cardName", required = false) String cardName,
            @RequestParam(value = "expiryDate", required = false) String expiryDate,
            @RequestParam(value = "cvv", required = false) String cvv,
            Model model) {

        if ("cashOnDelivery".equals(paymentMethod)) {
            // Calculate expected delivery date
            LocalDate deliveryDate = LocalDate.now().plusDays(5);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

            // Add relevant data to the model for the success page
            model.addAttribute("fullName", fullName);
            model.addAttribute("address", address);
            model.addAttribute("total", total);
            model.addAttribute("deliveryDate", deliveryDate.format(formatter));

            return "orderSuccess";
        }

        // Handle other payment methods
        // Add relevant data to the model for the payment page
        model.addAttribute("fullName", fullName);
        model.addAttribute("address", address);
        model.addAttribute("total", total);
        model.addAttribute("paymentMethod", paymentMethod);
        model.addAttribute("cardNumber", cardNumber);
        model.addAttribute("cardName", cardName);
        model.addAttribute("expiryDate", expiryDate);
        model.addAttribute("cvv", cvv);

        return "payment";
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return userService.findByUsername(username);
    }
}
