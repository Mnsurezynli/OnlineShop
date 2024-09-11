package application.Services;

import application.Dto.CartDto;
import application.Dto.CartItemDto;

public interface ICartService {


    void addProductToCart(Long cartId, Long productId, int quantity);

    CartDto viewCart(Long cartId);

    void updateCartItem(Long cartId, Long productId, int newQuantity);

    void removeProductFromCart(Long cartId, Long productId);
}




