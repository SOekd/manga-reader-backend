package com.mangareader.common.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("cloud_file")
@AllArgsConstructor
@Data
public class CloudFile {

    @Id
    private String id;

    private final CloudFileType cloudFileType;

    private final List<ChunkFile> chunkFiles;

}
