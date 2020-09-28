package cat.tecnocampus.tinder.domain;

import java.time.LocalDate;

public class Like {
    private Profile target;
    private boolean matched;
    private LocalDate creationDate;
    private LocalDate matchDate;

    public Like() {
    }

    public Like(Profile target) {
        this.target = target;
        creationDate = LocalDate.now();
        matched = false;
        matchDate = null;
    }

    public Profile getTarget() {
        return target;
    }

    public void setTarget(Profile target) {
        this.target = target;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
        this.matchDate = LocalDate.now();
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate date) {
        this.creationDate = date;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }
}
