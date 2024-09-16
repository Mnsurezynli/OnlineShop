package application.Repository;

import application.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface OrderRepository extends JpaRepository<Order, Long> {

   Optional<Order> findByIdAndUserId(Long orderId, Long userId);

   Optional<Order> findById(Long id);

   List<Order> findAll();
}
