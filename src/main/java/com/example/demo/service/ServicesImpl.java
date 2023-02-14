package com.example.demo.service;

import com.example.demo.domain.ApiRequest;
import com.example.demo.domain.ApiResponse;
import com.example.demo.domain.ResponseToStore;
import com.example.demo.domain.UserRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ServicesImpl implements Services{
    @Value("${file_path}") private String path;
    @Value("${API_KEY}") private String API_KEY;
    @Override
    public ResponseToStore sendRequest(UserRequest userRequest) throws IOException, InterruptedException, URISyntaxException {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setPrompt(userRequest.getPrompt());
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(apiRequest);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI("https://api.openai.com/v1/completions"))
                .header("Authorization","Bearer "+API_KEY)
                .headers("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response =  httpClient.send(httpRequest , HttpResponse.BodyHandlers.ofString());
        ApiResponse apiResponse =  gson.fromJson(response.body(), ApiResponse.class);
        ResponseToStore responseToStore = new ResponseToStore(userRequest.getPrompt(),apiResponse.getChoices().get(0).getText());
        return  responseToStore;
    }

    @Override
    public void writeTofile(ResponseToStore newResponse) throws Exception{
        String oldContent="" , it;
        Gson gson = new Gson();
        try{
            File file = new File(path);
            if(!file.exists()) file.createNewFile();
            BufferedReader br = new BufferedReader(new FileReader(file));
            while((it = br.readLine())!=null){
                oldContent+=it;
            }
            Type listType = new TypeToken<ArrayList<ResponseToStore>>(){}.getType();
            ArrayList<ResponseToStore> list = gson.fromJson(oldContent, listType);
            if(list == null) list = new ArrayList<>();
            list.add(newResponse);
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(gson.toJson(list));
            writer.close();
        }catch (Exception exception){}
    }
}
