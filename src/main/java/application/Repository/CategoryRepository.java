package application.Repository;

import application.model.Category;
import application.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category saveAndFlush(Category category);

    Category update(Long id, Category category);

    void deleteById(Long id);

    Optional<Category> findById(Long id);

    List<Category> findAll();

    List<Product> findProductsByCategoryId(Long id);
}
