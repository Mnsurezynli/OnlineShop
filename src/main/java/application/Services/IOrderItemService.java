package application.Services;

import application.Dto.OrderItemDto;

import java.util.List;
import java.util.Optional;

public interface IOrderItemService {

    OrderItemDto add (OrderItemDto orderItemDto);
    void deleteById(Long orderItemId);

    Optional<OrderItemDto> getOrderItemById(Long id);
    List<OrderItemDto> getAll();
    List<OrderItemDto> getOrderItemsByOrderId(Long orderId);
}
