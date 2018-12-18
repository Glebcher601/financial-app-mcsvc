package com.nixsolutions.financial.security.aspect;

/*@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@Configuration
@ContextConfiguration(classes = JwtValidationConfiguration.class)*/
public class JwtSecurityAdviceTest
{
/*  @Autowired
  SecuredService securedService;

  @ParameterizedTest(name = "Should {0}")
  @MethodSource("argsForShouldProtectMethod")
  public void shouldProtectMethod(String name, Executable methodCall, Class<? extends Exception> ex)
  {
    Assertions.assertThrows(ex, methodCall);
  }

  public Stream<Arguments> argsForShouldProtectMethod()
  {
    return Stream.of(
        Arguments.of(
            "throw exception if token expired",
            (Executable) () -> securedService.adminProtected("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJTVUJKRUNUIiwibmFtZSI6Ik5hbWUiLCJyb2xlcyI6WyJVU0VSIiwiVklTSVRPUiJdLCJpYXQiOjE1NDQ0NTE4NTUsImV4cCI6MTU0NDM1MH0.TDmfKMllvFtZTy4Y5jN-otFvj0zPAaIQW47emecrTUK29bOl4UYCidy9mJJ1wztMWjoAtR9na86H1TJTeNDdyA"),
            InvalidTokenException.class
        ),
        Arguments.of(
            "throw exception if has no access",
            (Executable) () -> securedService.adminProtected("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJTVUJKRUNUIiwibmFtZSI6Ik5hbWUiLCJyb2xlcyI6WyJVU0VSIl19.Cy8tvwsnXElwfBRe42a496K4nhqZGhpFeRNVN-AMtFcFwO16jq4LJjeXefcxXzvpmGwPnoR3S5LDJ-yB-9cMpw"),
            NoAccessException.class
        ),
        Arguments.of(
            "throw exception on wrong token",
            (Executable) () -> securedService.adminProtected("WRONG TOKEN"),
            InvalidTokenException.class
        ),
        Arguments.of(
            "throw exception if no token is present",
            (Executable) () -> securedService.adminProtected(""),
            InvalidTokenException.class
        )
    );
  }

  @Bean
  public JwtVerifier jwtVerifier()
  {
    return new SecretAwareJwtVerifier("SECRET");
  }*/
}