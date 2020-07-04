package academy.devdojo.webflux.service;

import academy.devdojo.webflux.entity.Anime;
import academy.devdojo.webflux.repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository repo;

    public Flux<Anime> findAll() {
        return repo.findAll();
    }

    public Mono<Anime> findById(int id) {
        return repo.findById(id).switchIfEmpty(monoReponseStatusNotFoundException());
    }

    public Mono<Anime> save(Anime anime) {
        return repo.save(anime);
    }

    public Mono<Void> update(Anime anime) {
        return findById(anime.getId())
                .flatMap(repo::save)
                .then();
    }

    public Mono<Void> delete(int id) {
        return findById(id)
                .flatMap(repo::delete);
    }

    public <T> Mono<T> monoReponseStatusNotFoundException() {
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND ,"Anime not found"));
    }
}
