package application.Repository;

import application.Dto.ProductDto;
import application.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product saveAndFlush(Product product);

   // @Modifying
   // @Query("UPDATE Product p SET p.name = :name, p.price = :price, p.description = :description WHERE p.id = :id")
   // void update(@Param("id") Long id, @Param("name") String name, @Param("price") Double price, @Param("description") String description);
    void  deleteById(Long id);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByName(String name);
}
