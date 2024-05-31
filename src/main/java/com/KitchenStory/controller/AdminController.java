package com.KitchenStory.controller;

import com.KitchenStory.entity.FoodItem;
import com.KitchenStory.entity.User;
import com.KitchenStory.service.FoodItemService;
import com.KitchenStory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private FoodItemService foodItemService;

    private static final String UPLOADED_FOLDER = "src/main/resources/static/images/";

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        model.addAttribute("username", username);

        List<FoodItem> foodItems = foodItemService.getAllFoodItems();
        model.addAttribute("foodItems", foodItems);

        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);

        return "admin-dashboard";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        User admin = userService.findByUsername(username);
        if (admin != null && passwordEncoder.matches(currentPassword, admin.getPassword())) {
            admin.setPassword(passwordEncoder.encode(newPassword));
            userService.saveUser(admin);
            model.addAttribute("message", "Password changed successfully!");
        } else {
            model.addAttribute("message", "Current password is incorrect!");
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/add-item")
    public String addItem(@RequestParam("itemName") String itemName,
                          @RequestParam("itemPrice") Double itemPrice,
                          @RequestParam("itemImage") MultipartFile itemImage, Model model) {
        if (itemImage.isEmpty()) {
            model.addAttribute("message", "Please select an image file to upload.");
            return "redirect:/admin/dashboard";
        }

        try {
            // Save the file to the static directory
            byte[] bytes = itemImage.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + itemImage.getOriginalFilename());
            Files.write(path, bytes);

            // Store the relative URL to the image in the database
            String imageUrl = "/images/" + itemImage.getOriginalFilename();
            FoodItem newItem = new FoodItem(itemName, itemPrice, imageUrl);
            foodItemService.saveFoodItem(newItem);

            model.addAttribute("message", "Item added successfully!");

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Failed to upload image.");
            return "redirect:/admin/dashboard";
        }

        return "redirect:/admin/dashboard";
    }

    @PostMapping("/delete-item")
    public String deleteItem(@RequestParam("itemId") Long itemId, Model model) {
        foodItemService.deleteFoodItemById(itemId);
        model.addAttribute("message", "Item deleted successfully!");
        return "redirect:/admin/dashboard";
    }
}
