package application.controller;

import application.Dto.OrderDto;
import application.Services.IOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private IOrderService iOrderService;

    public OrderController(IOrderService iOrderService) {
        this.iOrderService = iOrderService;
    }

    @PostMapping("/createOrder")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        OrderDto createdOrder = iOrderService.createOrder(orderDto);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> trackOrder(@PathVariable Long orderId) {
        OrderDto orderDto = iOrderService.trackOrder(orderId);
        return new ResponseEntity<>(orderDto,HttpStatus.OK);
    }

    @PutMapping("/{orderId}/cancel/{userId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId, @PathVariable Long userId) {
        iOrderService.cancelOrder(userId, orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        OrderDto orderDto = iOrderService.getById(id);
       return new ResponseEntity<>(orderDto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = iOrderService.getAll();
       return new ResponseEntity<>(orders,HttpStatus.OK);
    }
}
