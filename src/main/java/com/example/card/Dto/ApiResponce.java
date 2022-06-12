package com.example.card.Dto;

import com.example.card.Model.Chiqim;
import com.example.card.Model.Kirim;
import lombok.Data;

import java.util.List;

@Data
public class ApiResponce {
    private String xabar;
    private boolean holat;
    private List<Chiqim> chiqims;
    private List<Kirim> kirims;

    public ApiResponce(String xabar, boolean holat) {
        this.xabar = xabar;
        this.holat = holat;
    }

    public ApiResponce(boolean holat, String xabar, List<Chiqim> chiqims) {
        this.xabar = xabar;
        this.holat = holat;
        this.chiqims = chiqims;
    }

    public ApiResponce(String xabar, boolean holat, List<Kirim> kirims) {
        this.xabar = xabar;
        this.holat = holat;
        this.kirims = kirims;
    }
}
