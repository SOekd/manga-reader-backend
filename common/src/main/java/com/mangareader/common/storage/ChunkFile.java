package com.mangareader.common.storage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Transient;

@AllArgsConstructor
@Data
public class ChunkFile {

    @NonNull
    private final String id;

    private final int order;

    private final String password;

    @NonNull
    private final String hash;

}
