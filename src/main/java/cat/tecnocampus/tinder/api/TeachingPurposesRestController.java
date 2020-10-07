package cat.tecnocampus.tinder.api;

import cat.tecnocampus.tinder.api.frontendException.IncorrectRESTParameter;
import cat.tecnocampus.tinder.application.ProfileDAO;
import cat.tecnocampus.tinder.application.TinderController;
import cat.tecnocampus.tinder.application.dto.ProfileDTO;
import com.google.gson.Gson;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.RowMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.Max;
import java.util.List;

@RestController
@RequestMapping("/teaching")
@Validated
public class TeachingPurposesRestController {
    private TinderController tinderController;

    public TeachingPurposesRestController(TinderController tinderController) {
        this.tinderController = tinderController;
    }

    /**************** Explain simple GET ****************************/
    @GetMapping("/profiles")
    public List<ProfileDTO> getProfiles() {
        return tinderController.getProfilesEager();
    }

    /**************** Explain simple GET with a path variable ****************************/
    @GetMapping("/profiles/{id}")
    public ProfileDTO getProfile(@PathVariable String id) throws Exception {
        ProfileDTO user = tinderController.getProfileEager(id);
        return user;
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


    /*******************************************
     * DANGER ZONE. COMPLETELY FORBIDDEN THAT THE FRONT-END LAYER TALKS DIRECTLY TO THE DATABASE
     */

   // Forbidden to inject a dependency directly to the attribute
    @Autowired
    private JdbcTemplate jdbcTemplate;

    RowMapperImpl<ProfileDTO> profileRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newRowMapper(ProfileDTO.class);

    @GetMapping("/profilesProblems/{id}")
    public List<ProfileDTO> profilesProblems(@PathVariable String id) {
        final String queryProfilesLazy = "select id, email, nickname, gender, attraction, passion from tinder_user where id = " + id;
        return jdbcTemplate.query(queryProfilesLazy, profileRowMapper);
    }



}
