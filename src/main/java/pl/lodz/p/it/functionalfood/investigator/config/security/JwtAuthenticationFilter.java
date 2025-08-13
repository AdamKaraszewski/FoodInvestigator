package pl.lodz.p.it.functionalfood.investigator.config.security;

import pl.lodz.p.it.functionalfood.investigator.authenticationmodule.repositories.AuthenticationModuleAccountRepository;
import pl.lodz.p.it.functionalfood.investigator.entities.Account;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private AuthenticationModuleAccountRepository accountRepository;

    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService, AuthenticationModuleAccountRepository accountRepository) {
        this.jwtService = jwtService;
        this.accountRepository = accountRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userLogin;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userLogin = jwtService.extractUsername(jwt);
        if (userLogin != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Account account = accountRepository.findByLogin(userLogin);
            if (jwtService.isTokenValid(jwt, account)) {
                User user = new User(account.getLogin(), account.getLogin(), List.of(
                        new SimpleGrantedAuthority("ROLE_" + account.getAccessLevel().getLevel()))
                );
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,
                        null,user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
