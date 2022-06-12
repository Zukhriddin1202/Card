package com.example.card.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardDto {
    private String numer;
    private String password;
    private double sum;
    private String tonumer;

    public CardDto(String numer) {
        this.numer = numer;
    }



    public CardDto(String numer, String password) {
        this.numer = numer;
        this.password = password;
    }
}
