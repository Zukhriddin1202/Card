package com.example.card.Repository;

import com.example.card.Model.Card;
import com.example.card.Model.Kirim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KirimRepository extends JpaRepository<Kirim,Integer> {
    List<Kirim> findAllByToCard(Card toCard);

    List<Kirim> findAllBy();

}
