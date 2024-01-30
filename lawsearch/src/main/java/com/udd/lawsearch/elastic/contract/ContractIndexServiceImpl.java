package com.udd.lawsearch.elastic.contract;

import com.udd.lawsearch.government.dto.GovernmentDto;
import com.udd.lawsearch.shared.filestorage.FileStorageService;
import com.udd.lawsearch.shared.pdfservice.PdfContentData;
import com.udd.lawsearch.shared.pdfservice.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class ContractIndexServiceImpl implements ContractIndexService{
    private final ContractIndexRepository contractIndexRepository;
    private final PdfService pdfService;
    private final FileStorageService fileStorageService;
    @Value("${bucket-name}")
    private String bucketName;

    @Async
    public void create(GovernmentDto govDto, String govTypeName, double lat, double lon) throws Exception {
        String filename = govDto.getContract().getOriginalFilename();
        InputStream stream = fileStorageService.getFileFromMinio(bucketName,filename);
        PdfContentData data = pdfService.getContentData(stream);
        ContractIndex index = new ContractIndex(data.getName(), data.getSurname(),
                govDto.getName(), govTypeName, data.getContent(), lat, lon);

        contractIndexRepository.save(index);
    }
}
