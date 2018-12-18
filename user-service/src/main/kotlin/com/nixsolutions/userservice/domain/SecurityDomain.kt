package com.nixsolutions.userservice.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany

typealias SpringSecUser = org.springframework.security.core.userdetails.User;

@Entity
open class User(@Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                var id: Long,
                @Column(nullable = false, unique = true)
                var login: String,
                @Column(name = "password")
                @JsonProperty("password")
                var password_: String,
                var enabled: Boolean,
                @ManyToMany(fetch = FetchType.EAGER)
                @JoinTable(name = "USER_PERMISSION",
                    joinColumns = [JoinColumn(name = "user_id")],
                    inverseJoinColumns = [JoinColumn(name = "permission_id")])
                var permissions: MutableSet<Permission>
) {
  constructor(user: User) : this(user.id, user.login, user.password_, user.enabled, user.permissions) {
  }
}

@Entity
open class Permission(@Id
                      @GeneratedValue(strategy = GenerationType.IDENTITY)
                      var id: Long,
                      var key: String,
                      var description: String,
                      @JsonIgnore
                      @ManyToMany(mappedBy = "permissions")
                      var users: MutableSet<User>
) {
  override fun hashCode(): Int {
    return HashCodeBuilder.reflectionHashCode(this, mutableListOf("users"))
  }
}

class CustomUserPrincipal(user: User) : User(user), UserDetails {
  override fun getAuthorities() = permissions.stream()
      .map { SimpleGrantedAuthority(it.key) }
      .collect(Collectors.toList())

  override fun isEnabled() = true

  override fun getUsername() = login

  override fun isCredentialsNonExpired() = true

  override fun getPassword() = password_

  override fun isAccountNonExpired() = true

  override fun isAccountNonLocked() = true
}