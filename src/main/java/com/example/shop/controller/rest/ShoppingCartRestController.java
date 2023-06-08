package com.example.shop.controller.rest;

import com.example.shop.dto.CartItemDto;
import com.example.shop.dto.CartItemTransformer;
import com.example.shop.entity.CartItem;
import com.example.shop.entity.Order;
import com.example.shop.entity.Product;
import com.example.shop.entity.User;
import com.example.shop.security.LoginDetails;
import com.example.shop.service.CartItemService;
import com.example.shop.service.OrderService;
import com.example.shop.service.ProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cart")
public class ShoppingCartRestController {

    private final CartItemService cartItemService;
    private final ProductService productService;
    private final OrderService orderService;

    public ShoppingCartRestController(CartItemService cartItemService,
                                      ProductService productService,
                                      OrderService orderService){
        this.cartItemService = cartItemService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("/show/all")
    public List<CartItemDto> getAllProductsFromCart(@AuthenticationPrincipal LoginDetails loginDetails){
        User owner = loginDetails.getUser();
        Order order = orderService.findOpenOrderByUser(owner);

        return order.getProducts().stream().map(CartItemTransformer::convertToDto).toList();
    }

    @PostMapping("/add/{product_id}")
    public String addToCart(@PathVariable("product_id") long product_id,
                            @AuthenticationPrincipal LoginDetails loginDetails){
        User owner = loginDetails.getUser();
        Product product = productService.readById(product_id);
        Order order = orderService.findOpenOrderByUser(owner);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);

        if (order != null) {
            cartItem.setOrder(order);
        } else {
            order = new Order();
            order.setOwner(owner);
            order = orderService.create(order);
            cartItem.setOrder(order);
        }

        CartItem savedCartItem = cartItemService.create(cartItem);

        if (savedCartItem.getQuantity() > 1){
            return "The product - " + product.getName() +
                    " is already in the cart! The quantity of this product has been increased by 1!";
        }

        return "The product - " + product.getName() + " was added to shopping cart!";
    }

    @PostMapping("/update/{product_id}/{quantity}")
    public String updateQuantity(@PathVariable("product_id") long product_id,
                            @PathVariable("quantity") int quantity,
                            @AuthenticationPrincipal LoginDetails loginDetails){

        cartItemService.updateQuantity(product_id, orderService.findOpenOrderByUser(loginDetails.getUser()).getId(), quantity);

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

        List<CartItem> items = orderService.findOpenOrderByUser(loginDetails.getUser()).getProducts();

        if(items.isEmpty()){
            return "Shopping cart of user with id " + user.getId() + " is empty!";
        }

        for(CartItem cartItem : items){
            cartItemService.delete(cartItem.getId());
        }

        return "All items from shopping cart of user with id " + user.getId() + " successfully deleted!";
    }

}
