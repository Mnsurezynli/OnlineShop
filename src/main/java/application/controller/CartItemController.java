package application.controller;

import application.Dto.CartItemDto;
import application.Services.ICartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    private final ICartItemService cartItemService;

    @Autowired
    public CartItemController(ICartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/add")
    public ResponseEntity<CartItemDto> addCartItem(@RequestParam Long cartId, @RequestParam Long productId, @RequestParam int quantity) {
        CartItemDto cartItemDto = cartItemService.addCartItem(cartId, productId, quantity);
        return new ResponseEntity<>(cartItemDto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CartItemDto> updateCartItem(@RequestParam Long cartId, @RequestParam Long productId, @RequestParam int newQuantity) {
        CartItemDto cartItemDto = cartItemService.updateCartItem(cartId, productId, newQuantity);
        if (cartItemDto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cartItemDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeCartItem(@RequestParam Long cartId, @RequestParam Long productId) {
        cartItemService.removeCartItem(cartId, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItemDto>> getCartItems(@RequestParam Long cartId) {
        List<CartItemDto> cartItems = cartItemService.getCartItems(cartId);
        return ResponseEntity.ok(cartItems);
    }
}
