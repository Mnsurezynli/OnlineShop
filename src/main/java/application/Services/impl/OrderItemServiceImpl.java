package application.Services.impl;

import application.Dto.OrderItemDto;
import application.Dto.ProductDto;
import application.Repository.OrderItemRepository;
import application.Repository.OrderRepository;
import application.Repository.ProductRepository;
import application.Services.IOrderItemService;
import application.model.Order;
import application.model.OrderItem;
import application.model.Product;
import application.model.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements IOrderItemService {

    private OrderRepository orderRepository;

    private ProductRepository productRepository;

    private OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderItemDto add(OrderItemDto orderItemDto) {
        Order order = orderRepository.findById(orderItemDto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id " + orderItemDto.getOrderId()));
        Product product = productRepository.findById(orderItemDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + orderItemDto.getProductId()));

        OrderItem orderItem = new OrderItem();
        orderItem.setNumber(orderItemDto.getNumber());
        orderItem.setOrder(order);
        orderItem.setProduct(product);

        OrderItem OrderItem = orderItemRepository.save(orderItem);
        return convertToDto(OrderItem);
    }


    @Override
    public void deleteById(Long orderItemId) {
        Optional<OrderItem> orderItem = orderItemRepository.findById(orderItemId);
        if (orderItem.isPresent()) {
            orderItemRepository.deleteById(orderItemId);
            System.out.println("OrderItem deleted successfully");
        } else {
            System.out.println("OrderItem not found َََ");
        }
    }


    @Override
    public Optional<OrderItemDto> getOrderItemById(Long id) {
        return orderItemRepository.findOrderItemById(id).map(this::convertToDto);
    }

    @Override
    public List<OrderItemDto> getAll() {
        return orderItemRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderItemDto> getOrderItemsByOrderId(Long orderId) {
        List<OrderItem> orderItems = orderItemRepository.findOrderItemsByOrderId(orderId);

        if (orderItems.isEmpty()) {
            //   throw new ResourceNotFoundException("Not found orderItem ");
        }

        return orderItems.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    public OrderItemDto convertToDto(OrderItem orderItem) {

        if (orderItem == null) {
            return null;
        }
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setNumber(orderItem.getNumber());
        return orderItemDto;
    }

    public OrderItem convertToEntity(OrderItemDto orderItemDto) {
        if (orderItemDto == null) {
            return null;
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemDto.getId());
        orderItem.setNumber(orderItemDto.getNumber());
        return orderItem;
    }

}
