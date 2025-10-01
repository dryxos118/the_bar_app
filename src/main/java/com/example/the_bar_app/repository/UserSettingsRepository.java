package com.example.the_bar_app.repository;

import com.example.the_bar_app.entity.user.UserSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSettingsRepository extends JpaRepository<UserSettings,Long> {
}
