package academy.devdojo.webflux.repository;

import academy.devdojo.webflux.entity.UsersPasswords;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UsersAccessRepository extends ReactiveCrudRepository<UsersPasswords, Integer> {

    Mono<UsersPasswords> findByUsername(String username);

}
