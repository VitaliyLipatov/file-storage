package com.spring.filestorage.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("/api/file")
public class FileStorageController {

    @ApiOperation("Получить файл по id")
    @GetMapping("/{id}")
    public void getFile(@PathVariable BigDecimal id) {
    }
}
