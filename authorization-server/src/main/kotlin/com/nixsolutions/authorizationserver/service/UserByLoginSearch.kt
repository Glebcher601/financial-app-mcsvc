package com.nixsolutions.authorizationserver.service

import com.nixsolutions.authorizationserver.security.User

interface UserByLoginSearch {
  fun findUserByLogin(login: String): User?
}