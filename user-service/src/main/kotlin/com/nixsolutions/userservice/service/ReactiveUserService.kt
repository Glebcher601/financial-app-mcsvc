package com.nixsolutions.userservice.service

import com.nixsolutions.userservice.domain.User
import com.nixsolutions.userservice.misc.async
import com.nixsolutions.userservice.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ReactiveUserService {
  fun getAll(): Flux<User>

  fun delete(id: Long): Mono<Unit>

  fun save(user: User): Mono<User>

  fun getByLogin(login: String): Mono<User>
}

@Service
class ReactiveUserServiceImpl : ReactiveUserService {
  @Autowired
  lateinit var userRepository: UserRepository;

  override fun getAll(): Flux<User> {
    return async { userRepository.findAll() }.flatMapMany { list -> Flux.fromIterable(list) }
  }

  override fun delete(id: Long): Mono<Unit> {
    return async { userRepository.deleteById(id) }
  }

  override fun save(user: User): Mono<User> {
    return async { userRepository.save(user) }
  }

  override fun getByLogin(login: String): Mono<User> {
    return async { userRepository.findByLogin(login)!! }
  }


}