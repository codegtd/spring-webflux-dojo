package academy.devdojo.webflux.service;

import academy.devdojo.webflux.GlobalTestConfig;
import academy.devdojo.webflux.databuilder.AnimeCreatorBuilder;
import academy.devdojo.webflux.entity.Anime;
import academy.devdojo.webflux.repository.AnimeRepository;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@DataR2dbcTest
public class AnimeServiceTest extends GlobalTestConfig {

    @InjectMocks
    private AnimeService service;

    @Mock
    private AnimeRepository repo;

    Anime anime = AnimeCreatorBuilder.animeWithName().create();


    @Test
    public void blockHoundWorks() {
        try {
            FutureTask<?> task = new FutureTask<>(() -> {
                Thread.sleep(0);
                return "";
            });

            Schedulers.parallel().schedule(task);

            task.get(10 ,TimeUnit.SECONDS);
//            Assert.fail("should fail");
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
//            Assert.assertTrue("detected" ,e.getCause() instanceof BlockingOperationError);
        }
    }

}
