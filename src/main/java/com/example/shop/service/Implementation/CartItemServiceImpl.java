package com.example.shop.service.Implementation;

import com.example.shop.entity.CartItem;
import com.example.shop.entity.User;
import com.example.shop.repository.CartItemRepository;
import com.example.shop.service.CartItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CartItemService")
@Transactional
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartItem create(CartItem cartItem) {
        if (cartItem != null) {

            CartItem isExist = null;

            if(cartItem.getOrder().getProducts() != null) {

                isExist = cartItem.getOrder().getProducts().stream()
                        .filter(existingCartItem -> existingCartItem.getProduct().equals(cartItem.getProduct()))
                        .findFirst()
                        .orElse(null);
            }

            if(isExist != null) {
                isExist.setQuantity(isExist.getQuantity() + 1);
                return  cartItemRepository.save(isExist);
            } else {
                return cartItemRepository.save(cartItem);
            }
        }
        throw new NullPointerException("Cart Item cannot be 'null'");
    }

    @Override
    public CartItem readById(long id) {
        return cartItemRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cart Item with id " + id + " not found"));
    }

    @Override
    public CartItem update(CartItem cartItem) {
        if (cartItem != null) {
            readById(cartItem.getId());
            return cartItemRepository.save(cartItem);
        }
        throw new NullPointerException("User cannot be 'null'");
    }

    @Override
    public void updateQuantity(long productId, long orderId, int quantity) {
        cartItemRepository.updateQuantity(quantity, productId, orderId);
    }

    @Override
    public void delete(long id) {
        cartItemRepository.delete(readById(id));
    }

    @Override
    public List<CartItem> getAll() {
        return cartItemRepository.findAll();
    }

}
