package com.udd.lawsearch.elastic.contract;

import com.udd.lawsearch.shared.filestorage.FileStorageService;
import com.udd.lawsearch.shared.location.GeoCodingService;
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
    private final GeoCodingService geoCodingService;
    @Value("${bucket-name}")
    private String bucketName;

    @Async
    public void create(String fileName) throws Exception {
        InputStream stream = fileStorageService.getFileFromMinio(bucketName, fileName);
        PdfContentData data = pdfService.getContentData(stream);
        var coords = geoCodingService.getCoordinates(data.getGovernmentAddress());
        ContractIndex index = new ContractIndex(data.getName(), data.getSurname(),
                data.getGovernmentName(), data.getGovernmentType(), data.getContent(), coords[0], coords[1], fileName);

        contractIndexRepository.save(index);
    }
}
