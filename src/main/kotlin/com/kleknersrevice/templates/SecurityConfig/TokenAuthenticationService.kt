import com.kleknersrevice.templates.Service.Impl.UserDetailServiceImpl
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import java.util.*
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


internal object TokenAuthenticationService {
    val EXPIRATIONTIME: Long = 864000000 // 10 days
    val SECRET =
        "OBFFRmPkj6H1Txf4Qu8vCcQyEsvqfdso8lkYBCcDIwLsrdHZpVmvY1AOz6fY6de9J14AMju8vCcQyEsvqfdso8lkYBvY1AOz6fY6de9J1"
    val TOKEN_PREFIX = "Bearer"
    val HEADER_STRING = "Authorization"
    val AUTHORITIES_KEY = "Role"

    fun addAuthentication(res: HttpServletResponse, username: String, auth: Authentication) {
        val authorities = auth.authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        val JWT = Jwts.builder()
            .setSubject(username)
            .claim(AUTHORITIES_KEY, authorities)
            .setExpiration(Date(System.currentTimeMillis() + EXPIRATIONTIME))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact()
        res.addHeader(HEADER_STRING, "$TOKEN_PREFIX $JWT")
    }

    fun getAuthentication(request: HttpServletRequest, userDetailServiceImpl: UserDetailServiceImpl): Authentication? {
        val token = request.getHeader(HEADER_STRING)
        if (token != null) {
            // parse the token.
            val userName = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .body
                .subject
            val claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .body
            val authorities =
                claims.get(AUTHORITIES_KEY).toString().split(',').map { auth -> SimpleGrantedAuthority(auth) }
            return if (userName != null){
                val user = userDetailServiceImpl.loadUserByUsername(userName)
                val principal = User(userName, user!!.password, authorities)
                UsernamePasswordAuthenticationToken(principal, null, authorities)
            }
            else
                null
        }
        return null
    }
}