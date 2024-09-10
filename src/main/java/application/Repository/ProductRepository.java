package application.Repository;

import application.Dto.ProductDto;
import application.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product saveAndFlush(Product product);

    Product update(Long id , Product product);

    void  delete(Long id);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByName(String name);
}
