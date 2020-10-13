package cat.tecnocampus.tinder.application;

import cat.tecnocampus.tinder.application.dto.quote.QuoteCTO;
import cat.tecnocampus.tinder.application.dto.quote.ValueCTO;
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

    public ValueCTO getSingleQuote(Long id) {
        var quote = restTemplate.getForObject(url + "/{id}", QuoteCTO.class, Long.toString(id));
        return quote.getValue();
    }

    public ValueCTO getRandomQuote() {
        var quote = restTemplate.getForEntity(url + "/random", QuoteCTO.class);
        return quote.getBody().getValue();
    }

    public List<ValueCTO> getQuotes() {
        var quotes = restTemplate.getForEntity(url, QuoteCTO[].class);
        return Arrays.asList(quotes.getBody()).stream().map(QuoteCTO::getValue).collect(Collectors.toList());
    }
}
