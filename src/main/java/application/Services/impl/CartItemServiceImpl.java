package application.Services.impl;

import application.Dto.CartItemDto;
import application.Services.ICartItemService;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl  implements ICartItemService {
    @Override
    public CartItemDto add(CartItemDto cartItemDto, Long cartId) {
        return null;
    }

    @Override
    public CartItemDto update(Long cartItemId, CartItemDto cartItemDto) {
        return null;
    }

    @Override
    public void delete(Long cartItemId) {

    }
}
