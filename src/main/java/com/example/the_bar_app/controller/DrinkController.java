package com.example.the_bar_app.controller;

import com.example.the_bar_app.dto.drink.DrinkDto;
import com.example.the_bar_app.dto.OnCreate;
import com.example.the_bar_app.dto.OnPut;
import com.example.the_bar_app.service.impl.IDrinkService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/drinks")
@RequiredArgsConstructor
@Tag(name = "Drinks")
@PreAuthorize("hasAnyAuthority('ADMIN','BARMAN')")
public class DrinkController {

    private final IDrinkService service;
    private final ObjectMapper om;

    @GetMapping("/get/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> get(@PathVariable Long id) {
        DrinkDto drinkDto = service.get(id);
        return ResponseEntity.ok(drinkDto);
    }

    @GetMapping("/get/all")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getAll() {
        List<DrinkDto> drinkDtoList = service.getAll();
        return ResponseEntity.ok(drinkDtoList);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Validated(OnCreate.class) @RequestBody DrinkDto dto) {
        boolean success = service.create(dto);
        return ResponseEntity.ok(success);
    }

    @PutMapping("/replace/{id}")
    public ResponseEntity<?> replace(
            @PathVariable Long id,
            @Validated(OnPut.class) @RequestBody DrinkDto dto) {
        boolean success = service.replace(id, dto);
        return ResponseEntity.ok(success);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody JsonNode patch) throws IOException {
        DrinkDto current = service.get(id);
        DrinkDto merged = om.readerForUpdating(current).readValue(patch);
        boolean success = service.update(id, merged);
        return ResponseEntity.ok(success);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean success = service.delete(id);
        return ResponseEntity.ok(success);
    }
}
