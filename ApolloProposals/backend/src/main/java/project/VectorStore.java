package project;

import org.apache.tika.Tika;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

@Component
public class VectorStore {
    @Value("classpath:/templates/promptAi.st")
    private Resource functionPromptTemplate;
    private File baseDocument;
    @Autowired
    private SimpleVectorStore vectorStore;

    public void setSimpleVectorStore(SimpleVectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void init() {
        try {
            // Carrega o arquivo da pasta resources
            String resourcePath = "/baseDocuments/MCCVB_AOR_RFP_FINAL_2021_86bccca1-3682-4a3a-aa4f-43831d7b7058.pdf";
            InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(resourcePath);

            if (resourceStream == null) {
                throw new IllegalArgumentException("Resource not found: " + resourcePath);
            }

            // Copia o InputStream para um arquivo temporário (necessário para APIs que requerem File)
            File tempFile = File.createTempFile("baseDocuments", ".pdf");
            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                resourceStream.transferTo(out);
            }

            // Atualiza a variável baseDocument
            baseDocument = tempFile;

            // Usa Tika para ler o conteúdo
            Tika tika = new Tika();
            vectorStore.load(baseDocument);

            try (InputStream inputStream = new FileInputStream(baseDocument)) {
                String rawContent = tika.parseToString(inputStream);
                Document doc = new Document(rawContent);
                List<Document> docs = List.of(doc);

                TextSplitter textSplitter = new TokenTextSplitter();
                List<Document> splitDocs = textSplitter.apply(docs);

                vectorStore.add(splitDocs);
                vectorStore.save(baseDocument);
            }
        } catch (Exception e) {
            System.err.println("Erro ao processar o arquivo com Tika: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void update(List<File> files) {
        Tika tika = new Tika();
        for (File file : files) {
            if (file.exists()) {
                System.out.println("Carregando vetor de: " + file.getName());
                vectorStore.load(file);

                try (InputStream inputStream = new FileInputStream(file)) {
                    String rawContent = tika.parseToString(inputStream);
                    Document doc = new Document(rawContent);
                    List<Document> docs = List.of(doc);

                    TextSplitter textSplitter = new TokenTextSplitter();
                    List<Document> splitDocs = textSplitter.apply(docs);

                    vectorStore.add(splitDocs);
                    vectorStore.save(file);

                } catch (Exception e) {
                    System.err.println("Erro ao processar arquivo com Tika: " + file.getName());
                    e.printStackTrace();
                }
            } else {
                System.out.println("Arquivo não encontrado: " + file.getAbsolutePath());
            }
        }
    }

    public List<String> search(String searchParam, int filesSize) {
        List<Document> documents = vectorStore.similaritySearch(SearchRequest.query(searchParam).withTopK(filesSize));
        return documents.stream().map(Document::getContent).toList();
    }
}
