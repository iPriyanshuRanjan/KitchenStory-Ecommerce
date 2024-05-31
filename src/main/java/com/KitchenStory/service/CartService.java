package com.KitchenStory.service;

import com.KitchenStory.entity.Cart;
import com.KitchenStory.entity.CartItem;
import com.KitchenStory.entity.FoodItem;
import com.KitchenStory.entity.User;
import com.KitchenStory.repository.CartItemRepository;
import com.KitchenStory.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public Cart getCartByUser(User user) {
        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(user);
            return cartRepository.save(cart);
        });
    }

    @Transactional
    public void addItemToCart(User user, FoodItem foodItem, int quantity) {
        Cart cart = getCartByUser(user);
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getFoodItem().getId().equals(foodItem.getId()))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setCart(cart);
                    newItem.setFoodItem(foodItem);
                    newItem.setQuantity(0);
                    cart.getItems().add(newItem);
                    return newItem;
                });
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItemRepository.save(cartItem);
    }

    @Transactional
    public void removeItemFromCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    public List<CartItem> getCartItems(Cart cart) {
        return cartItemRepository.findByCart(cart);
    }
}
