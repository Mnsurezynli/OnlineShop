package application.Repository;

import application.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface CommentRepository extends JpaRepository<Comment, Long> {

    void deleteById(Long id);

    Optional<Comment> findById(Long id);

    List<Comment> findAll();

    List<Comment> findByProductId(Long productId);

    List<Comment> findByUserId(Long userId);
}
