package application.Repository;

import application.Dto.UserDto;
import application.Dto.UserProfileDto;
import application.model.User;
import application.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {

    Optional<UserProfile> update(Long id , UserProfileDto userProfileDto);

    Optional<UserProfile> findById(Long id);

    List<UserProfile> findAll();
}
