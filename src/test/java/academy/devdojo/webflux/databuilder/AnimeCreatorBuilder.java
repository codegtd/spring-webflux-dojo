package academy.devdojo.webflux.databuilder;

import academy.devdojo.webflux.entity.Anime;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;

import java.util.Locale;

@Builder
@Getter
public class AnimeCreatorBuilder {

    private Anime anime;

//    private static Faker faker = new Faker(new Locale("en-CA.yml"));
//
//    @Default
//    private static String randomFakeName = faker.name().fullName();


    public static AnimeCreatorBuilder animeWithName() {
        Anime fakeNameAnime = new Anime();
//        fakeNameAnime.setName(randomFakeName);
        return AnimeCreatorBuilder.builder().anime(fakeNameAnime).build();
    }

    public Anime create() {
        return this.anime;
    }
}

