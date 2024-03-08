package com.mangareader.manga.chapter;

import com.mangareader.manga.manga.Manga;
import com.mongodb.lang.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chapters")
@AllArgsConstructor
@Data
public class Chapter {

    @Id
    @NotNull
    private String id;

    @NotNull
    private String name;

    @NotNull
    private String cloudFileReference;

    @Nullable
    private String translatorId;

    @NotNull
    private Boolean available;


}
