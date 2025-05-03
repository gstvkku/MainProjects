package project.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.dto.RequestDto;
import project.service.AiService;
import project.service.RequestService;

import java.math.BigDecimal;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;
    private final RequestService requestService;

    public AiController(AiService aiService, RequestService requestService) {
        this.aiService = aiService;
        this.requestService = requestService;
    }

    @PostMapping("/{customerId}/generate-proposal")
    public ResponseEntity<Map<String, Object>> generateProposal(
            @PathVariable Long customerId,
            @RequestBody RequestDto requestDto, // JSON Payload
            @RequestParam(value = "files", required = false) List<MultipartFile> files // Optional files
    ) {
        try {
            // Setting the customerId in the requestDto
            requestDto.setCustomerId(customerId);

            // Convert received files to File objects, if they exist
            List<File> fileList = (files != null && !files.isEmpty())
                    ? convertMultipartFilesToFileList(files)
                    : new ArrayList<>();

            // Generate the AI response
            String response = aiService.getChatResponse(requestDto, fileList).getOutput().getContent();
            requestDto.setResponse(response);

            // Save the request using the request service
            RequestDto savedRequest = requestService.createRequest(requestDto);

            // Return a JSON with response and request id
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("response", response);
            responseBody.put("requestId", savedRequest.getId()); // Assuming `id` is the identifier for the request
            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();

            // Return error details in JSON format
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error processing request: " + e.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<File> convertMultipartFilesToFileList(List<MultipartFile> files) {
        return files.stream()
                .map(file -> {
                    try {
                        // Certifica-se de que o caminho do arquivo esteja correto
                        File convertedFile = new File("src/main/resources/files/" + file.getOriginalFilename());

                        // Cria o diretório se ele não existir
                        convertedFile.getParentFile().mkdirs();

                        // Salva o arquivo no diretório
                        file.transferTo(convertedFile);
                        System.out.println("Arquivo salvo: " + convertedFile.getAbsolutePath());
                        return convertedFile;

                    } catch (Exception e) {
                        System.err.println("Erro ao converter o arquivo: " + file.getOriginalFilename());
                        e.printStackTrace();
                        throw new RuntimeException("Erro ao converter arquivo.", e);
                    }
                })
                .collect(Collectors.toList());
    }
}