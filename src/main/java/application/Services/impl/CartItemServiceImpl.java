package application.Services.impl;

import application.Dto.CartDto;
import application.Dto.CartItemDto;
import application.Repository.CartItemRepository;
import application.Repository.CartRepository;
import application.Repository.ProductRepository;
import application.Services.ICartItemService;
import application.exception.ResourceNotFoundException;
import application.model.Cart;
import application.model.CartItem;
import application.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements ICartItemService {

    private CartItemRepository cartItemRepository;


    private CartRepository cartRepository;

    private ProductRepository productRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }
    @Transactional
    @Override
    public CartItemDto addCartItem(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        Product product = productRepository.findById(productId)
          .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        CartItem existingCartItem = cartItemRepository.findByCartAndProduct(cart, product);
        CartItem cartItem = null;
        if (existingCartItem != null) {
            existingCartItem.setNumber(existingCartItem.getNumber() + quantity);
            cartItemRepository.save(existingCartItem);
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setNumber(quantity);
            cartItemRepository.save(cartItem);
        }

        return convertToDto(cartItem);
    }
    @Transactional
    @Override
    public CartItemDto updateCartItem(Long cartId, Long productId, int newQuantity) {
        Cart cart = cartRepository.findById(cartId)
        .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        Product product = productRepository.findById(productId)
         .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product);
        if (cartItem != null) {
            if (newQuantity <= 0) {
                cartItemRepository.delete(cartItem);
                return null;
            } else {
                cartItem.setNumber(newQuantity);
                cartItemRepository.save(cartItem);
            }
        } else {
              throw new ResourceNotFoundException("CartItem not found");
        }

        return convertToDto(cartItem);
    }
    @Transactional
    @Override
    public void removeCartItem(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
          .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product);
        if (cartItem != null) {
            cartItemRepository.delete(cartItem);
        } else {
              throw new ResourceNotFoundException("CartItem not found");
        }
    }

    @Override
    public List<CartItemDto> getCartItems(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with ID " + cartId + " not found"));
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        return cartItems.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CartItemDto convertToDto(CartItem cartItem) {

        if (cartItem == null) {
            return null;
        }
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setId(cartItem.getId());
        cartItemDto.setNumber(cartItem.getNumber());
        return cartItemDto;
    }

    public CartItem convertToEntity(CartItemDto cartItemDto) {
        if (cartItemDto == null) {
            return null;
        }
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemDto.getId());
        cartItem.setNumber(cartItemDto.getNumber());
        return cartItem;
    }

}
