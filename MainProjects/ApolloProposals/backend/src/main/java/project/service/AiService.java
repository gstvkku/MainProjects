package project.service;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import project.VectorStore;
import project.dto.RequestDto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AiService {
    @Value("${openai.api.key}")
    private String apiKey;
    @Value("classpath:/templates/promptAi.st")
    private Resource functionPromptTemplate;
    @Autowired
    private VectorStore vectorStore;
    private final ChatClient chatClient;
    private final RequestService requestService;

    public AiService(ChatClient chatClient, RequestService requestService) {
        this.chatClient = chatClient;
        this.requestService = requestService;
    }

    public Generation getChatResponse(RequestDto requestContent, List<File> files) {

        String tipForBase = "The Monterey County Convention & Visitors Bureau (MCCVB) is seeking a full-\n" +
                "service agency to come on board as an agency of record, providing creative and \n" +
                "media buying services to develop the Monterey County brand and promote tourism \n" +
                "to the County. The selected agency will assist in the development and execution of \n" +
                "effective, integrated marketing programs that: ";

        List<String> baseContent = vectorStore.search(tipForBase, 1);
        List<String> filesContent = new ArrayList<>();
        String oldRequests = getPreviousRequests(requestContent.getCustomerId());

        // Se arquivos forem enviados, atualiza o vetor e faz a busca
        if (files != null && !files.isEmpty()) {
            vectorStore.update(files); // Atualiza o vetor com os novos arquivos
            filesContent = vectorStore.search(requestContent.toString(), files.size());
        } else {
            System.out.println("Nenhum arquivo foi enviado. Processando apenas o RequestDto.");
        }

        // Cria o prompt a partir do template
        PromptTemplate promptTemplate = new PromptTemplate(functionPromptTemplate);
        Prompt prompt = promptTemplate.create(Map.of(
                "input", requestContent,
                "documents", String.join("\n", filesContent),
                "basedocument", String.join("\n", baseContent),
                "previousrequests", String.join("\n", oldRequests)
        ));

        System.out.println("HEEEYYEYEYEYEYEYEYEYEYE");
        System.out.println(oldRequests);
        System.out.println("HEEEYYEYEYEYEYEYEYEYEYE");

        // Realiza a chamada ao ChatClient
        return chatClient.call(prompt).getResult();
    }

    public String getPreviousRequests(Long customerId) {

        List<RequestDto> previousRequests = requestService.getRequestsByUser(customerId);
        StringBuilder requestsToAi = new StringBuilder();
        for (RequestDto requestDto : previousRequests) {
            String response = "response" + requestDto.getId() + ": " + requestDto.getResponse();
            String evaluation = "client evaluation for response" + requestDto.getId() + ": " + requestDto.getEvaluation();
            requestsToAi.append(response)
                    .append("\n")
                    .append(evaluation)
                    .append("\n\n");
        }

        return requestsToAi.toString();
    }

}