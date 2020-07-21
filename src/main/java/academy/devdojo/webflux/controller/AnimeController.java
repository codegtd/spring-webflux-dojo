package academy.devdojo.webflux.controller;

import academy.devdojo.webflux.entity.Anime;
import academy.devdojo.webflux.service.IAnimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequestMapping("animes")
public class AnimeController{

    @Qualifier("service")
    @Autowired
    IAnimeService service;

    @GetMapping
    @ResponseStatus(OK)
    public Flux<Anime> listAll(){
        return service.findAll();
    }

    @GetMapping("{id}")
    @ResponseStatus(OK)
    public Mono<Anime> listAll(@PathVariable int id){
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Mono<Anime> save(@Valid @RequestBody Anime anime){
        return service.save(anime);
    }

    @PostMapping("saveall_rollback")
    @ResponseStatus(CREATED)
    public Flux<Anime> saveallrollback(@RequestBody List<Anime> animes){
        return service.saveallrollback(animes);
    }

    @PutMapping(path = "{id}")
    @ResponseStatus(NO_CONTENT)
    public Mono<Void> update(@PathVariable int id ,@Valid @RequestBody Anime anime){
        return service.update(anime.withId(id));
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(NO_CONTENT)
    public Mono<Void> delete(@PathVariable int id){
        return service.delete(id);
    }
}
