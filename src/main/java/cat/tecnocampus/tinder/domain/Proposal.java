package cat.tecnocampus.tinder.domain;

public class Proposal {
    private String target;
    private boolean matched;

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
}
