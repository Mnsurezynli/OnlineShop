package application.Services.impl;
import application.Dto.CartItemDto;
import application.Dto.OrderDto;
import application.Dto.OrderItemDto;
import application.Repository.CartRepository;
import application.Repository.OrderRepository;
import application.Repository.ProductRepository;
import application.Services.IOrderService;
import application.exception.ResourceNotFoundException;
import application.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    @Transactional
    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Cart cart = cartRepository.findById(orderDto.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user with id: " + orderDto.getUser().getId()));
        for (CartItemDto cartItemDto : orderDto.getCartItems()) {
            Product product = productRepository.findById(cartItemDto.getProduct().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + cartItemDto.getProduct().getId()));

            int newInventory = product.getInventory() - cartItemDto.getQuantity();
            if (newInventory < 0) {
                throw new IllegalArgumentException("Insufficient inventory for product with id: " + cartItemDto.getProduct().getId());
            }
            product.setInventory(newInventory);
            productRepository.save(product);
        }

        Order order = convertToEntity(orderDto);
        Order savedOrder = orderRepository.save(order);
        return convertToDto(savedOrder);
    }

    @Transactional
    @Override
    public OrderDto trackOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        return convertToDto(order);
    }

    @Transactional
    @Override
    public void cancelOrder(Long userId, Long orderId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for user with id: " + userId));

        if ("SHIPPED".equals(order.getStatus())) {
            throw new RuntimeException("It is not possible to cancel the order at the shipping stage");
        }

        order.setStatus("CANCELED");
        orderRepository.save(order);
    }

    @Override
    public OrderDto getById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return convertToDto(order);
    }

    @Override
    public List<OrderDto> getAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private OrderDto convertToDto(Order order) {
        if (order == null) {
            return null;
        }
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setStatus(order.getStatus());
        orderDto.setDate(order.getDate());
        return orderDto;
    }

    private Order convertToEntity(OrderDto orderDto) {
        if (orderDto == null) {
            return null;
        }
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setTotalPrice(orderDto.getTotalPrice());
        order.setStatus(orderDto.getStatus());
        order.setDate(orderDto.getDate());

        if (orderDto.getUser() != null) {
            User user = new User();
            user.setId(orderDto.getUser().getId());
            order.setUser(user);
        }
        return order;
    }


}
