package application.controller;

import application.Dto.OrderItemDto;
import application.Dto.ProductDto;
import application.Services.IOrderItemService;
import application.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/OrderItem")
public class OrderItemController {

    private IOrderItemService iOrderItemService;

    public OrderItemController(IOrderItemService iOrderItemService) {
        this.iOrderItemService = iOrderItemService;
    }
     @PostMapping("/add")
    public ResponseEntity <OrderItemDto> addOrderItem(@RequestBody OrderItemDto orderItemDto) {
        OrderItemDto orderItemDto1 = iOrderItemService.add(orderItemDto);
        return new ResponseEntity<>(orderItemDto1, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderItem(@PathVariable("id") Long orderItemId) {
        iOrderItemService.deleteById(orderItemId);
        return new ResponseEntity<>("OrderItem was deleted ",HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<OrderItemDto>> getOrderItemById(@PathVariable("id") Long id) {
        List<OrderItemDto> orderItemDtos = iOrderItemService.getOrderItemById(id);
        return new ResponseEntity<>(orderItemDtos, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<OrderItemDto>> getAllOrderItems() {
        List<OrderItemDto> orderItemDtos =  iOrderItemService.getAll();
        return new ResponseEntity<>(orderItemDtos, HttpStatus.OK);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItemDto>> getOrderItemsByOrderId(@PathVariable("orderId") Long orderId) {
        List<OrderItemDto> orderItemDtos = iOrderItemService.getOrderItemsByOrderId(orderId);
        return new ResponseEntity<>(orderItemDtos, HttpStatus.OK);
    }
}

