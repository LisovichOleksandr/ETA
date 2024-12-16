package ua.apparatus.manager.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ua.apparatus.manager.client.payload.NewVerbPayloadClient;
import ua.apparatus.manager.entity.Verb;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class RestClientVerbRestClient implements VerbRestClient {

    // Способ доступа к типам которие здесь обьявленныйе
    // Ето нужно для того чтобы JACSON БИБЛИОТЕКА КОТОРАЯ ПАРСИТ JSON строки и преобразует их в офмф обьекты
    // могла понять что  у нас список  Verb
    private static final ParameterizedTypeReference<List<Verb>> VERB_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {};

    private final RestClient restClient;

    @Override
    public List<Verb> findAllVerbs() {
        return this.restClient
                .get()
                .uri("/catalogue-api/verbs")
                .retrieve()
                .body(VERB_TYPE_REFERENCE);
    }

    @Override
    public Verb createVerb(Verb verb) {
        try {
            return this.restClient
                    .post()
                    .uri("/catalogue-api/verbs")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewVerbPayloadClient(verb.getInfinitive(), verb.getVerb_v2(), verb.getVerb_v3(),
                            verb.getIng(), verb.getTranslate_ua()))
                    .retrieve()
                    .body(Verb.class);
        } catch (HttpClientErrorException.BadRequest exception){
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<Verb> findVerb(int verbId) {
        try {
            return Optional.ofNullable(this.restClient.get()
                    .uri("/catalogue-api/verbs/{verbId}", verbId)
                    .retrieve()
                    .body(Verb.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateVerb(Verb verb) {
        try {
            this.restClient
                    .patch()
                    .uri("/catalogue-api/verbs/{verbId}", verb.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewVerbPayloadClient(verb.getInfinitive(), verb.getVerb_v2(), verb.getVerb_v3(),
                            verb.getIng(), verb.getTranslate_ua()))
                    .retrieve()
                    .toBodilessEntity(); // Можна вызвать если требуется доступ к ответу
        } catch (HttpClientErrorException.BadRequest exception){
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteVerb(int verbId) {
        try {
            this.restClient
                    .delete()
                    .uri("/catalogue-api/verbs/{verbId}", verbId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException();
        }
    }
}
