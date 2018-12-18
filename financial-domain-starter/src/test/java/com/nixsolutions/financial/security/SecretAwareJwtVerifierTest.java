package com.nixsolutions.financial.security;

/*@TestInstance(TestInstance.Lifecycle.PER_CLASS)*/
public class SecretAwareJwtVerifierTest
{
  /*@Test
  public void shouldProduceExceptionOnWrongToken()
  {
    //given
    String token =
        "eyJ111111iJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ._XEngvIuxOcA-j7y_upRUbXli4DLToNf7HxH1XNmxSc";

    SecretAwareJwtVerifier jwtVerifier = new SecretAwareJwtVerifier("SECRET");
    Executable parseToken = () -> jwtVerifier.parseToken(token);

    //then
    Assertions.assertThrows(InvalidTokenException.class, parseToken);
  }

  @Test
  public void shouldProduceExceptionOnEmptyToken()
  {
    //given
    String token = "";
    SecretAwareJwtVerifier jwtVerifier = new SecretAwareJwtVerifier("SECRET");
    Executable parseToken = () -> jwtVerifier.parseToken(token);

    //then
    Assertions.assertThrows(InvalidTokenException.class, parseToken);
  }

  @Test
  public void shouldProduceClaimsOnCorrectToken()
  {
    //given
    String token =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ._XEngvIuxOcA-j7y_upRUbXli4DLToNf7HxH1XNmxSc";
    SecretAwareJwtVerifier jwtVerifier = new SecretAwareJwtVerifier("SECRET");

    //when
    Claims claims = jwtVerifier.parseToken(token);

    //then
    assertAll(
        () -> assertEquals("1234567890", claims.getSubject()),
        () -> assertEquals("John Doe", claims.get("name")),
        () -> assertEquals(1516239022, claims.getIssuedAt().getTime() / 1000));
  }

  @ParameterizedTest(name = "Should {0}")
  @MethodSource("argsForShouldProduceExceptionalAccessDecision")
  public void shouldProduceExceptionalAccessDecision(String name, Executable accessDecision,
                                                     Class<? extends Exception> expectedException)
  {

    //then
    Assertions.assertThrows(expectedException, accessDecision);
  }

  public Stream<Arguments> argsForShouldProduceExceptionalAccessDecision()
  {
    return Stream.of(
        Arguments.of(
            "return exceptional if has no access",
            (Executable) () -> new SecretAwareJwtVerifier("SECRET")
                .hasAccess(createClaims(), new String[]{"ADMIN"})
                .throwUnderlyingException(),
            NoAccessException.class
        )
    );
  }

  private Claims createClaims()
  {
    Claims claims = new DefaultClaims();
    claims.put(ROLES, "USER");
    claims.setExpiration(new Date(Instant.now().plus(500, ChronoUnit.HOURS).toEpochMilli()));
    return claims;
  }

  private Claims createExpiredClaims()
  {
    Claims claims = new DefaultClaims();
    claims.put(ROLES, "USER");
    claims.setExpiration(new Date(Instant.now().minus(100, ChronoUnit.HOURS).toEpochMilli()));
    return claims;
  }*/
}