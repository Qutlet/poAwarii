package com.maciejBigos.poAwarii.service;

import com.maciejBigos.poAwarii.model.DatabaseFile;
import com.maciejBigos.poAwarii.model.enums.FilePurpose;
import com.maciejBigos.poAwarii.repository.DatabaseFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class DatabaseFileStorageService {

    @Autowired
    private DatabaseFileRepository databaseFileRepository;

    @Autowired
    private SpecialistService specialistService;

    @Autowired
    private UserService userService;

    public DatabaseFile upload(MultipartFile file, String senderId, FilePurpose filePurpose) throws IOException {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        DatabaseFile databaseFile = new DatabaseFile(filename,file.getContentType(),senderId,file.getBytes());
        DatabaseFile saveDatabaseFile = databaseFileRepository.save(databaseFile);
        switch (filePurpose) {
            case USER_PROFILE_PHOTO:
                userService.addPhoto(senderId,saveDatabaseFile.getId());
                break;
            case SPECIALIST_WORK_PHOTO:
                specialistService.addPhoto(senderId, saveDatabaseFile.getId());
                break;
            default:
                break;
        }
        return saveDatabaseFile;
    }

    public DatabaseFile download(String id) {
        return databaseFileRepository.findById(id).get();
    }

    public Stream<DatabaseFile> getAllFiles() {
        return databaseFileRepository.findAll().stream();
    }

}
