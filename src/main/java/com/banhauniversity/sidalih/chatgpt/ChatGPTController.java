package com.banhauniversity.sidalih.chatgpt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/pharmacy/chatbot")
public class ChatGPTController {
    @Autowired ChatGPTServices chatGPTServices;
    @PostMapping
    public String search(@RequestBody Request request) throws IOException {
        return chatGPTServices.processSearch(request);
    }
}
