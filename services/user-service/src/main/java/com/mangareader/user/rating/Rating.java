package com.mangareader.user.rating;

import com.mongodb.lang.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ratings")
@AllArgsConstructor
@Data
public class Rating {

    @Id
    private final String id;

    @NotNull
    private final String userId;

    @NotNull
    private final String mangaId;

    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private final Integer rating;

    @Nullable
    private final String comment;

}
