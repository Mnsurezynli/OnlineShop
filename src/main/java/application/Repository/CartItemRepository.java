package application.Repository;

import application.model.Cart;
import application.model.CartItem;
import application.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
@EnableJpaRepositories
public interface CartItemRepository extends JpaRepository<CartItem,Long> {


    CartItem findByCartAndProduct(Cart cart, Product product);

    Arrays findByCart(Cart cart);
}
