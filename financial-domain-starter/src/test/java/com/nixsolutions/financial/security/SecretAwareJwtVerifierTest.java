package com.nixsolutions.financial.security;

import static com.nixsolutions.financial.security.SecurityConstants.ROLE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import com.nixsolutions.financial.security.exception.InvalidTokenException;
import com.nixsolutions.financial.security.exception.NoAccessException;
import com.nixsolutions.financial.security.exception.TokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SecretAwareJwtVerifierTest
{
  @Test
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
            "return exceptional if expired",
            (Executable) () -> new SecretAwareJwtVerifier("SECRET")
                .hasAccess(createExpiredClaims(), "USER")
                .throwUnderlyingException(),
            TokenExpiredException.class
        ),
        Arguments.of(
            "return exceptional if has no access",
            (Executable) () -> new SecretAwareJwtVerifier("SECRET")
                .hasAccess(createClaims(), "ADMIN")
                .throwUnderlyingException(),
            NoAccessException.class
        )
    );
  }

  private Claims createClaims()
  {
    Claims claims = new DefaultClaims();
    claims.put(ROLE, "USER");
    claims.setExpiration(new Date(Instant.now().plus(500, ChronoUnit.HOURS).toEpochMilli()));
    return claims;
  }

  private Claims createExpiredClaims()
  {
    Claims claims = new DefaultClaims();
    claims.put(ROLE, "USER");
    claims.setExpiration(new Date(Instant.now().minus(100, ChronoUnit.HOURS).toEpochMilli()));
    return claims;
  }
}