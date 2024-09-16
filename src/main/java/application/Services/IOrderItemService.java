package application.Services;

import application.Dto.OrderItemDto;

import java.util.List;

public interface IOrderItemService {

    OrderItemDto add(OrderItemDto orderItemDto);

    void deleteById(Long orderItemId);

    OrderItemDto getOrderItemById(Long id);

    List<OrderItemDto> getAll();

    List<OrderItemDto> getOrderItemsByOrderId(Long orderId);
}
