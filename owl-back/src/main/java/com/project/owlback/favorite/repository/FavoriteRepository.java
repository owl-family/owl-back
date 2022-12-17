package com.project.owlback.favorite.repository;

import com.project.owlback.favorite.dto.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
