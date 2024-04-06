package com.banhauniversity.sidalih.chatgpt;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatGPTRequest {
    private String model = "gpt-3.5-turbo-instruct";
    private String prompt;
    private int temperature = 1;
    @SerializedName(value = "max_tokens")
    private int maxTokens = 100;

    public ChatGPTRequest(String prompt) {
        this.prompt = prompt;
    }
}
