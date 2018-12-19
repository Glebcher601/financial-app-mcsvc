package com.nixsolutions.authorizationserver.security.jwt

/*import com.nixsolutions.authorizationserver.security.CustomUserDetailsService
import com.nixsolutions.authorizationserver.security.payload.BEARER_TOKEN
import org.apache.commons.lang3.StringUtils.EMPTY
import org.apache.commons.lang3.StringUtils.isNotEmpty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

const val AUTHORIZATION_HEADER = "Authorization"
const val BEARER_PREFIX = BEARER_TOKEN + " "

@Component
class JwtAuthenticationFilter : OncePerRequestFilter() {

  @Autowired
  private lateinit var tokenProvider: JwtTokenProvider

  @Autowired
  private lateinit var customUserDetailsService: CustomUserDetailsService;

  override fun doFilterInternal(request: HttpServletRequest,
                                response: HttpServletResponse,
                                filterChain: FilterChain) {
    val jwt = getJwtFromRequest(request)

    if (tokenProvider.validateToken(jwt)) {
      val userId = tokenProvider.getUserIdFromJWT(jwt)

      val userDetails = customUserDetailsService.loadUserDetailsById(userId)
      val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)

      authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
      SecurityContextHolder.getContext().authentication = authentication
    }

    filterChain.doFilter(request, response)
  }

  private fun getJwtFromRequest(request: HttpServletRequest): String {
    val bearerToken = request.getHeader(AUTHORIZATION_HEADER)
    return if (isNotEmpty(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      bearerToken.substring(BEARER_PREFIX.length, bearerToken.length)
    } else EMPTY
  }

}*/
