package application.Services.impl;

import application.Dto.OrderItemDto;
import application.Repository.OrderItemRepository;
import application.Repository.OrderRepository;
import application.Repository.ProductRepository;
import application.Services.IOrderItemService;
import application.exception.ResourceNotFoundException;
import application.model.Order;
import application.model.OrderItem;
import application.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements IOrderItemService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    @Autowired
    public OrderItemServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    @Override
    public OrderItemDto add(OrderItemDto orderItemDto) {
        Order order = orderRepository.findById(orderItemDto.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderItemDto.getOrderId()));
        Product product = productRepository.findById(orderItemDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + orderItemDto.getProductId()));

        OrderItem orderItem = new OrderItem();
        orderItem.setNumber(orderItemDto.getNumber());
        orderItem.setOrder(order);
        orderItem.setProduct(product);

        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        return convertToDto(savedOrderItem);
    }
    @Transactional
    @Override
    public void deleteById(Long orderItemId) {
        if (!orderItemRepository.existsById(orderItemId)) {
            throw new ResourceNotFoundException("OrderItem with ID " + orderItemId + " not found.");
        }
        orderItemRepository.deleteById(orderItemId);
    }

    @Override
    public OrderItemDto getOrderItemById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem with ID " + id + " not found."));
        return convertToDto(orderItem);
    }

    @Override
    public List<OrderItemDto> getAll() {
        return orderItemRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderItemDto> getOrderItemsByOrderId(Long orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        if (orderItems.isEmpty()) {
            throw new ResourceNotFoundException("No OrderItems found for order with ID " + orderId);
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
        orderItemDto.setOrderId(orderItem.getOrder().getId());
        orderItemDto.setProductId(orderItem.getProduct().getId());
        return orderItemDto;
    }


    private OrderItem convertOrderItemDtoToEntity(OrderItemDto orderItemDto) {
        if (orderItemDto == null) {
            return null;
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemDto.getId());
        orderItem.setNumber(orderItemDto.getNumber());


        if (orderItemDto.getProduct() != null) {
            Product product = new Product();
            product.setId(orderItemDto.getProduct().getId());
            orderItem.setProduct(product);
        }

        return orderItem;
    }
}
