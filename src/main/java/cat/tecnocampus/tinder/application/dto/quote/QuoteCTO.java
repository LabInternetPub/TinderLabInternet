package cat.tecnocampus.tinder.application.dto.quote;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteCTO {

    private String type;
    private ValueCTO valueCTO;

    public QuoteCTO() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ValueCTO getValue() {
        return valueCTO;
    }

    public void setValue(ValueCTO valueCTO) {
        this.valueCTO = valueCTO;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "type='" + type + '\'' +
                ", value=" + valueCTO +
                '}';
    }
}