package com.example.card.Controller;

import com.example.card.Dto.ApiResponce;
import com.example.card.Dto.CardDto;
import com.example.card.Model.Card;
import com.example.card.Model.Chiqim;
import com.example.card.Model.Kirim;
import com.example.card.Repository.CardRepository;
import com.example.card.Service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/card")

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CardController {

    @Autowired
    CardService cardService;

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody Card card){
        ApiResponce apiResponce = cardService.insert(card);
        return ResponseEntity.status(apiResponce.isHolat()?201:409).body(apiResponce.getXabar());
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> select(){
        return ResponseEntity.ok().body(cardService.select());
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody Card card,@PathVariable Integer id){
        ApiResponce apiResponce = cardService.update(card,id);
        return ResponseEntity.status(apiResponce.isHolat()?201:409).body(apiResponce.getXabar());
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        ApiResponce apiResponce = cardService.delete(id);
        return ResponseEntity.status(apiResponce.isHolat()?201:409).body(apiResponce.getXabar());
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> selectId(@PathVariable Integer id){
        return ResponseEntity.ok().body(cardService.selectId(id));
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping("/kirmlar")
    public List<Kirim> kirmlar(){
        return cardService.kirims();
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping("/kirm")
    public List<Kirim> kirm(@RequestBody CardDto dto){
        return cardService.kirim(dto);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping("/chiqimlar")
    public List<Chiqim> chiqimlar(){
        return cardService.chiqms();
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping("/chiqim")
    public List<Chiqim> chiqim(@RequestBody CardDto dto){
        return cardService.chiqim(dto);
    }

}
