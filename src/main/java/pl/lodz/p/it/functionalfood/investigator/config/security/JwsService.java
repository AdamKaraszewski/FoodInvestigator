package pl.lodz.p.it.functionalfood.investigator.config.security;

import pl.lodz.p.it.functionalfood.investigator.dto.SignableDTO;
import pl.lodz.p.it.functionalfood.investigator.dto.accountmoduleDTO.accountDTOs.AccountDTO;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwsService {

    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    public String signAccountDTO(AccountDTO account) {
        return Jwts.builder()
                .claim("id", account.getId())
                .claim("version", account.getVersion())
                .signWith(SECRET_KEY)
                .compact();
    }

    public String signSignableDTO(SignableDTO dto) {
        return Jwts.builder()
                .claim("id", dto.getId())
                .claim("version", dto.getVersion())
                .signWith(SECRET_KEY)
                .compact();
    }
}
