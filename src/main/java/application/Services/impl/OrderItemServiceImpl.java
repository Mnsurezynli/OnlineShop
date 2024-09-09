package application.Services.impl;

import application.Dto.OrderItemDto;
import application.Services.IOrderItemService;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements IOrderItemService {
    @Override
    public OrderItemDto add(OrderItemDto orderItemDto) {
        return null;
    }

    @Override
    public OrderItemDto update(Long orderItemId, OrderItemDto orderItemDto) {
        return null;
    }

    @Override
    public void delete(Long orderItemId) {

    }
}
