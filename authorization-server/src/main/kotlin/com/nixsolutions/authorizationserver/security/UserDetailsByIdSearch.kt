package com.nixsolutions.authorizationserver.security

import com.nixsolutions.authorizationserver.service.UserByIdSearch

interface UserDetailsByIdSearch : UserByIdSearch {

  fun loadUserDetailsById(id: Long): CustomUserDetails {
    return CustomUserDetails(findUserById(id) ?: throw RuntimeException("User cannot be found"))
  }
}