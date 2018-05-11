package com.abuarquemf.jwtsec.security

import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter: GenericFilterBean() {

    override fun doFilter(request: ServletRequest?,
                          response: ServletResponse?,
                          chain: FilterChain?) {

        val authentication = TokenAuthenticationService
                .getAuthentication(request as HttpServletRequest)

        if(authentication != null) {
            SecurityContextHolder.getContext().authentication = authentication
            chain!!.doFilter(request, response)
        } else {
            (response as HttpServletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED)
        }
    }

}
