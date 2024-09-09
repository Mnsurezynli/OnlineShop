package application.Services.impl;

import application.Dto.OrderDto;
import application.Services.IOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {


    @Override
    public OrderDto save(Long userId) {
        return null;
    }

    @Override
    public OrderDto trackOrder(Long orderId) {
        return null;
    }

    @Override
    public void cancleOrder(Long orderId) {

    }

    @Override
    public List<OrderDto> getByUserId(Long id) {
        return null;
    }
}
