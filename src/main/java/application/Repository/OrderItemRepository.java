package application.Repository;

import application.Dto.OrderItemDto;
import application.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

    OrderItem saveAndFlush (OrderItem orderItem);
    void deleteById(Long orderItemId);

    Optional<OrderItem> findOrderItemById(Long id);
    List<OrderItem> findAll();
    List<OrderItem> findOrderItemsByOrderId(Long orderId);
}
