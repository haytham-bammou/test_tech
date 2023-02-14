package com.example.demo.api;

import com.example.demo.domain.*;
import com.example.demo.service.Services;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatGptController {
    private final Services services;
    @PostMapping("/request")
    public ResponseEntity<ResponseToStore> callChatGpt(@RequestBody UserRequest userRequest) throws Exception{
        ResponseToStore responseToStore = services.sendRequest(userRequest);
        services.writeTofile(responseToStore);
        return ResponseEntity.ok().body(responseToStore);
    }
}
