package com.nixsolutions.userservice.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.apache.commons.lang3.builder.HashCodeBuilder
import javax.persistence.*

typealias SpringSecUser = org.springframework.security.core.userdetails.User;

@Entity
open class User(@Column(nullable = false, unique = true)
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
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = 0

  constructor(user: User) : this(user.login, user.password_, user.enabled, user.permissions) {
    id = user.id
  }
}

@Entity
open class Permission(@Id
                      @GeneratedValue(strategy = GenerationType.IDENTITY)
                      var id: Long? = null,
                      @JsonProperty(required = false)
                      var key: String? = null,
                      @JsonProperty(required = false)
                      var description: String? = null,
                      @JsonIgnore
                      @ManyToMany(mappedBy = "permissions")
                      var users: MutableSet<User> = mutableSetOf()) {

  override fun hashCode(): Int {
    return HashCodeBuilder.reflectionHashCode(this, mutableListOf("users"))
  }
}

interface Persisted<out E> {
  fun toPersistenceEntity(): E
}

class JsonUserInfo(var id: Long? = null,
                   var login: String,
                   var password: String,
                   var enabled: Boolean,
                   var permissions: MutableSet<JsonPermissionInfo>) : Persisted<User> {
  override fun toPersistenceEntity(): User = User(login, password, enabled,
      mutableSetOf(*permissions.map { it.toPersistenceEntity() }.toTypedArray()));

}

class JsonPermissionInfo(var id: Long) : Persisted<Permission> {
  override fun toPersistenceEntity(): Permission = Permission(id, null, null, mutableSetOf())
}