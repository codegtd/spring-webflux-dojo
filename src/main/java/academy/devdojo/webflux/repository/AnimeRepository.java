package academy.devdojo.webflux.repository;

import academy.devdojo.webflux.entity.Anime;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AnimeRepository extends ReactiveCrudRepository<Anime, Integer> {

    @Override
    Mono<Anime> findById(Integer id);
}
