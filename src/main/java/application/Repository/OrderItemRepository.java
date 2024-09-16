package application.Repository;

import application.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    void deleteById(Long orderItemId);

    Optional<OrderItem> findById(Long id);

    List<OrderItem> findAll();

    List<OrderItem> findByOrderId(Long orderId);
}
