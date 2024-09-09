package application.Services;

import application.Dto.OrderDto;

import java.util.List;

public interface IOrderService {

    public OrderDto save(Long userId);

    public OrderDto trackOrder(Long orderId);

    public void cancleOrder(Long orderId);

    public List<OrderDto> getByUserId(Long id);
}
