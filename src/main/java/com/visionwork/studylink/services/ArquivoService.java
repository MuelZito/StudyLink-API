    package com.visionwork.studylink.services;

    import com.visionwork.studylink.models.anotacao.Anotacao;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.core.io.Resource;
    import org.springframework.core.io.UrlResource;
    import org.springframework.stereotype.Service;
    import org.springframework.web.multipart.MultipartFile;
    import java.io.IOException;
    import java.net.MalformedURLException;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.util.UUID;

    @Service
    public class ArquivoService {
        @Value("${upload.path}")
        private String uploadPath;

        public Anotacao.Arquivo salvarArquivo(MultipartFile arquivo) throws IOException {
            String nomeUnico = UUID.randomUUID().toString() + "_" + arquivo.getOriginalFilename();

            Path diretorio = Paths.get(uploadPath);
            if (!Files.exists(diretorio)) {
                Files.createDirectories(diretorio);
            }

            Path caminhoCompleto = diretorio.resolve(nomeUnico);
            Files.copy(arquivo.getInputStream(), caminhoCompleto);

            return new Anotacao.Arquivo(
                    arquivo.getOriginalFilename(),
                    caminhoCompleto.toString(),
                    arquivo.getContentType(),
                    arquivo.getSize()
            );
        }

        public Resource carregarArquivo(String nomeArquivo) throws MalformedURLException {
            Path caminho = Paths.get(uploadPath).resolve(nomeArquivo);
            Resource resource = new UrlResource(caminho.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Arquivo não encontrado: " + nomeArquivo);
            }
        }
    }