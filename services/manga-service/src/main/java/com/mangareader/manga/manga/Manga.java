package com.mangareader.manga.manga;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "mangas")
@AllArgsConstructor
@Data
public class Manga {

    @Id
    @NotNull
    private final String id;

    @NotNull
    private final String mainTitle;

    @NotNull
    private final List<String> alternativeTitles;

    @NotNull
    private final String description;

    @NotNull
    private final byte[] mainImage;

    @NotNull
    private final List<byte[]> alternativeImages;

    @NotNull
    private MangaStatus status;

    @NotNull
    private final MangaType type;

    @NotNull
    private final List<String> studios;

    @NotNull
    private final List<String> gender;


    @NotNull
    private final List<String> authors;

    @NotNull
    private final List<String> artists;

    @CreatedBy
    @NotNull
    private final LocalDateTime uploadedAt;

    @NotNull
    private final LocalDate publishedAt;

    @NotNull
    @LastModifiedBy
    private LocalDateTime lastUpdatedAt;

}
