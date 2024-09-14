package application.Services.impl;

import application.Dto.CartDto;
import application.Dto.CartItemDto;
import application.Dto.ProductDto;
import application.Repository.CartItemRepository;
import application.Repository.CartRepository;
import application.Repository.ProductRepository;
import application.Services.ICartService;
import application.exception.ResourceNotFoundException;
import application.model.Cart;
import application.model.CartItem;
import application.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartServiceImpl implements ICartService {

    private CartRepository cartRepository;

    private ProductRepository productRepository;

    private CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Transactional
    @Override
    public void addProductToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        Product product = productRepository.findById(productId)
          .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        CartItem existingCartItem = cartItemRepository.findByCartAndProduct(cart, product);
        if (existingCartItem != null) {
            existingCartItem.setNumber(existingCartItem.getNumber() + quantity);
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setNumber(quantity);
            cartItemRepository.save(newCartItem);
        }

    }


    @Override
    public CartDto viewCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        return convertToDto(cart);
    }

    @Transactional
    @Override
    public void updateCartItem(Long cartId, Long productId, int newQuantity) {

        Cart cart = cartRepository.findById(cartId)
          .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        Product product = productRepository.findById(productId)
           .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product);
        if (cartItem != null) {
            if (newQuantity <= 0) {
                cartItemRepository.delete(cartItem);
            } else {
                cartItem.setNumber(newQuantity);
                cartItemRepository.save(cartItem);
            }
        } else {
                throw new ResourceNotFoundException("CartItem not found");
        }


    }

    @Transactional
    @Override
    public void removeProductFromCart(Long cartId, Long productId) {
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

    public CartDto convertToDto(Cart cart) {

        if (cart == null) {
            return null;
        }
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setUser(cart.getUser());
        cartDto.setCartItems(cart.getCartItems());
        return cartDto;
    }

    public Cart convertToEntity(CartDto cartDto) {
        if (cartDto == null) {
            return null;
        }
        Cart cart = new Cart();
        cart.setId(cartDto.getId());
        cart.setUser(cartDto.getUser());
        cart.setCartItems(cartDto.getCartItems());
        return cart;
    }

}
