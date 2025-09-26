package com.example.the_bar_app.repository;

import com.example.the_bar_app.entity.Drink;
import com.example.the_bar_app.entity.DrinkCategory;
import com.example.the_bar_app.entity.DrinkTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrinkRepository extends JpaRepository<Drink, Long> {
    List<Drink> findByCategory(DrinkCategory category);

    List<Drink> findByTagsContaining(DrinkTag tag);
}
