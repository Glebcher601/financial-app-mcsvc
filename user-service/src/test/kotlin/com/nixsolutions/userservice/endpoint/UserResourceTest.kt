package com.nixsolutions.userservice.endpoint

import com.nixsolutions.userservice.repository.UserRepository
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner::class)
@AutoConfigureMockMvc
class UserResourceTest {

  @Autowired
  private lateinit var mockMvc: MockMvc;

  @Autowired
  private lateinit var userRepository: UserRepository;

  @Before
  fun setUp() {
  }

  @After
  fun tearDown() {
  }

  @Test
  fun getAll() {

  }

  @Test
  fun getById() {
  }

  @Test
  fun getByLogin() {
  }

  @Test
  fun create() {
  }

  @Test
  fun update() {
  }

  @Test
  fun deleteById() {
  }
}