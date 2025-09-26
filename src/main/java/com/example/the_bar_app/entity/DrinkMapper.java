package com.example.the_bar_app.entity;

import com.example.the_bar_app.dto.DrinkDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DrinkMapper {
    DrinkDto toDto(Drink entity);
    Drink toEntity(DrinkDto dto);

    List<DrinkDto> toDtoList(List<Drink> entities);

    // PUT
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    void replaceEntityFromDto(DrinkDto dto, @MappingTarget Drink entity);

    // PATCH
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(DrinkDto dto, @MappingTarget Drink entity);
}
