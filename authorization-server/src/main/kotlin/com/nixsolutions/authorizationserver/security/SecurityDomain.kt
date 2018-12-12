package com.nixsolutions.authorizationserver.security

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

@JsonIgnoreProperties(ignoreUnknown = true)
data class User(val id: Long,
                val login: String,
                val password: String,
                val enabled: Boolean,
                val permissions: Set<Permission> //Just for now
)

data class CustomUserDetails(private val user: User) : UserDetails {

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
    return user.permissions.stream()
        .map { SimpleGrantedAuthority(it.key) }
        .collect(Collectors.toList())
  }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Permission(val key: String)
