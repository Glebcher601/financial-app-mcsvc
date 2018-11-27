package com.nixsolutions.authorizationserver.service

import com.nixsolutions.authorizationserver.security.User

interface UserByIdSearch {
  fun findUserById(id: Long): User?
}