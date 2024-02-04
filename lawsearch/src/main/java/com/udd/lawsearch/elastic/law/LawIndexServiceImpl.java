package com.udd.lawsearch.elastic.law;

import com.udd.lawsearch.documents.UploadDocsDto;
import com.udd.lawsearch.government.dto.GovernmentDto;
import com.udd.lawsearch.shared.filestorage.FileStorageService;
import com.udd.lawsearch.shared.pdfservice.PdfContentData;
import com.udd.lawsearch.shared.pdfservice.PdfService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LawIndexServiceImpl implements LawIndexService{
    private final LawIndexRepository lawIndexRepository;
    private final PdfService pdfService;
    private final FileStorageService fileStorageService;
    @Value("${bucket-name}")
    private String bucketName;
    @Async
    public void create(final List<MultipartFile> files) throws Exception {
        for(MultipartFile law : files){
            String filename = law.getOriginalFilename();
            InputStream stream = fileStorageService.getFileFromMinio(bucketName,filename);
            PdfContentData data = pdfService.getContentData(stream);
            LawIndex index = new LawIndex();
            index.setContent(data.getContent());

            lawIndexRepository.save(index);
        }
    }
}
