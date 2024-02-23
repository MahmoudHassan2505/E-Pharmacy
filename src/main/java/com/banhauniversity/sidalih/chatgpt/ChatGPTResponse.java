package com.banhauniversity.sidalih.chatgpt;

import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatGPTResponse {
    private List<ChatGPTChoices> choices;

}
