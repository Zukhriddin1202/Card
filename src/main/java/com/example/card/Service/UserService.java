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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Lazy

    @Autowired
    CardRepository cardRepository;

    @Autowired
    KirimRepository kirimRepository;

    @Autowired
    ChiqimRepository chiqimRepository;


    @Autowired
    PasswordEncoder passwordEncoder;


    public ApiResponce kirims(CardDto dto){
        Optional<Card> byNumer = cardRepository.findByNumer(dto.getNumer());
        if (byNumer.isPresent()) {
            Card card=byNumer.get();
            boolean matches = passwordEncoder.matches(dto.getPassword(),  card.getPassword());
            if (matches)
            {
                List<Kirim> kirimList = kirimRepository.findAllByToCard(card);
                return new ApiResponce("Kartangizga tushumlar jadvlai",true,kirimList);
            }
            return new ApiResponce("Parolingiz xato",false);
        }
        return new ApiResponce("Bunday karta raqami mavjud emas", false);
    }

    public ApiResponce chiqim(CardDto dto){
        Optional<Card> byNumer = cardRepository.findByNumer(dto.getNumer());
        if (byNumer.isPresent()) {
            Card card=byNumer.get();
            boolean matches = passwordEncoder.matches(dto.getPassword(),  card.getPassword());
            if (matches)
            {
                List<Chiqim> chiqimList = chiqimRepository.findAllByFromCard(card);
                return new ApiResponce(true,"Kartangizdagi chiqimlar jadvlai",chiqimList);
            }
            return new ApiResponce("Parolingiz xato",false);
        }
        return new ApiResponce("Bunday karta raqami mavjud emas", false);
    }

    public ApiResponce otkazma(CardDto dto){
            Optional<Card> byNumer = cardRepository.findByNumer(dto.getNumer());
            if (byNumer.isPresent()) {
                Card card=byNumer.get();
                boolean matches = passwordEncoder.matches(dto.getPassword(),  card.getPassword());
                if (matches)
                {
                    Optional<Card> byNumer1 = cardRepository.findByNumerAndNumerNot(dto.getTonumer(),card.getNumer());
                    if(byNumer1.isPresent()) {
                        Card card3=byNumer1.get();
                        if (card.getBalans() >= dto.getSum() * 1.2) {
                            Kirim kirim=new Kirim();
                            kirim.setFromCard(card);
                            kirim.setToCard(card3);
                            kirim.setSumma(dto.getSum());
                            kirimRepository.save(kirim);
                            Chiqim chiqim=new Chiqim();
                            chiqim.setFromCard(card);
                            chiqim.setToCard(card3);
                            chiqim.setSumma(dto.getSum() * 1.2);
                            chiqim.setKommisiya(dto.getSum() * 0.2);
                            chiqimRepository.save(chiqim);
                            Card card1=card;
                            card1.setBalans(card.getBalans()-dto.getSum() * 1.2);
                            cardRepository.save(card1);
                            Card card2=card3;
                            card2.setBalans(card3.getBalans()+dto.getSum());
                            cardRepository.save(card2);
                            return new ApiResponce("Amaliyot muavfaqiyatli amalga oshirildi", true);
                        }
                        return new ApiResponce("Kechirasiz kartangizda mablag' yetrali emas", false);
                    }
                    return new ApiResponce("O'tkazmoqchi bo'lgan kartangiz mavjud emas", false);
                }
                return new ApiResponce("Parolingiz xato", false);
            }
            return new ApiResponce("Bunday karta raqami mavjud emas", false);
    }
}
