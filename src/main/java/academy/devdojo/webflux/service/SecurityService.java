package academy.devdojo.webflux.service;

import academy.devdojo.webflux.repository.UsersAccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class SecurityService implements ISecurityService, ReactiveUserDetailsService {

    @Autowired
    private UsersAccessRepository _repo;

    public Mono<UserDetails> findByUsername(String username) {
        return _repo
                .findByUsername(username)
                .cast(UserDetails.class);
    }
}
