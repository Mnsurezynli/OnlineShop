package application.Services.impl;

import application.Dto.CartDto;
import application.Dto.CartItemDto;
import application.Repository.CartItemRepository;
import application.Repository.CartRepository;
import application.Repository.ProductRepository;
import application.Repository.UserRepository;
import application.Services.ICartService;
import application.exception.ResourceNotFoundException;
import application.model.Cart;
import application.model.CartItem;
import application.model.Product;
import application.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements ICartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    private final UserRepository userRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, CartItemRepository cartItemRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public CartDto createCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        Cart cart = new Cart();
        cart.setUser(user);
        Cart savedCart = cartRepository.save(cart);
        return convertToDto(savedCart);
    }

    @Transactional
    @Override
    public void addProductToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id " + cartId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));
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
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id " + cartId));
        System.out.println("Cart found: " + cart);
        return convertToDto(cart);
    }


    @Transactional
    @Override
    public void updateCartItem(Long cartId, Long productId, int newQuantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id " + cartId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product);
        if (cartItem != null) {
            if (newQuantity <= 0) {
                cartItemRepository.delete(cartItem);
            } else {
                cartItem.setNumber(newQuantity);
                cartItemRepository.save(cartItem);
            }
        } else {
            throw new ResourceNotFoundException("CartItem not found with cart ID " + cartId + " and product ID " + productId);
        }
    }

    @Transactional
    @Override
    public void removeProductFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id " + cartId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product);
        if (cartItem != null) {
            cartItemRepository.delete(cartItem);
        } else {
            throw new ResourceNotFoundException("CartItem not found with cart ID " + cartId + " and product ID " + productId);
        }
    }

    public CartDto convertToDto(Cart cart) {
        if (cart == null) {
            return null;
        }
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setUser(cart.getUser());
        cartDto.setCartItems(cart.getCartItems().stream()
                .map(cartItem -> new CartItemDto(cartItem.getId(),
                        cartItem.getNumber(),
                        cartItem.getProduct().getName(),
                        cartItem.getProduct().getPrice()))
                .collect(Collectors.toList()));
        return cartDto;
    }

//    public Cart convertToEntity(CartDto cartDto) {
//        if (cartDto == null) {
//            return null;
//        }
//        Cart cart = new Cart();
//        cart.setId(cartDto.getId());
//        cart.setUser(cartDto.getUser());
//        cart.setCartItems(cartDto.getCartItems().stream()
//                .map(cartItemDto -> new CartItem(cartItemDto.getId(), cartItemDto.getNumber()))
//                .collect(Collectors.toList()));
//        return cart;
//    }
}
