package id.co.perspro.loginservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import id.co.perspro.loginservice.model.entity.RoleEntity;
import id.co.perspro.loginservice.model.enums.UserRoleEnum;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

  Optional<RoleEntity> findByRole(UserRoleEnum role);
}
