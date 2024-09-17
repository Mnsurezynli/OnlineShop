package application.Services;

import application.Dto.CartDto;

public interface ICartService {

    CartDto createCart(Long userId);

    void addProductToCart(Long cartId, Long productId, int quantity);

    CartDto viewCart(Long cartId);

    void updateCartItem(Long cartId, Long productId, int newQuantity);

    void removeProductFromCart(Long cartId, Long productId);
}


