import com.kleknersrevice.templates.Service.Impl.UserDetailServiceImpl
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

internal object TokenAuthenticationService {
    val EXPIRATIONTIME: Long = 864000000 // 10 days
    val SECRET =
        "OBFFRmPkj6H1Txf4Qu8vCcQyEsvqfdso8lkYBCcDIwLsrdHZpVmvY1AOz6fY6de9J14AMju8vCcQyEsvqfdso8lkYBvY1AOz6fY6de9J1"
    val TOKEN_PREFIX = "Bearer"
    val HEADER_STRING = "Authorization"

    fun addAuthentication(res: HttpServletResponse, username: String) {
        val JWT = Jwts.builder()
            .setSubject(username)
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

            return if (userName != null){
                val user = userDetailServiceImpl.loadUserByUsername(userName)
                UsernamePasswordAuthenticationToken(userName, null, user?.authorities)
            }
            else
                null
        }
        return null
    }
}