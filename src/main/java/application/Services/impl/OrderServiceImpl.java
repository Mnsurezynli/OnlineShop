package application.Services.impl;

import application.Dto.CartItemDto;
import application.Dto.OrderDto;
import application.Repository.CartRepository;
import application.Repository.OrderRepository;
import application.Repository.ProductRepository;
import application.Repository.UserRepository;
import application.Services.IOrderService;
import application.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {

    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    private CartRepository cartRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        // یافتن سبد خرید کاربر
        Cart cart = cartRepository.findById(orderDto.getUser().getId())
        // .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user with id: " + orderDto.getUserId()));
        // بررسی موجودی محصولات و کاهش موجودی
        for (CartItemDto cartItemDto : orderDto.getCartItems()) {
            Product product = productRepository.findById(cartItemDto.getProduct().getId())
            //  .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + cartItemDto.getProductId()));
            // کاهش موجودی محصول
            product.setInventory(product.getInventory() - cartItemDto.getQuantity());
            productRepository.save(product);
        }
        // ایجاد و ذخیره سفارش
        Order order = new Order();
        Order savedOrder = orderRepository.save(order);
        order = convertToEntity(orderDto);
        return orderDto;
    }
    @Transactional
    @Override
    public OrderDto trackOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
        // .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));


        OrderDto orderDto = convertToDto(order);


        return orderDto;
    }

    @Transactional
    @Override
    public void cancelOrder(Long userId, Long orderId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new RuntimeException("The order was not found"));
        if ("SHIPPED".equals(order.getStatus())) {
            throw new RuntimeException("It is not possible to cancel the order at the shipping stage");
        }
        order.setStatus("CANCELED");
        orderRepository.save(order);
        System.out.println("The order was successfully cancelled");
    }


    @Override
    public OrderDto getById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToDto(order);
    }

    @Override
    public List<OrderDto> getAll() {
        List<Order> order = orderRepository.findAll();
        return
                order.stream().map(this::convertToDto).collect(Collectors.toList());

    }

    public OrderDto convertToDto(Order order) {

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

    public Order convertToEntity(OrderDto orderDto) {
        if (orderDto == null) {
            return null;
        }
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setTotalPrice(orderDto.getTotalPrice());
        order.setStatus(orderDto.getStatus());
        order.setDate(orderDto.getDate());
        return order;
    }


}
