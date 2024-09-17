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

    @PostMapping("/create")
    public ResponseEntity<CartDto> createCart(@RequestParam Long userId) {
        CartDto cartDto = cartService.createCart(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

    @PostMapping("/{cartId}/products/{productId}")
    public ResponseEntity<Void> addProductToCart(@PathVariable Long cartId, @PathVariable Long productId, @RequestParam int quantity) {
        cartService.addProductToCart(cartId, productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<String> viewCart(@PathVariable Long cartId) {
        System.out.println("Received request to view cart with id: " + cartId);
        CartDto cartDto = cartService.viewCart(cartId);

        if (cartDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found with id: " + cartId);
        }

        return ResponseEntity.ok("Cart found: " + cartDto.toString());
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
