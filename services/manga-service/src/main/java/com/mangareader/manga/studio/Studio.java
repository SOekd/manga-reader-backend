package com.mangareader.manga.studio;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "studios")
@AllArgsConstructor
@Data
public class Studio {

    @Id
    private final String id;

    @NotNull
    private final String name;

    @NotNull
    private final String country;

}
