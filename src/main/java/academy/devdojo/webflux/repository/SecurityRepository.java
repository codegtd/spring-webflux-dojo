package academy.devdojo.webflux.repository;

import academy.devdojo.webflux.entity.SecurityEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface SecurityRepository extends ReactiveCrudRepository<SecurityEntity, Integer> {
    Mono<SecurityEntity> findByUsername(String username);
}
