package com.example.the_bar_app.entity.user;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_settings")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UserSettings {
    @Id
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 10)
    private Theme theme;

    @Column(nullable = false,length = 3)
    private String currency = "EUR";

    @Column(nullable = false)
    private boolean showOutOfStock = false;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_fav_drink_ids",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "drink_id",nullable = false)
    private Set<Long> favoriteDrinkIds = new HashSet<>();
}
