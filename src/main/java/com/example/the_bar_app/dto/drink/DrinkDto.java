package com.example.the_bar_app.dto.drink;

import com.example.the_bar_app.dto.OnCreate;
import com.example.the_bar_app.dto.OnPut;
import com.example.the_bar_app.entity.drink.DrinkCategory;
import com.example.the_bar_app.entity.drink.DrinkTag;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrinkDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(groups = {OnCreate.class, OnPut.class})
    private String name;

    @NotNull(groups = {OnCreate.class, OnPut.class})
    private DrinkCategory category;

    private Set<DrinkTag> tags;

    private String glass;
    private String image;

    @NotNull(groups = {OnCreate.class, OnPut.class})
    @DecimalMin(value = "0.00", groups = {OnCreate.class, OnPut.class})
    private BigDecimal price;

    private String instruction;

    @NotNull(groups = {OnCreate.class, OnPut.class})
    private Boolean hasAlcohol;

    private Boolean outOfStock = false;

    private List<String> ingredients;
}
