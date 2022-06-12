package com.example.card.Controller;

import com.example.card.Dto.ApiResponce;
import com.example.card.Dto.CardDto;
import com.example.card.Service.CardService;
import com.example.card.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/mycard")
public class UserController {

    @Autowired
    CardService cardService;

    @Autowired
    UserService userService;

    @PostMapping("/otkazma")
    public ResponseEntity<?> otkazma(@RequestBody CardDto dto){
        ApiResponce apiResponce=userService.otkazma(dto);
        return ResponseEntity.status(apiResponce.isHolat()?201:409).body(apiResponce.getXabar());
    }

    @GetMapping("/kirim")
    public ResponseEntity<?> kirim(@RequestBody CardDto dto){
        ApiResponce apiResponce=userService.kirims(dto);
        return ResponseEntity.status(apiResponce.isHolat()?201:409).body(apiResponce.getXabar()+"\n"+apiResponce.getKirims());
    }

    @GetMapping("/chiqim")
    public ResponseEntity<?> chiqim(@RequestBody CardDto dto){
        ApiResponce apiResponce=userService.chiqim(dto);
        return ResponseEntity.status(apiResponce.isHolat()?201:409).body(apiResponce.getXabar()+"\n"+apiResponce.getChiqims());
    }
}
