package academy.devdojo.webflux.controller;

import academy.devdojo.webflux.entity.Anime;
import academy.devdojo.webflux.repository.AnimeRepository;
import academy.devdojo.webflux.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("animes")
public class AnimeController {

    private final AnimeService service;

    @GetMapping
    public Flux<Anime> listAll(){
        return service.findAll();
    }

    @GetMapping("{id}")
    public Mono<Anime> listAll(@PathVariable int id){
        return service.findById(id);
    }

    @PostMapping
    public Mono<Anime> save(@Valid @RequestBody Anime anime){
        return service.save(anime);
    }

    @PutMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> update (@PathVariable int id, @Valid @RequestBody Anime anime){
        return service.update(anime.withId(id));
    }
//    @PutMapping
//    public Mono<Void> update (@Valid @RequestBody Anime anime){
//        return service.update(anime.withId(id));

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete (@PathVariable int id){
        return service.delete(id);
    }
}
