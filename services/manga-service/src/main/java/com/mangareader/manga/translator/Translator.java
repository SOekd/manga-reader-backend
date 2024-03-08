package com.mangareader.manga.translator;

import com.mangareader.manga.language.Language;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "translators")
@AllArgsConstructor
@Data
public class Translator {

    @Id
    private final String id;

    @NotNull
    private final String scan;

    @NotNull
    private Language language;

    @Pattern(regexp = "https://.*")
    @Null
    private String scanUrl;

}
