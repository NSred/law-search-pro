package com.udd.lawsearch.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadDocsDto {
    private MultipartFile contract;
    private List<MultipartFile> laws;
}
