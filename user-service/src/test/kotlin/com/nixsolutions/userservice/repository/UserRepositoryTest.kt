package com.nixsolutions.userservice.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.nixsolutions.userservice.domain.User
import org.junit.Ignore
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.stream.Stream
import javax.sql.DataSource

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
@Ignore
class UserRepositoryTest {

  @Autowired
  private lateinit var userRepository: UserRepository;

  @Autowired
  private lateinit var dataSource: DataSource;

  val objectMapper: ObjectMapper = ObjectMapper();

  @BeforeEach
  fun setUp() {
    val initSchema = ClassPathResource("test-sql/schema/schema.sql")
    val initData = ClassPathResource("test-sql/data/data.sql")
    val databasePopulator = ResourceDatabasePopulator(initSchema, initData)
    DatabasePopulatorUtils.execute(databasePopulator, dataSource)
  }

  @ParameterizedTest(name = "Should {0}")
  @MethodSource("argsForTestCrud")
  fun testOverAll(testCaseName: String, exec: () -> List<User>, expected: List<User>) {
    //when-then
    Assertions.assertIterableEquals(expected, exec())
  }

  fun argsForTestCrud(): Stream<Arguments>? {
    return Stream.of(
        Arguments.of(
            "find all",
            { userRepository.findAll() },
            getListOfUsers("/test-data/users.json")
        ),
        Arguments.of(
            "find by id",
            { mutableListOf(userRepository.findById(1).get()) },
            getListOfUsers("/test-data/user_get_id1.json")
        ),
        Arguments.of(
            "get by login",
            { mutableListOf(userRepository.findByLogin("user1")!!) },
            getListOfUsers("/test-data/user_get_login1.json")
        ),
        Arguments.of(
            "delete by id",
            {
              userRepository.deleteById(1)
              userRepository.findAll()
            },
            getListOfUsers("/test-data/user_delete_id1.json")
        ),
        Arguments.of(
            "update user",
            {
              val user = User(1, "user1_UPD", "password1_UPD", true, HashSet())
              userRepository.save(user)
              userRepository.findAll()
            },
            getListOfUsers("/test-data/user_update_id1.json")
        ))
  }

  private fun getListOfUsers(fileName: String): List<User> {
    return objectMapper.readValue<List<User>>(javaClass.getResourceAsStream(fileName))
  }
}