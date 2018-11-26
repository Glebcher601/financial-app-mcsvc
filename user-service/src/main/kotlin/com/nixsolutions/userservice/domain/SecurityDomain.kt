package com.nixsolutions.userservice.domain

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
