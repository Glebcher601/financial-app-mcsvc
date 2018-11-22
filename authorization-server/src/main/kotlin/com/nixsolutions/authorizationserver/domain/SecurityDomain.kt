package com.nixsolutions.authorizationserver.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class User(@Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                val id: Long,
                val login: String,
                val password: String, //TODO encrypter
                val enabled: Boolean,
                val permission: String //Just for now
)

@Deprecated("Not implemented!")
data class Permission(val id: Long,
                      val key: String,
                      val description: String);

@Deprecated("Not implemented!")
data class UserPermission(val user: User, val permission: String);