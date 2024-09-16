package application.controller;

import application.Dto.CartDto;
import application.Services.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final ICartService cartService;

    @Autowired
    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{cartId}/products/{productId}")
    public ResponseEntity<Void> addProductToCart(@PathVariable Long cartId, @PathVariable Long productId, @RequestParam int quantity) {
        cartService.addProductToCart(cartId, productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> viewCart(@PathVariable Long cartId) {
        CartDto cartDto = cartService.viewCart(cartId);
        return ResponseEntity.ok(cartDto);
    }

    @PutMapping("/{cartId}/products/{productId}")
    public ResponseEntity<Void> updateCartItem(@PathVariable Long cartId, @PathVariable Long productId, @RequestParam int newQuantity) {
        cartService.updateCartItem(cartId, productId, newQuantity);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/products/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        cartService.removeProductFromCart(cartId, productId);
        return ResponseEntity.noContent().build();
    }
}
