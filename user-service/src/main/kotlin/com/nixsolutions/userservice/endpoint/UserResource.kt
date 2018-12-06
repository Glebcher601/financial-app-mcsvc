package com.nixsolutions.userservice.endpoint

import com.nixsolutions.financial.security.aspect.JwtProtected
import com.nixsolutions.userservice.domain.User
import com.nixsolutions.userservice.misc.async
import com.nixsolutions.userservice.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
  fun getAll(): Flux<User> {
    return async { userRepository.findAll() }.flatMapMany { list -> Flux.fromIterable(list) };
  }

  @JwtProtected(roles = ["ADMIN"])
  @GetMapping(path = ["/{id}"])
  fun getById(@PathVariable id: Long): Mono<User> {
    return async { userRepository.findById(id).orElseThrow { RuntimeException() } }
  }

  @GetMapping(path = ["/byLogin/{login}"])
  fun getByLogin(@PathVariable login: String): Mono<User> {
    return async { userRepository.findByLogin(login)!! };
  }

  @PostMapping
  fun create(@RequestBody user: User): Mono<User> {
    return async { userRepository.save(user) }
  }

  @PutMapping
  fun update(@RequestBody user: User): Mono<User> {
    return async { userRepository.save(user) }
  }

  @DeleteMapping(path = ["/{id}"])
  fun deleteById(@PathVariable id: Long): Mono<Unit> {
    return async { userRepository.deleteById(id) }
  }

}

/*
* @RestController
@RequestMapping(path = ["/users"])
class UserResource {

  @Autowired
  private lateinit var userRepository: UserRepository;

  @GetMapping
  fun getAll(): List<User> {
    return userRepository.findAll()
  }

  @GetMapping(path = ["/{id}"])
  fun getById(@PathVariable id: Long): User {
    return userRepository.findById(id).orElseThrow { RuntimeException() }
  }

  @GetMapping(path = ["/byLogin/{login}"])
  fun getByLogin(@PathVariable login: String): User {
    return userRepository.findByLogin(login)!!
  }

  @PostMapping
  fun create(@RequestBody user: User): User {
    return userRepository.save(user)
  }

  @PutMapping
  fun update(@RequestBody user: User): User {
    return userRepository.save(user)
  }

  @DeleteMapping(path = ["/{id}"])
  fun deleteById(@PathVariable id: Long): ResponseEntity<Unit> {
    userRepository.deleteById(id)
    return ResponseEntity.ok().build()
  }

}*/
