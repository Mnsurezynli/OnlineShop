package application.Services;

import application.Dto.CartItemDto;

public interface ICartItemService {
     public CartItemDto add(CartItemDto cartItemDto, Long cartId);
    public   CartItemDto update(Long cartItemId, CartItemDto cartItemDto);
    public void delete(Long cartItemId);
}

