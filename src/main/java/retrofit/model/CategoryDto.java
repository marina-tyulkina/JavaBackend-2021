package retrofit.model;

import lombok.*;

import java.io.Serializable;

@Getter

public enum CategoryDto implements Serializable {

    FOOD(1, "Food"),
    ELECTRONIC(2, "Electronic");

    private final long id;
    private final String title;

    CategoryDto(long id, String title) {
        this.id = id;
        this.title = title;
    }

}
