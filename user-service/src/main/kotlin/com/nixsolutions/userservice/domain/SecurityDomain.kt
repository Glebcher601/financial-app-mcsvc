package com.nixsolutions.userservice.domain

import javax.persistence.*

@Entity
data class User(@Column(nullable = false, unique = true)
                var login: String,
                var password: String,
                var enabled: Boolean,
                var permission: String //Just for now
) {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = 0
}

@Entity
@Deprecated("Not implemented!")
data class Permission(@Id
                      val id: Long,
                      val key: String,
                      val description: String);

@Entity
data class BLAH(@Id val id: Long);

/*
@Entity
@Deprecated("Not implemented!")
data class UserPermission(@ManyToOne
                          val user: User,
                          val permission: Permission);*/
