package com.example.the_bar_app.service;

import com.example.the_bar_app.api.AppException;
import com.example.the_bar_app.api.ErrorType;
import com.example.the_bar_app.dto.DrinkDto;
import com.example.the_bar_app.entity.Drink;
import com.example.the_bar_app.entity.DrinkMapper;
import com.example.the_bar_app.repository.DrinkRepository;
import com.example.the_bar_app.service.impl.IDrinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DrinkService implements IDrinkService {

    private final DrinkRepository repo;
    private final DrinkMapper mapper;

    @Override
    public DrinkDto get(Long id) {
        Drink entity = repo.findById(id).orElseThrow(() -> notFound(id));
        return mapper.toDto(entity);
    }

    @Override
    public List<DrinkDto> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public boolean create(DrinkDto dto) {
        Drink entity = mapper.toEntity(dto);
        if(dto.getHasAlcohol() == null) entity.setHasAlcohol(true);
        if(dto.getOutOfStock() == null) entity.setOutOfStock(false);
        if(dto.getTags() == null) entity.setTags(new HashSet<>());
        if(dto.getIngredients() == null) entity.setIngredients(new ArrayList<>());
        entity.setId(null);
        repo.save(entity);
        return true;
    }

    @Override
    public boolean replace(Long id, DrinkDto dto) {
        Drink entity = repo.findById(id).orElseThrow(() -> notFound(id));
        mapper.replaceEntityFromDto(dto, entity);
        if (dto.getHasAlcohol() == null) entity.setHasAlcohol(true);
        if (dto.getOutOfStock() == null) entity.setOutOfStock(false);
        repo.save(entity);
        return true;
    }

    @Override
    public boolean update(Long id, DrinkDto dto) {
        Drink entity = repo.findById(id).orElseThrow(() -> notFound(id));
        mapper.updateEntityFromDto(dto, entity);
        repo.save(entity);
        return true;
    }

    @Override
    public boolean delete(Long id) {
        Drink entity = repo.findById(id).orElseThrow(() -> notFound(id));
        repo.delete(entity);
        return true;
    }

    private AppException notFound(Long id) {
        return new AppException(ErrorType.BAD_REQUEST, "Drink " + id + " not found");
    }
}
