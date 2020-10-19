package cat.tecnocampus.tinder.application;

import cat.tecnocampus.tinder.application.dto.quote.QuoteDTO;
import cat.tecnocampus.tinder.application.dto.quote.ValueDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuoteController {
    private final String url = "https://gturnquist-quoters.cfapps.io/api";
    private RestTemplate restTemplate;

    public QuoteController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ValueDTO getSingleQuote(Long id) {
        var quote = restTemplate.getForObject(url + "/{id}", QuoteDTO.class, Long.toString(id));
        return quote.getValue();
    }

    public ValueDTO getRandomQuote() {
        var quote = restTemplate.getForEntity(url + "/random", QuoteDTO.class);
        return quote.getBody().getValue();
    }

    public List<ValueDTO> getQuotes() {
        var quotes = restTemplate.getForEntity(url, QuoteDTO[].class);
        return Arrays.asList(quotes.getBody()).stream().map(QuoteDTO::getValue).collect(Collectors.toList());
    }
}
