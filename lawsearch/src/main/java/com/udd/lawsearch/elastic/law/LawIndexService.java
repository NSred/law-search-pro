package com.udd.lawsearch.elastic.law;

import com.udd.lawsearch.documents.UploadDocsDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LawIndexService {
    void create(List<MultipartFile> files) throws Exception;
}
