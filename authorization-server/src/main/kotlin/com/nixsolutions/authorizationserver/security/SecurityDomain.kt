package com.nixsolutions.authorizationserver.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
data class User(@Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                val id: Long,
                @Column(nullable = false, unique = true)
                val login: String,
                val password: String,
                val enabled: Boolean,
                val permission: String //Just for now
)

data class CustomUserPrincipal(private val user: User) : UserDetails {
    override fun isEnabled(): Boolean {
        return user.enabled;
    }

    override fun getUsername(): String {
        return user.login
    }

    override fun isCredentialsNonExpired(): Boolean {
        return user.enabled
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun isAccountNonExpired(): Boolean {
        return user.enabled
    }

    override fun isAccountNonLocked(): Boolean {
        return user.enabled
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(user.permission));
    }
}

@Entity
@Deprecated("Not implemented!")
data class Permission(@Id
                      val id: Long,
                      val key: String,
                      val description: String);

/*
@Entity
@Deprecated("Not implemented!")
data class UserPermission(@ManyToOne
                          val user: User,
                          val permission: Permission);*/
