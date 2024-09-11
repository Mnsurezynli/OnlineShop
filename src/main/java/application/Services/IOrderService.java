package application.Services;

import application.Dto.OrderDto;

import java.util.List;

public interface IOrderService {

    OrderDto createOrder(OrderDto orderDto);

    OrderDto trackOrder(Long orderId);

    void cancelOrder(Long userId, Long orderId);

    OrderDto getById(Long id);

     List<OrderDto> getAll();
}
