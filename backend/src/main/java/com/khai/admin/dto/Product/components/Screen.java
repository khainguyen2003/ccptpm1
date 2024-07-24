package com.khai.admin.dto.Product.components;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class Screen implements Serializable {
    @JsonProperty("size")
    private String size;
    @JsonProperty("resolution")
    private String resolution;
    @JsonProperty("fps")
    private String fps;
    @JsonProperty("tech")
    private List<String> tech;
}
