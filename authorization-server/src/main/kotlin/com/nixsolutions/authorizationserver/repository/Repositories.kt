package com.nixsolutions.authorizationserver.repository

import com.nixsolutions.authorizationserver.security.Permission
import com.nixsolutions.authorizationserver.security.User
//import com.nixsolutions.authorizationserver.security.UserPermission
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByLogin(login: String): User?
}

interface PermissionRepository : JpaRepository<Permission, Long>

//interface UserPermissionRepository: JpaRepository<UserPermission, Long>

