package application.Services;

import application.Dto.CartDto;
import application.Dto.CartItemDto;

public interface ICartService {

    public void save(Long userId , CartItemDto cartItemDto);

    public void delete(Long userId);

    public CartDto getByUser(Long userId);

    public CartDto calculate(Long userId);
}
