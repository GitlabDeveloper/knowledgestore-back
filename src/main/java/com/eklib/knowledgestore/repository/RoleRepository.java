package com.eklib.knowledgestore.repository;

import com.eklib.knowledgestore.model.user.Role;
import com.eklib.knowledgestore.model.user.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
/**
 * Created by n.yushchenko on 15.03.2019
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
