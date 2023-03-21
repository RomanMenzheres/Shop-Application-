package com.example.shop.controller.rest;


import com.example.shop.dto.CartItemDto;
import com.example.shop.dto.CartItemTransformer;
import com.example.shop.entity.CartItem;
import com.example.shop.entity.Product;
import com.example.shop.entity.User;
import com.example.shop.security.LoginDetails;
import com.example.shop.service.CartItemService;
import com.example.shop.service.ProductService;
import com.example.shop.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cart")
public class ShoppingCartRestController {

    private final CartItemService cartItemService;
    private final ProductService productService;

    public ShoppingCartRestController(CartItemService cartItemService,
                                      ProductService productService){
        this.cartItemService = cartItemService;
        this.productService = productService;
    }

    @PostMapping("/add/{product_id}")
    public String addToCart(@PathVariable("product_id") long product_id,
                            @AuthenticationPrincipal LoginDetails loginDetails){
        Product product = productService.readById(product_id);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setUser(loginDetails.getUser());
        cartItem.setQuantity(1);

        cartItemService.create(cartItem);

        return "The product - " + product.getName() + " was added to shopping cart!";
    }

    @PostMapping("/update/{product_id}/{quantity}")
    public String updateQuantity(@PathVariable("product_id") long product_id,
                            @PathVariable("quantity") int quantity,
                            @AuthenticationPrincipal LoginDetails loginDetails){

        cartItemService.updateQuantity(product_id, loginDetails.getUser().getId(), quantity);

        return "The quantity for product " + productService.readById(product_id).getName() + " was updated!";
    }

    @DeleteMapping("/delete/{cartItem_id}")
    public String delete(@PathVariable("cartItem_id") long cartItemId){
        cartItemService.delete(cartItemId);

        return "Cart Item with id " + cartItemId + " successfully deleted!";
    }

    @DeleteMapping("/delete/all")
    public String deleteAll(@AuthenticationPrincipal LoginDetails loginDetails){

        User user =  loginDetails.getUser();

        List<CartItem> items = cartItemService.findCartItemByUser(user);

        if(items.isEmpty()){
            return "Shopping cart of user with id " + user.getId() + " is empty!";
        }

        for(CartItem cartItem : items){
            cartItemService.delete(cartItem.getId());
        }

        return "All items from shopping cart of user with id " + user.getId() + " successfully deleted!";
    }

}
