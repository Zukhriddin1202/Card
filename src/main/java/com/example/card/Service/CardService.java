package com.example.card.Service;

import com.example.card.Dto.ApiResponce;
import com.example.card.Dto.CardDto;
import com.example.card.Model.Card;
import com.example.card.Model.Chiqim;
import com.example.card.Model.Kirim;
import com.example.card.Repository.CardRepository;
import com.example.card.Repository.ChiqimRepository;
import com.example.card.Repository.KirimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service


public class CardService {

    @Lazy

    @Autowired
    CardRepository cardRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    KirimRepository kirimRepository;

    @Autowired
    ChiqimRepository chiqimRepository;

    public ApiResponce insert(Card card){
        boolean exists = cardRepository.existsByNumer(card.getNumer());
        if(exists)
            return new ApiResponce("Bunday karta allaqachon mavjud",false);
        Card card1=new Card();
        card1.setUsername(card.getUsername());
        card1.setNumer(card.getNumer());
        card1.setPassword(passwordEncoder.encode(card.getPassword()));
        card1.setBalans(card.getBalans());
        card1.setActive(card.isActive());
        card1.setDate(card.getDate());
        cardRepository.save(card1);
        return new ApiResponce("Muavfaqiyatli saqlandi",false);
    }

    public List<Card> select(){
        return cardRepository.findAll();
    }

    public ApiResponce update(Card card,Integer id){
        Optional<Card> byId = cardRepository.findById(id);
        if(byId.isPresent()){
            boolean numerAndIdNot = cardRepository.existsByNumerAndIdNot(card.getNumer(), id);
            if(numerAndIdNot)
                return new ApiResponce("Bunday karta allaqachon mavjud",false);
            Card card1=byId.get();
            card1.setUsername(card.getUsername());
            card1.setNumer(card.getNumer());
            card1.setPassword(passwordEncoder.encode(card.getPassword()));
            card1.setBalans(card.getBalans());
            card1.setActive(card.isActive());
            card1.setDate(card.getDate());
            cardRepository.save(card1);
            return new ApiResponce("Muavfaqiyatli yangilandi",true);
        }
        return new ApiResponce("Bunday carta mavjud emas",false);
    }

    public ApiResponce delete(Integer id){
        Optional<Card> byId = cardRepository.findById(id);
        if (byId.isPresent()){
            cardRepository.deleteById(id);
            return new ApiResponce("Muavfaqiyatli o'chirildi",true);
        }

        return new ApiResponce("Bunday carta mavjud emas",false);
    }

    public Card selectId(Integer id){
        Optional<Card> byId = cardRepository.findById(id);
        return byId.orElse(null);
    }

    public List<Kirim> kirims(){
        return kirimRepository.findAllBy();
    }


    public List<Kirim> kirim(CardDto dto){
        Optional<Card> byNumer = cardRepository.findByNumer(dto.getNumer());
        if (byNumer.isPresent()) {
                return kirimRepository.findAllByToCard(byNumer.get());
        }
        return new ArrayList<>();
    }

    public List<Chiqim> chiqms(){
        return chiqimRepository.findAllBy();
    }

    public List<Chiqim> chiqim(CardDto dto){
        Optional<Card> byNumer = cardRepository.findByNumer(dto.getNumer());
        if (byNumer.isPresent()) {
            return chiqimRepository.findAllByFromCard(byNumer.get());
        }
        return new ArrayList<>();
    }



}
