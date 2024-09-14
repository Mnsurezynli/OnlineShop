package application.controller;

import application.Dto.CartDto;
import application.Services.ICartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
 @RequestMapping("/api/cart")
public class CartController {

    private ICartService iCartService;

    public CartController(ICartService iCartService) {
        this.iCartService = iCartService;
    }


    @PostMapping("/{cartId}/products/{productId}")
    public ResponseEntity<String> addProductToCart(@PathVariable Long cartId, @PathVariable Long productId, @RequestParam  int quantity){
        iCartService.addProductToCart(cartId, productId, quantity);
        return new ResponseEntity<>("the product add to cart", HttpStatus.OK);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> viewCart(@PathVariable Long cartId) {
        CartDto cartDto = iCartService.viewCart(cartId);
        return new ResponseEntity<>(cartDto,HttpStatus.OK);
    }


    @PutMapping("/{cartId}/products/{productId}")
    public ResponseEntity<String> updateCartItem(@PathVariable Long cartId, @PathVariable Long productId, @RequestParam int newQuantity) {
        iCartService.updateCartItem(cartId, productId, newQuantity);
        return new ResponseEntity<>("updated cartItem ",HttpStatus.OK);
    }

    // حذف محصول از سبد خرید
    @DeleteMapping("/{cartId}/products/{productId}")
    public ResponseEntity<String> removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        iCartService.removeProductFromCart(cartId, productId);
      return new ResponseEntity<>("the product removed from the cart",HttpStatus.OK);
    }
}
