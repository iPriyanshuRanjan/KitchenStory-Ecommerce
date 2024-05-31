package com.KitchenStory.service;

import com.KitchenStory.entity.FoodItem;
import com.KitchenStory.repository.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodItemService {

    @Autowired
    private FoodItemRepository foodItemRepository;

    public List<FoodItem> getAllFoodItems() {
        return foodItemRepository.findAll();
    }

    public void saveFoodItem(FoodItem foodItem) {
        foodItemRepository.save(foodItem);
    }

    public void deleteFoodItemById(Long id) {
        foodItemRepository.deleteById(id);
    }

    public FoodItem getFoodItemById(Long itemId) {
        Optional<FoodItem> optionalFoodItem = foodItemRepository.findById(itemId);
        return optionalFoodItem.orElse(null);
    }
}
