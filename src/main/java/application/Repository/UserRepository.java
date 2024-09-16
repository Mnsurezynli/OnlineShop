package application.Repository;

import application.Dto.UserDto;
import application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User,Long> {

     User saveAndFlush(User user);

      Optional<User> findByUsernameAndPassword(String username , String password);

      Optional<User> update(Long id ,UserDto userDto);

    Optional<User> findByUsername(String username);

      void deleteById(Long id);

      Optional<User> findById(Long id);

      List<User> findAll();


}
