package com.nixsolutions.userservice.repository

import com.nixsolutions.userservice.domain.Permission
import com.nixsolutions.userservice.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
  fun findByLogin(login: String): User?
}

@Repository
interface PermissionRepository : JpaRepository<Permission, Long>
