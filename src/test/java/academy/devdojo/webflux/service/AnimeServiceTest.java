package academy.devdojo.webflux.service;

import academy.devdojo.webflux.GlobalTestConfig;
import academy.devdojo.webflux.databuilder.AnimeCreatorBuilder;
import academy.devdojo.webflux.entity.Anime;
import academy.devdojo.webflux.repository.AnimeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.mockito.BDDMockito.anyInt;
import static org.mockito.BDDMockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnimeServiceTest extends GlobalTestConfig {

    @InjectMocks
    private AnimeService service;

    @Mock
    private AnimeRepository repo;

    Anime anime = AnimeCreatorBuilder.animeWithName().create();
    Anime animeCompare = AnimeCreatorBuilder.animeWithNameAndValidid().create();

    @Before
    public void setUpLocal() {
        when(service.findAll()).thenReturn(Flux.just(anime));
        when(repo.findById(Mockito.anyInt())).thenReturn(Mono.just(anime));
        when(repo.save(Mockito.any())).thenReturn(Mono.just(anime));
        when(repo.delete(Mockito.any(Anime.class))).thenReturn(Mono.empty());
    }

    @Test
    public void findAll_devdojo() {
        StepVerifier.create(service.findAll())
                .expectSubscription()
                .expectNext(anime)
                .verifyComplete();
    }

    @Test
    public void findById() {
        StepVerifier.create(service.findById(anyInt()))
                .expectSubscription()
                .expectNext(anime)
                .verifyComplete();
    }

    @Test
    public void findById_Empty_Error() {
        when(repo.findById(Mockito.anyInt())).thenReturn(Mono.empty());

        StepVerifier
                .create(service.findById(anyInt()))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    public void save() {
        StepVerifier
                .create(service.save(anime))
                .expectSubscription()
                .expectNext(anime)
                .verifyComplete();
    }

    @Test
    public void update() {
        StepVerifier
                .create(service.update(animeCompare))
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    public void update_error_object_not_found() {
        when(repo.findById(anyInt())).thenReturn(Mono.empty());
        StepVerifier
                .create(service.update(animeCompare))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    public void delete() {
        StepVerifier
                .create(service.delete(anyInt()))
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    public void delete_Error() {
        when(repo.findById(Mockito.anyInt())).thenReturn(Mono.empty());

        StepVerifier
                .create(service.delete(anyInt()))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    public void blockHoundWorks() {
        try {
            FutureTask<?> task = new FutureTask<>(() -> {
                Thread.sleep(0);
                return "";
            });

            Schedulers.parallel().schedule(task);

            task.get(10 ,TimeUnit.SECONDS);
            Assert.fail("should fail");
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            Assert.assertTrue("detected" ,e.getCause() instanceof BlockingOperationError);
        }
    }
}
