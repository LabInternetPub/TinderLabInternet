package cat.tecnocampus.tinder.domain;

import java.time.LocalDate;

public class Proposal {
    private String target;
    private boolean matched;
    private LocalDate date;

    public Proposal() {
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
        return date;
    }

    public void setCreationDate(LocalDate date) {
        this.date = date;
    }
}
