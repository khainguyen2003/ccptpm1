package com.khai.admin.dto.Product.components;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class CPU implements Serializable {
    @JsonProperty("cpu_tech")
    private String cpu_tech;
    @JsonProperty("core_amount")
    private byte core_amount;
    @JsonProperty("thread_amount")
    private byte thread_amount;
    @JsonProperty("cpu_speed")
    private String cpu_speed;
    @JsonProperty("max_speed")
    private String max_speed;
    @JsonProperty("cache_size")
    private String cache_size;

    @Override
    public String toString() {
        return "CPU{" +
                "cpu_tech='" + cpu_tech + '\'' +
                ", core_amount=" + core_amount +
                ", thread_amount=" + thread_amount +
                ", cpu_speed='" + cpu_speed + '\'' +
                ", max_speed='" + max_speed + '\'' +
                ", cache_size='" + cache_size + '\'' +
                '}';
    }
}
