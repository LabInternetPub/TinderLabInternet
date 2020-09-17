package cat.tecnocampus.tinder.api;

import cat.tecnocampus.tinder.application.TinderController;
import cat.tecnocampus.tinder.application.dto.ProfileDTO;
import com.google.gson.Gson;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;

@RestController
@RequestMapping("/teaching")
@Validated
public class TeachingPourposesRestController {
    TinderController tinderController;

    public TeachingPourposesRestController(TinderController tinderController) {
        this.tinderController = tinderController;
    }

    @GetMapping("/int/{i}")
    public int getInt(@PathVariable @Max(50) int i) {
        return i;
    }

    @PostMapping("/profilesString")
    public String addProfile(@RequestBody String profile) {
        Gson gson = new Gson();

        ProfileDTO user=gson.fromJson(profile, ProfileDTO.class);
        tinderController.addProfile(user);;
        return gson.toJson(user);
    }

}
