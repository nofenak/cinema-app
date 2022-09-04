package com.example.screening.domain;


import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface ScreeningRepository extends JpaRepository<Screening, UUID> {

    List<Screening> findAllByFilmId(UUID filmId);

    List<Screening> findAllByShownAt(LocalDateTime date);
}
