package com.nixsolutions.financial.security;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SecretAwareJwtVerifierTest
{
  @Test
  public void hasAccess() throws Exception
  {
  }

  @ParameterizedTest(name = "Should {0}")
  @MethodSource("argsForParseToken")
  public void parseToken() throws Exception
  {
  }

  public Stream<Arguments> argsForParseToken()
  {
    return Stream.of(
        Arguments.of(
            ""
        )
    );
  }

  @Test
  public void shouldProduceExceptionOnWrongToken()
  {
    //given
    String token =
        "eyJ111111iJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ._XEngvIuxOcA-j7y_upRUbXli4DLToNf7HxH1XNmxSc";

    SecretAwareJwtVerifier jwtVerifier = new SecretAwareJwtVerifier("SECRET");

    //when
    try
    {
      jwtVerifier.parseToken(token);

    }
    catch (InvalidTokenException e)
    {

    }

    Assertions.fail("InvalidTokenException should be thrown");
  }

}