package application.Repository;

import application.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface CartRepository extends JpaRepository<Cart, Long> {

    void addProductToCart(Long cartId, Long productId, int quantity);

    Cart viewCart(Long cartId);

    void updateCartItem(Long cartId, Long productId, int newQuantity);

    void removeProductFromCart(Long cartId, Long productId);
}


