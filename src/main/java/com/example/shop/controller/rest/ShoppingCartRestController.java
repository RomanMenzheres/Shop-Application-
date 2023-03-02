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

@RestController
@RequestMapping("cart")
public class ShoppingCartRestController {

    private final CartItemService cartItemService;
    private final ProductService productService;
    private final UserService userService;

    public ShoppingCartRestController(CartItemService cartItemService,
                                      ProductService productService,
                                      UserService userService){
        this.cartItemService = cartItemService;
        this.productService = productService;
        this.userService = userService;
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

}
