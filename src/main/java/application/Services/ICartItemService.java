package application.Services;

import application.Dto.CartItemDto;

import java.util.List;

public interface ICartItemService {


    CartItemDto addCartItem(Long cartId, Long productId, int quantity);

    CartItemDto updateCartItem(Long cartId, Long productId, int newQuantity);

    void removeCartItem(Long cartId, Long productId);

    List<CartItemDto> getCartItems(Long cartId);

}



