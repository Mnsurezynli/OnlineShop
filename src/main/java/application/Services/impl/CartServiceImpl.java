package application.Services.impl;

import application.Dto.CartDto;
import application.Dto.CartItemDto;
import application.Services.ICartService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements ICartService {
    @Override
    public void save(Long userId, CartItemDto cartItemDto) {

    }

    @Override
    public void delete(Long userId) {

    }

    @Override
    public CartDto getByUser(Long userId) {
        return null;
    }

    @Override
    public CartDto calculate(Long userId) {
        return null;
    }
}
