package application.Repository;

import application.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface CartRepository extends JpaRepository<Cart, Long> {

   // void save(Long cartId, Long productId, int quantity);

    @Query("SELECT c FROM Cart c WHERE c.id = :cartId")
    Cart findCartByById(@Param("cartId") Long cartId);


   // void updateCartItem(Long cartId, Long productId, int newQuantity);

    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.id = :productId")
    void removeProductFromCart(@Param("cartId") Long cartId,  @Param("productId") Long productId);

}


