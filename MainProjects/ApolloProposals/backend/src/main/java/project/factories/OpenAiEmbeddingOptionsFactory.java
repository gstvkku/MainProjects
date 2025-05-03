package project.factories;

import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.beans.factory.FactoryBean;

public class OpenAiEmbeddingOptionsFactory implements FactoryBean<OpenAiEmbeddingOptions> {
    private String model;

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public OpenAiEmbeddingOptions getObject() {
        return OpenAiEmbeddingOptions.builder().withModel(model).build();
    }

    @Override
    public Class<?> getObjectType() {
        return OpenAiEmbeddingOptions.class;
    }
}
