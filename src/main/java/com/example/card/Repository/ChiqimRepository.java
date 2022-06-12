package com.example.card.Repository;

import com.example.card.Model.Card;
import com.example.card.Model.Chiqim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChiqimRepository extends JpaRepository<Chiqim,Integer> {
    List<Chiqim> findAllByFromCard(Card fromCard);
    List<Chiqim> findAllBy();
}
