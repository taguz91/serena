package com.taguz91.api_serena.controller;

import com.taguz91.api_serena.api.aws.BucketName;
import com.taguz91.api_serena.api.response.PageResponse;
import com.taguz91.api_serena.models.SyncHistory;
import com.taguz91.api_serena.repository.SyncHistoryRepository;
import com.taguz91.api_serena.service.contracts.FileStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/sync")
public class SyncHistoryController {

    @Autowired
    private SyncHistoryRepository syncHistoryRepository;
    @Autowired
    private FileStoreService fileStoreService;

    @GetMapping("")
    public ResponseEntity<PageResponse<SyncHistory>> index(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SyncHistory> syncs = syncHistoryRepository.findAll(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<SyncHistory>(syncs));
    }

    @PostMapping(
            value = "",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SyncHistory> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String type
    ) {
        SyncHistory sync = new SyncHistory();
        sync.setFilename(file.getOriginalFilename());
        sync.setStatus("pending");
        sync.setType(type);

        String urlS3 = fileStoreService.save(file, BucketName.SYNC_FILES);
        sync.setS3Key(urlS3);

        SyncHistory saved = syncHistoryRepository.save(sync);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saved);
    }
}
