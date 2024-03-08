package com.mangareader.manga.gender;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "gender")
@Data
@AllArgsConstructor
public class Gender {

    @Id
    @NotNull
    private final String id;

    @NotNull
    private String name;

    @NotNull
    private Boolean adult;

}
