package lk.ijse.gdse68.aad.NoteTakerV2.repository;

import lk.ijse.gdse68.aad.NoteTakerV2.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity getUserEntityByUserId(String userId);
    Optional<UserEntity> findByEmail(String email);
}
