package com.example.the_bar_app.repository;

import com.example.the_bar_app.entity.drink.Drink;
import com.example.the_bar_app.entity.drink.DrinkCategory;
import com.example.the_bar_app.entity.drink.DrinkTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface DrinkRepository extends JpaRepository<Drink, Long> {
    List<Drink> findByCategory(DrinkCategory category);

    List<Drink> findByTagsContaining(DrinkTag tag);

    @Query("select d from Drink d where d.id in :ids")
    List<Drink> findAllByIdIn(@Param("ids") Collection<Long> ids);
}
