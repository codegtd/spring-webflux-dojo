package academy.devdojo.webflux.service;

import academy.devdojo.webflux.entity.Anime;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


public interface IAnimeService {
    Flux<Anime> findAll();

    Mono<Anime> findById(int id);

    Mono<Anime> save(Anime anime);

    Mono<Void> update(Anime anime);

    Mono<Void> delete(int id);

    Flux<Anime> saveallrollback(List<Anime> animes);
}
