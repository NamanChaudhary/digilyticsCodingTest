package com.digi.analytics.controller;


import com.digi.analytics.model.ResponseMessage;
import com.digi.analytics.model.User;
import com.digi.analytics.service.EntityManager;
import com.digi.analytics.service.ExcelService;
import com.digi.analytics.service.UserService;
import com.digi.analytics.utility.ExcelHelper;
import com.digi.analytics.utility.MediaTypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/register")
public class ApplicationController {

    @Resource
    public UserService userService;

    @Autowired
    ExcelService fileService;

    @Autowired
    private ServletContext servletContext;

    @GetMapping("/helthcheck")
    public ResponseEntity<String> helthCheck() {
        return ResponseEntity.status(HttpStatus.OK).body("welcome!!");
    }

    @PostMapping("/registerUsers")
    public ResponseEntity<ResponseMessage> registerUsers(@RequestParam("file") MultipartFile file) {
        String message = "";
        ResponseMessage message1 = new ResponseMessage();

        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                List<User> users = fileService.save(file);

                userService.validate(users);
                Map<Boolean, List<User>> mapByError = users.stream().collect(Collectors.partitioningBy(user -> Objects.nonNull(user.getError())));

                mapByError.get(true).stream().filter(user-> Objects.isNull(user.getError())).forEach(e-> userService.insertUser(e));
                String fileName = EntityManager.fileName + System.currentTimeMillis()+EntityManager.fileExtension;
                String path = System.getProperty("user.dir") + EntityManager.defaultDirectory;
                ExcelHelper.genErrorExcelFile(mapByError.get(false), path+fileName);
                message1.setNo_of_rows_parsed(mapByError.get(true).size());
                message1.setNo_of_rows_failed(mapByError.get(false).size());
                message1.setError_file_url("<IP>:<PORT>/downloaderrorfile?fileName="+fileName);

                return ResponseEntity.status(HttpStatus.OK).body(message1);
            } catch (Exception e) {
                message1.setError_file_url("Could not upload the file: " + file.getOriginalFilename() + "!");
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message1);
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message1);
    }

    @GetMapping("/downloaderrorfile")
    public ResponseEntity<ByteArrayResource> downloadFile(
            @RequestParam("fileName") String fileName) throws IOException {

        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
        System.out.println("fileName: " + fileName);
        System.out.println("mediaType: " + mediaType);

        String filepath = System.getProperty("user.dir") + EntityManager.defaultDirectory+fileName;
        System.out.println("file path : "+filepath);

        Path path = Paths.get(filepath);
        byte[] data = Files.readAllBytes(path);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                // Content-Type
                .contentType(mediaType) //
                // Content-Lengh
                .contentLength(data.length) //
                .body(resource);
    }
}
