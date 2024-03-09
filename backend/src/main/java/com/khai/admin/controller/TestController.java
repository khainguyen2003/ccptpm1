package com.khai.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @GetMapping("/age")
    ResponseEntity<String> age(
            @RequestParam("yearOfBirth") int yearOfBirth) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");
        return new ResponseEntity<>(
                "Your age is " + yearOfBirth, headers,
                HttpStatus.OK);
    }
    @GetMapping("/hello")
    ResponseEntity<String> hello(
            @RequestParam(value = "name", required = false) String name,
            @RequestHeader("my-number") int myNumber,
            // get all header
            @RequestHeader Map<String, String> headersReq
//            @RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language,
    ) {
        headersReq.forEach((key, value) -> {
            log.info(String.format("Header '%s' = %s", key, value));
        });

        // body: "Hello World!"
        /* Các phương thức có thể
        BodyBuilder accepted();
        BodyBuilder badRequest();
        BodyBuilder created(java.net.URI location);
        HeadersBuilder<?> noContent();
        HeadersBuilder<?> notFound();
        BodyBuilder ok();
         */
        HttpHeaders headersRes = new HttpHeaders();
        headersRes.add("Custom-Header", "foo");
        if(name == null) {
            return ResponseEntity.badRequest()
                    .headers(headersRes)
                    .body("Hãy nhập tên");
        }
        return ResponseEntity.ok("Hello "+name+", age "+myNumber+"!");
    }
}
