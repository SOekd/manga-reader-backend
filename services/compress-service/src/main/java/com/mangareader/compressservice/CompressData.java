package com.mangareader.compressservice;

import lombok.Data;

public record CompressData(String filename, byte[] bytes) {

}
