package cat.tecnocampus.tinder.api;

import cat.tecnocampus.tinder.application.QuoteController;
import cat.tecnocampus.tinder.application.dto.quote.ValueDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/quotes")
@RestController
public class QuoteRestController {
    private QuoteController quoteController;

    public QuoteRestController(QuoteController quoteController) {
        this.quoteController = quoteController;
    }

    @GetMapping("/{id}")
    public ValueDTO getQuoteId(@PathVariable Long id) {
        return quoteController.getSingleQuote(id);
    }

    @GetMapping("/random")
    public ValueDTO getQuoteRandom() {
        return quoteController.getRandomQuote();
    }

    @GetMapping("")
    public List<ValueDTO> getQuotes() {
        return quoteController.getQuotes();
    }
}
