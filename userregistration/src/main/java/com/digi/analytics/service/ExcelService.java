package com.digi.analytics.service;

import com.digi.analytics.model.User;
import com.digi.analytics.utility.ExcelHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {


    UserService userService;
    public List<User> save(MultipartFile file) {
        try {
            return ExcelHelper.excelToUsers(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

}
