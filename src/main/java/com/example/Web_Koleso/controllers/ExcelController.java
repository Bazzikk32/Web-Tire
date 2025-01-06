package com.example.Web_Koleso.controllers;

import com.example.Web_Koleso.parser.KolesoParser;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URI;

@RestController
public class ExcelController {
    private final KolesoParser kolesoParser;

    public ExcelController(KolesoParser kolesoParser) {
        this.kolesoParser = kolesoParser;
    }

@PostMapping("/upload")
public ModelAndView upload(@RequestParam("file") MultipartFile file) throws IOException {
    ModelAndView modelAndView = new ModelAndView();


    if (file.isEmpty()) {
        modelAndView.setViewName("errorPage"); // Укажите название вашей страницы ошибки
        modelAndView.addObject("message", "Файл не должен быть пустым");
        return modelAndView;
    }

    // Парсинг файла
    kolesoParser.parseExcelFile(file.getInputStream());

    // Успешный переход
    modelAndView.setViewName("success"); // Укажите название страницы успеха
    modelAndView.addObject("message", "Проверка прошла успешно");
    return modelAndView;
}

}
