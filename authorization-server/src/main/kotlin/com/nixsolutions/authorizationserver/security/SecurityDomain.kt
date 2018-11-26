package com.nixsolutions.authorizationserver.security

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class User(val id: Long,
                val login: String,
                val password: String,
                val enabled: Boolean,
                val permission: String //Just for now
)

data class CustomUserPrincipal(private val user: User) : UserDetails {

  init {
    if (user.id.equals(0))
      throw RuntimeException();
  }

  override fun isEnabled(): Boolean {
    return user.enabled;
  }

  @JsonProperty
  fun getId(): Long {
    return user.id;
  }

  @JsonProperty
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

  @JsonProperty
  override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
    return mutableListOf(SimpleGrantedAuthority(user.permission))
  }
}

data class Permission(val id: Long,
                      val key: String,
                      val description: String)
