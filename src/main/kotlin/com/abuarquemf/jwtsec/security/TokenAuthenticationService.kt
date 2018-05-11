package com.abuarquemf.jwtsec.security

import com.abuarquemf.jwtsec.models.User
import com.google.gson.Gson
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jose.crypto.MACVerifier
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import org.springframework.security.core.Authentication
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

object TokenAuthenticationService {

    private val SECRET = "#$*(NBDUS)SMW<S_@ZR(002,,smspd[s]c-w88w)KKNzzafd1o`ue0ks./,d,?;lw,dk3mf9lnaknfubs"
    private val HEADER = "Authorization"

    fun addAuthentication(login: String, servletResponse: HttpServletResponse) {
        val sharedSecret = SECRET.toByteArray()
        val signer = MACSigner(sharedSecret)
        val claimsSet = JWTClaimsSet.Builder()
                .subject(login).build()
        val signedJWT = SignedJWT(JWSHeader(JWSAlgorithm.HS256), claimsSet)
        signedJWT.sign(signer)
        val token = signedJWT.serialize()
        servletResponse.addHeader("Token", token)
    }

    fun getAuthentication(servletRequest: HttpServletRequest): Authentication? {
        val givenToken = servletRequest.getHeader(HEADER)
        if(givenToken != null) {
            val signedJWT = SignedJWT.parse(givenToken)
            val sharedSecret = SECRET.toByteArray()
            if(signedJWT.verify(MACVerifier(sharedSecret))) {
                val user = Gson().fromJson<User>(SignedJWT.parse(givenToken)
                        .payload.toJSONObject().toString(), User::class.java)
                return UserAuthToken(user)
            }
        }
        return null
    }
}
