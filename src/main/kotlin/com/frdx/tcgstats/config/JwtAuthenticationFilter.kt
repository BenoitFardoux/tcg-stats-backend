package com.frdx.tcgstats.config

import com.frdx.tcgstats.utils.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.lang.NonNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver
@Component
class JwtAuthenticationFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var jwtService: JwtService

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Autowired
    private lateinit var handlerExceptionResolver: HandlerExceptionResolver

    override fun doFilterInternal(
        @NonNull request: HttpServletRequest,
        @NonNull response: HttpServletResponse,
        @NonNull filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        if (authHeader.isNullOrEmpty() || !authHeader.startsWith("Bearer ")) {
            logger.debug("Authorization header is missing or invalid")
            filterChain.doFilter(request, response)
            return
        }

        try {
            val jwt = authHeader.substring(7)
            logger.debug("Processing JWT: $jwt")

            val userEmail: String = jwtService.extractUsername(jwt)
            if (userEmail.isEmpty()) {
                logger.warn("JWT does not contain a valid username")
                filterChain.doFilter(request, response)
                return
            }

            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication == null || authentication.name != userEmail) {
                val userDetails = userDetailsService.loadUserByUsername(userEmail)
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    val authToken = UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.authorities
                    )
                    authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authToken
                    logger.debug("Authenticated user: $userEmail")
                } else {
                    logger.warn("Invalid JWT token for user: $userEmail")
                }
            }

            filterChain.doFilter(request, response)
        } catch (exception: Exception) {
            logger.error("JWT authentication failed: ${exception.message}", exception)
            handlerExceptionResolver.resolveException(request, response, null, exception)
        }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.requestURI
        val method = request.method
        val allowedPaths = when (method) {
            "GET" -> SecurityConfiguration.PUBLIC_GET_REQUEST_MANAGER
            "POST" -> SecurityConfiguration.PUBLIC_POST_REQUEST_MANAGER
            else -> emptyArray()
        }

        return allowedPaths.any { allowedPath ->
            val sanitizedPath = allowedPath.replace("/*", "").replace("/**", "")
            path.contains(sanitizedPath)
        }
    }
}
