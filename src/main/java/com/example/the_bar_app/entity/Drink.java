package com.example.the_bar_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(
        name = "drinks",
        indexes = {
                @Index(name = "idx_drink_name", columnList = "name"),
                @Index(name = "idx_drink_category", columnList = "category")
        },
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Drink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DrinkCategory category;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "drink_tags",
            joinColumns = @JoinColumn(name = "drink_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "tag", nullable = false, length = 30)
    @Builder.Default
    private Set<DrinkTag> tags = new HashSet<>();

    @Column(length = 60)
    private String glass;

    @Column(length = 255)
    private String image;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal price;

    @Lob
    private String instruction;

    @Column(nullable = false)
    @Builder.Default
    private boolean hasAlcohol = true;

    @Column(nullable = false)
    @Builder.Default
    private boolean outOfStock = false;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "drink_ingredients",
            joinColumns = @JoinColumn(name = "drink_id")
    )
    @Column(name = "ingredient", nullable = false, length = 80)
    @Builder.Default
    private List<String> ingredients = new ArrayList<>();

    public void addTag(DrinkTag tag) {
        this.tags.add(tag);
    }

    public void addIngredient(String ingredient) {
        if (ingredient != null && !ingredient.isBlank()) this.ingredients.add(ingredient.trim());
    }
}
