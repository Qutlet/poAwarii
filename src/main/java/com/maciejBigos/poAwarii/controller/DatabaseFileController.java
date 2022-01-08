package com.maciejBigos.poAwarii.controller;

import com.maciejBigos.poAwarii.model.DatabaseFile;
import com.maciejBigos.poAwarii.model.enums.FilePurpose;
import com.maciejBigos.poAwarii.model.messeges.ResponseFile;
import com.maciejBigos.poAwarii.model.messeges.ResponseMessage;
import com.maciejBigos.poAwarii.service.DatabaseFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin
@RequestMapping("file")
public class DatabaseFileController {

    public static final String success = "Uploaded the file successfully: ";
    public static final String failure = "Could not upload the file: ";

    @Autowired
    private DatabaseFileStorageService databaseFileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> upload(@RequestParam("senderId") String senderId,
                                                  @RequestParam("file") MultipartFile file,
                                                  @RequestParam(defaultValue = "NO_PURPOSE", required = false, name = "purpose") FilePurpose filePurpose) {
        try {
            databaseFileStorageService.upload(file,senderId,filePurpose);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(success + file.getOriginalFilename()));
        }catch (IOException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(failure + file.getOriginalFilename()));
        }
    }

    @GetMapping("/download")
    public ResponseEntity<List<ResponseFile>> downloadAll() {
        List<ResponseFile> files = databaseFileStorageService.getAllFiles().map(databaseFile -> {
            String fileURI = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/file/download/")
                    .path(databaseFile.getId())
                    .toUriString();

            return new ResponseFile(
                    databaseFile.getName(),
                    fileURI,
                    databaseFile.getType(),
                    databaseFile.getData().length,
                    databaseFile.getTransferorId());
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        DatabaseFile downloadedFile = databaseFileStorageService.download(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + downloadedFile.getName() + "\"")
                .body(downloadedFile.getData());
    }
}
