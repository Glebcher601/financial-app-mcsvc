package com.nixsolutions.userservice.endpoint

import com.nixsolutions.userservice.domain.JsonUserInfo
import com.nixsolutions.userservice.domain.User
import com.nixsolutions.userservice.misc.asyncFailsafe
import com.nixsolutions.userservice.repository.UserRepository
import io.micrometer.core.instrument.MeterRegistry
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.util.concurrent.Executors

@RestController
@RequestMapping(path = ["/api/users"])
class UserResource {

  companion object {
    val LOG = LoggerFactory.getLogger(UserResource::class.java)
    private val scheduler = Schedulers.fromExecutor(Executors.newFixedThreadPool(4))
  }

  @Autowired
  private lateinit var meterRegistry: MeterRegistry;

  @Autowired
  private lateinit var userRepository: UserRepository;

  @GetMapping
  fun getAll(): Flux<User> {
    return asyncFailsafe { userRepository.findAll() }
        .flatMapMany { list -> Flux.fromIterable(list) }
        .subscribeOn(scheduler)
  }

  @GetMapping(path = ["/{id}"])
  fun getById(@PathVariable id: Long): Mono<User> {
    return asyncFailsafe { userRepository.findById(id).orElseThrow { RuntimeException() } }
        .subscribeOn(scheduler)
  }

  @GetMapping(path = ["/byLogin/{login}"])
  fun getByLogin(@PathVariable login: String): Mono<User> {
    meterRegistry.counter("loginRequests").increment()
    return asyncFailsafe { userRepository.findByLogin(login)!! }
        .subscribeOn(scheduler)
  }

  @PostMapping
  fun create(@RequestBody user: JsonUserInfo): Mono<User> {
    meterRegistry.counter("userCreations").increment()
    return asyncFailsafe { userRepository.save(user.toPersistenceEntity()) }
        .subscribeOn(scheduler)
  }

  @PutMapping
  fun update(@RequestBody user: JsonUserInfo): Mono<User> {
    meterRegistry.counter("userUpdates").increment()
    return asyncFailsafe { userRepository.save(user.toPersistenceEntity()) }
        .subscribeOn(scheduler)
  }

  @DeleteMapping(path = ["/{id}"])
  fun deleteById(@PathVariable id: Long): Mono<Unit> {
    return asyncFailsafe { userRepository.deleteById(id) }
        .subscribeOn(scheduler)
  }

}
