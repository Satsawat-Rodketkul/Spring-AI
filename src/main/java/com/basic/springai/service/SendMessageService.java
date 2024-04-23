package com.basic.springai.service;

import com.basic.springai.models.SendMessageRequestModel;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SendMessageService {

    private final VectorStore vectorStore;
    private final OllamaChatClient ollamaChatClient;

    public String sendMessage(SendMessageRequestModel message) {
        String prompt = """
                    This document from resume or CV contains personal information such as name, mail, address, education, skills, competencies and experience.
                    Use information from these {documents} to provide answer short and concise.
                    If the answer is more than one create numbered list for answer.
                    """;

        List<Document> listOfSimilarDocs = this.vectorStore.similaritySearch(message.getUserMessage());

        String docs = listOfSimilarDocs.stream().map(Document::getContent)
                .collect(Collectors.joining(System.lineSeparator()));

        Message systemMessage = new SystemPromptTemplate(prompt)
                .createMessage(Map.of ("documents", docs));

        UserMessage userMessage = new UserMessage(message.getUserMessage());
        Prompt promptList = new Prompt(List.of(systemMessage, userMessage));

        Generation generation = ollamaChatClient.call(promptList).getResult();
        return generation.getOutput().getContent();
    }
}