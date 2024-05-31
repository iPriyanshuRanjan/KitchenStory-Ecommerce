package com.KitchenStory.controller;
import com.KitchenStory.entity.FoodItem;
import com.KitchenStory.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private FoodItemService foodItemService;

    @GetMapping("/")
    public String home(Model model) {
        List<FoodItem> foodItems = foodItemService.getAllFoodItems();
        model.addAttribute("foodItems", foodItems);
        return "index";
    }

    @GetMapping("/products")
    public String products(Model model) {
        List<FoodItem> foodItems = foodItemService.getAllFoodItems();
        model.addAttribute("foodItems", foodItems);
        return "products";
    }
}
