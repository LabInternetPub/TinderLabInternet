package cat.tecnocampus.tinder.application.aop;

import cat.tecnocampus.tinder.application.dto.ProfileDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class LoggerAdvice {
	
    private static final Logger logger = LoggerFactory.getLogger(LoggerAdvice.class);


    //A pointcut that matches one single method
    @Pointcut("execution(* cat.tecnocampus.tinder.application.TinderController.getProfilesLazy(..))")
    public void pointcutListProfilesLazy() {}

    //Before advice of a pointcut
    @Before("pointcutListProfilesLazy()")
    public void beforeListUsers() {
        logger.info("Going to list all profiles in a lazy fashion");
    }

    //After advice of a pointcut
    @After("pointcutListProfilesLazy()")
    public void afterListUsers() {
        logger.info("Already listed all profiles in a lazy fashion");
    }


    //A pointcut that matches all methods having the word "Likes" in any position of methods' name
    @Pointcut("execution(* cat.tecnocampus.tinder.application.TinderController.*Likes*(..))")
    public void pointcutLikes() {}

    //Before advice of a pointcut
    @Before("pointcutLikes()")
    public void beforeDealingNotes() {
        logger.info("Going to deal with likes");
    }

    //A pointcut that matches all methods returning a List<ProfileDTO> and its name ending with "Eager"
    @Pointcut("execution(public java.util.List<cat.tecnocampus.tinder.application.dto.ProfileDTO> cat.tecnocampus.tinder.application.TinderController.*Eager(..))")
    public void listEagerProfiles() {}

    //Around advice. Note that this method must return what the proxied method is supposed to return
    @Around("listEagerProfiles()")
    public List<ProfileDTO> dealRequestParam(ProceedingJoinPoint jp) {

        try {
            logger.info("Before showing notes");
            //note that showUserRequestParameter is proxied and it must return a string
            // representing the thymeleaf file name
            List<ProfileDTO> res = (List<ProfileDTO>) jp.proceed();
            logger.info("Going to list all profiles in an eager fashion");
            return res;
        } catch (Throwable throwable) {
            logger.info("Already listed all profiles in an eager fashion");
            throwable.printStackTrace();
            return new ArrayList<ProfileDTO>();
        }
    }

    //Getting the parameters of the proxied method
    @Pointcut("execution(* cat.tecnocampus.tinder.application.TinderController.getCandidates(..)) && args(id)")
    public void showCandidatesPointcut(String id) {}

    @Before("showCandidatesPointcut(id)")
    public void showUserAdvice(String id) {
        logger.info("Going to show candidates for: " + id);
    }

}
