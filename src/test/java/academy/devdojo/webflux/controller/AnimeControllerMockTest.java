package academy.devdojo.webflux.controller;

import academy.devdojo.webflux.GlobalTestConfig;
import academy.devdojo.webflux.databuilder.AnimeCreatorBuilder;
import academy.devdojo.webflux.entity.Anime;
import academy.devdojo.webflux.service.AnimeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnimeControllerMockTest extends GlobalTestConfig {

    @InjectMocks
    private AnimeController controller;

    @Mock
    private AnimeService service;

    Anime anime = AnimeCreatorBuilder.animeWithName().create();
    Anime animeCompare = AnimeCreatorBuilder.animeWithNameAndValidid().create();

    @Before
    public void setUpLocal() throws Exception {
        when(service.findAll()).thenReturn(Flux.just(anime));
        when(service.findById(Mockito.anyInt())).thenReturn(Mono.just(anime));
        when(service.save(Mockito.any())).thenReturn(Mono.just(anime));
        when(service.delete(anyInt())).thenReturn(Mono.empty());
        when(service.update(Mockito.any())).thenReturn(Mono.empty());
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

    @Test
    public void findAll_devdojo() {
        StepVerifier
                .create(controller.listAll())
                .expectSubscription()
                .expectNext(anime)
                .verifyComplete();
    }

    @Test
    public void findById() {
        StepVerifier.create(controller.listAll(anyInt()))
                .expectSubscription()
                .expectNext(anime)
                .verifyComplete();
    }

    @Test
    public void save() {
        StepVerifier
                .create(controller.save(anime))
                .expectSubscription()
                .expectNext(anime)
                .verifyComplete();
    }

    @Test
    public void update() {
        StepVerifier
                .create(controller.update(anyInt(), animeCompare))
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    public void delete() {
        StepVerifier
                .create(controller.delete(1))
                .expectSubscription()
                .verifyComplete();
    }
}