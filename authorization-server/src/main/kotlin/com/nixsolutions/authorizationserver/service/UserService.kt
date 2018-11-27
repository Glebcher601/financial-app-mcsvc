package com.nixsolutions.authorizationserver.service

interface UserService : UserByIdSearch, UserByLoginSearch, ServiceRegistryAware {
}