package cat.tecnocampus.tinder.domain;

import java.time.LocalDate;

public class Proposal {
    private String target;
    private boolean matched;
    private LocalDate creationDate;
    private LocalDate matchDate;

    public Proposal() {
    }

    public Proposal(String target) {
        this.target = target;
        creationDate = LocalDate.now();
        matched = false;
        matchDate = null;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
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
