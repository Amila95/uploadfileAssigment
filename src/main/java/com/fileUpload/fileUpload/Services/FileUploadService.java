package com.fileUpload.fileUpload.Services;

import com.fileUpload.fileUpload.Repository.FIleDetailsRepostory;
import com.fileUpload.fileUpload.modal.FileDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class FileUploadService {
    @Autowired
    FIleDetailsRepostory fIleDetailsRepostory;

    private static final int BUFFER_SIZE = 4096;

    public void unzip(InputStream zipFilePath, String ZipFileName) throws IOException {
        ZipInputStream zipIn = new ZipInputStream(zipFilePath);
        ZipEntry entry = zipIn.getNextEntry();
        while (entry != null) {
            if (!entry.isDirectory()) {
                this.extractFile(zipIn, ZipFileName, entry.getName());
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();

        }
        zipIn.close();


    }

    private void extractFile(ZipInputStream zipIn, String zipFileName, String fileName) throws IOException {
        // BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        final StringBuilder sb = new StringBuilder();
        final byte[] buffer = new byte[2];

        while (zipIn.read(buffer, 0, buffer.length) != -1) {
            sb.append(new String(buffer));
        }
        String[] lines = sb.toString().split("\\r?\\n");
        for (String line : lines) {
            //System.out.println("line " + " : " + line);
            final String uri = "https://restcountries.eu/rest/v2/alpha/" + line;
            RestTemplate restTemplate = new RestTemplate();
            String string = restTemplate.getForObject(uri, String.class);
            String[] arrOfStr = string.split(":", 2);
            String[] arrOfStr1 = arrOfStr[1].split(",", 2);
            String newS = arrOfStr1[0].toString().replace("\"", "");
            //System.out.println("Country Name " + newS + "ZipFileName " + zipFileName + "fileName " + fileName);
            FileDetails fileDetails = new FileDetails();
            fileDetails.setCountry(newS);
            fileDetails.setFileName(fileName);
            fileDetails.setZipFileName(zipFileName);
            fileDetails.setCountryCode(line);
            fIleDetailsRepostory.save(fileDetails);
        }


    }

    public List<FileDetails> getAllFile() {
        return fIleDetailsRepostory.findAll();
    }
}
