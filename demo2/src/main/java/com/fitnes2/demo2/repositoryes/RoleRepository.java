package com.fitnes2.demo2.repositoryes;

import com.fitnes2.demo2.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
