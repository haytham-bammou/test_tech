package com.example.demo.service;

import com.example.demo.domain.ResponseToStore;
import com.example.demo.domain.UserRequest;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Services {
    ResponseToStore sendRequest(UserRequest userRequest) throws IOException, InterruptedException, URISyntaxException;
    void writeTofile(ResponseToStore newResponse) throws Exception;
}
