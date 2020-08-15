package academy.devdojo.webflux.service;

import academy.devdojo.webflux.entity.Anime;
import academy.devdojo.webflux.repository.AnimeRepository;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Qualifier("service")
@Service
public class AnimeService implements IAnimeService {

    @Autowired
    private AnimeRepository repo;

    @Override
    public Flux<Anime> findAll() {
        return repo.findAll();
    }

    @Override
    public Mono<Anime> findById(int id) {
        return repo
                .findById(id)
                .switchIfEmpty(monoReponseStatusNotFoundException());
    }

    @Override
    public Mono<Anime> save(Anime anime) {
        return repo.save(anime);
    }

    @Override
    public Mono<Void> update(Anime anime) {
        return findById(anime.getId())
                .flatMap(repo::save)
                .then();
    }

    @Override
    public Mono<Void> delete(int id) {
        return findById(id)
                .flatMap(repo::delete);
    }

    @Transactional
    @Override
    public Flux<Anime> saveallrollback(List<Anime> animes) {
        return repo.saveAll(animes)
                   .doOnNext(this::throwResponseStatusExceptionWhenEmptyName);
    }

    private void throwResponseStatusExceptionWhenEmptyName(Anime anime) {
        if (StringUtil.isNullOrEmpty(anime.getName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Name");
    }

    public <T> Mono<T> monoReponseStatusNotFoundException() {
        return Mono
                .error(new ResponseStatusException(HttpStatus.NOT_FOUND,"Anime not found"));
    }
}
