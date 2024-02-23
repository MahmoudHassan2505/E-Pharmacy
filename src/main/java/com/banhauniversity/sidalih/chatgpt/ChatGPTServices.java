package com.banhauniversity.sidalih.chatgpt;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class ChatGPTServices {

    private String OPEN_AI_URL = "https://api.openai.com/v1/completions";
    private String OPEN_AI_KEY = "Bearer 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";

    public String processSearch(Request request) throws IOException {
        String url = OPEN_AI_URL;
        HttpPost httpPost = new HttpPost(url);

        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("Authorization", OPEN_AI_KEY);

        Gson gson = new Gson();
        ChatGPTRequest chatGPTRequest = new ChatGPTRequest(request.getQuery());
        String body = gson.toJson(chatGPTRequest);


        log.info("body" + body);

        try {
            StringEntity entity = new StringEntity(body);
            httpPost.setEntity(entity);

            try (CloseableHttpClient httpClient = HttpClients.custom().build(); CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {

                String responseBody = EntityUtils.toString(httpResponse.getEntity());

                log.info("response" + responseBody);

                ChatGPTResponse chatGPTResponse = gson.fromJson(responseBody, ChatGPTResponse.class);
                return chatGPTResponse.getChoices().get(0).getText();
            } catch (Exception e) {
                return e.getMessage();
            }
        } catch (Exception e) {
            return e.getMessage();
        }


    }
}
