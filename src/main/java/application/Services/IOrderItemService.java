package application.Services;

import application.Dto.OrderItemDto;

public interface IOrderItemService {

    public OrderItemDto add (OrderItemDto orderItemDto);

    public OrderItemDto update(Long orderItemId, OrderItemDto orderItemDto);

    public void delete(Long orderItemId);
}
