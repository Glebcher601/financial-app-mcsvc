package com.nixsolutions.userservice.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.apache.commons.lang3.builder.HashCodeBuilder
import javax.persistence.*

@Entity
data class User(@Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                var id: Long,
                @Column(nullable = false, unique = true)
                var login: String,
                var password: String,
                var enabled: Boolean,
                @ManyToMany(fetch = FetchType.EAGER)
                @JoinTable(name = "USER_PERMISSION",
                    joinColumns = [JoinColumn(name = "user_id")],
                    inverseJoinColumns = [JoinColumn(name = "permission_id")])
                var permissions: Set<Permission>
)

@Entity
data class Permission(@Id
                      @GeneratedValue(strategy = GenerationType.IDENTITY)
                      var id: Long,
                      var key: String,
                      var description: String,
                      @JsonIgnore
                      @ManyToMany(mappedBy = "permissions")
                      var users: Set<User>
) {
  override fun hashCode(): Int {
    return HashCodeBuilder.reflectionHashCode(this, mutableListOf("users"))
  }
}
