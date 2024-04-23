package com.basic.springai.controller;

import com.basic.springai.models.SendMessageRequestModel;
import com.basic.springai.service.SendMessageService;
import com.basic.springai.service.SetUpDataToDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class OllamaController {

    private final SendMessageService sendMessageService;
    private final SetUpDataToDbService setUpDataToDbService;

    @PostMapping("/sendMessage")
    public String sendMessage(@RequestBody SendMessageRequestModel message) {
        return sendMessageService.sendMessage(message);
    }

    @PostMapping("/setData")
    public String setUpDataToDb(@RequestParam("pdf") Resource pdf) {
        return setUpDataToDbService.setUpDataToDb(pdf);
    }

    @PostMapping("/setData2")
    public String setUpDataToDb(@RequestParam("pdf") MultipartFile pdf) {
        return setUpDataToDbService.setUpDataToDb(pdf);
    }

}
