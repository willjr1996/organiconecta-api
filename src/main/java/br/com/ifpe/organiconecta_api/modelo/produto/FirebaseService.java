package br.com.ifpe.organiconecta_api.modelo.produto;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@Service
public class FirebaseService {

    private static final String BUCKET_NAME = "seu-bucket.appspot.com"; // 🔹 Insira o nome do seu bucket Firebase
    private final Storage storage = StorageOptions.getDefaultInstance().getService();

    // 🔹 Upload da Imagem para Firebase 🔹
    public String uploadFile(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();

            storage.create(blobInfo, file.getBytes());

            return "https://storage.googleapis.com/" + BUCKET_NAME + "/" + fileName; // Retorna a URL da imagem
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer upload da imagem", e);
        }
    }

    // 🔹 Deletar Imagem do Firebase 🔹
    public void deleteFile(String imageUrl) {
        try {
            if (imageUrl == null || imageUrl.isEmpty()) {
                return;
            }

            // Extrai o nome do arquivo da URL
            String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

            // Identifica o arquivo no Firebase e exclui
            BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
            boolean deleted = storage.delete(blobId);

            if (!deleted) {
                throw new RuntimeException("Erro ao excluir arquivo do Firebase: arquivo não encontrado.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir arquivo do Firebase", e);
        }
    }
}
