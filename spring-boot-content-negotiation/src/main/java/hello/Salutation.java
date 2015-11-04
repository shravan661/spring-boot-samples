package hello;

import javax.validation.constraints.Size;

public class Salutation {

    @Size(min = 4, max = 20)
    private String name;

    public Salutation() {}

    public Salutation(String content) {
        this.name = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
