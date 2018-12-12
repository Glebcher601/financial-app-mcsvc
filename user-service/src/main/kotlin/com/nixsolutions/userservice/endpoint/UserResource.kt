package com.nixsolutions.userservice.endpoint

import com.nixsolutions.userservice.domain.User
import com.nixsolutions.userservice.misc.async
import com.nixsolutions.userservice.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping(path = ["/users"])
class UserResource {

  companion object {
    val LOG = LoggerFactory.getLogger(UserResource::class.java);
  }

  @Autowired
  private lateinit var userRepository: UserRepository;

  @GetMapping
  //@JwtProtected(roles = ["SUPERADMIN", "ADMIN", "SYSTEM"])
  fun getAll(@RequestHeader Authorization: String): Flux<User> {
    return async { userRepository.findAll() }.flatMapMany { list -> Flux.fromIterable(list) };
  }

  //@JwtProtected(roles = ["SUPERADMIN", "ADMIN", "SYSTEM"])
  @GetMapping(path = ["/{id}"])
  fun getById(@RequestHeader Authorization: String, @PathVariable id: Long): Mono<User> {
    return async { userRepository.findById(id).orElseThrow { RuntimeException() } }
  }

  //@JwtProtected(roles = ["SUPERADMIN", "ADMIN", "SYSTEM"])
  @GetMapping(path = ["/byLogin/{login}"])
  fun getByLogin(@RequestHeader Authorization: String, @PathVariable login: String): Mono<User> {
    return async { userRepository.findByLogin(login)!! };
  }

  //@JwtProtected(roles = ["SUPERADMIN", "ADMIN", "SYSTEM"])
  @PostMapping
  fun create(@RequestHeader Authorization: String, @RequestBody user: User): Mono<User> {
    return async { userRepository.save(user) }
  }

  //@JwtProtected(roles = ["SUPERADMIN", "ADMIN", "SYSTEM"])
  @PutMapping
  fun update(@RequestHeader Authorization: String, @RequestBody user: User): Mono<User> {
    return async { userRepository.save(user) }
  }

  //@JwtProtected(roles = ["SUPERADMIN", "ADMIN", "SYSTEM"])
  @DeleteMapping(path = ["/{id}"])
  fun deleteById(@RequestHeader Authorization: String, @PathVariable id: Long): Mono<Unit> {
    return async { userRepository.deleteById(id) }
  }

}
