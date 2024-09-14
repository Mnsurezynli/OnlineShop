package application.controller;

import application.Dto.CartItemDto;
import application.Services.ICartItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cartItem")
public class CartItemController {

    private ICartItemService iCartItemService;

    public CartItemController(ICartItemService iCartItemService) {
        this.iCartItemService = iCartItemService;
    }

     @PostMapping("/{cartId}/products/{productId}")
    public ResponseEntity<CartItemDto> addCartItem(@PathVariable Long cartId,@PathVariable Long productId,@RequestParam int quantiy){
        CartItemDto cartItemDto = iCartItemService.addCartItem(cartId, productId,quantiy);
        return new ResponseEntity<>(cartItemDto, HttpStatus.OK);
    }

    @PutMapping("/{cartId}/products/{productId}")
    public ResponseEntity<CartItemDto> updateCartItem( @PathVariable Long cartId, @PathVariable Long productId, @RequestParam int newQuantity) {
        CartItemDto cartItemDto = iCartItemService.updateCartItem(cartId, productId, newQuantity);
        if (cartItemDto == null) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(cartItemDto,HttpStatus.OK);
    }

    @DeleteMapping("/{cartId}/products/{productId}")
    public ResponseEntity<String> removeCartItem(@PathVariable Long cartId, @PathVariable Long productId) {
        iCartItemService.removeCartItem(cartId, productId);
        return new ResponseEntity<>("cartItem deleted successfully",HttpStatus.OK);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<List<CartItemDto>> getCartItems(@PathVariable Long cartId) {
        List<CartItemDto> cartItems = iCartItemService.getCartItems(cartId);
        return new ResponseEntity<>(cartItems,HttpStatus.OK);
    }
}
