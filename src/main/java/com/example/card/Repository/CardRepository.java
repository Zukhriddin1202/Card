package com.example.card.Repository;

import com.example.card.Model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card,Integer> {
    boolean existsByNumer(String numer);
    boolean existsByNumerAndIdNot(String numer, Integer id);
    Optional<Card> findByNumer(String numer);
    Optional<Card> findByNumerAndNumerNot(String numer, String numer2);
}
