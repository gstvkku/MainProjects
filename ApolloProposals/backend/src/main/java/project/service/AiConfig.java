package project.service;

import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import project.factories.OpenAiEmbeddingOptionsFactory;
import project.VectorStore;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiEmbeddingClient;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.retry.RetryUtils;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${ai.chat.model:gpt-4o}")
    private String chatModel;

    @Value("${ai.temperature:0.4}")
    private float temperature;

    @Value("${ai.embedding.model:text-embedding-3-small}")
    private String embeddingModel;

    @Bean
    public OpenAiApi openAiApi() {
        return new OpenAiApi(apiKey);
    }

    @Bean
    public ChatClient chatClient() {
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .withModel(chatModel)
                .withTemperature(temperature)
                .build();

        return new OpenAiChatClient(openAiApi(), options);
    }

    @Bean
    public OpenAiEmbeddingOptions openAiEmbeddingOptions() {
        OpenAiEmbeddingOptionsFactory factory = new OpenAiEmbeddingOptionsFactory();
        factory.setModel(embeddingModel);
        return factory.getObject();
    }

    @Bean
    public OpenAiEmbeddingClient embeddingClient() {
        return new OpenAiEmbeddingClient(
                openAiApi(),
                MetadataMode.EMBED,
                openAiEmbeddingOptions(),
                RetryUtils.DEFAULT_RETRY_TEMPLATE
        );
    }

    @Bean
    public SimpleVectorStore simpleVectorStore() {
        return new SimpleVectorStore(embeddingClient());
    }

    @Bean(initMethod = "init")
    public VectorStore vectorStore() {
        VectorStore store = new VectorStore();
        store.setSimpleVectorStore(simpleVectorStore());
        return store;
    }
}