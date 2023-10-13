package com.dbdata.Controller;
import com.dbdata.Service.PaymentService;
import org.apache.poi.EncryptedDocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
public class APIController {


    @Autowired
    private PaymentService paymentService;



    @GetMapping
    public String home()
    {
        return "This is Springboot Application";
    }

    @PostMapping("/upload")
    public ResponseEntity<List<List<String>>> uploadExcel(@RequestParam("file") MultipartFile file)
            throws EncryptedDocumentException, IOException {

        paymentService.save(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> download() throws IOException {
        String fileName ="paytmInfo.xlsx";
        ByteArrayInputStream inputStream = paymentService.getDataDownloaded();
        InputStreamResource    response = new InputStreamResource(inputStream);

        ResponseEntity<InputStreamResource> responseEntity = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(response);
        return responseEntity;
    }
}
