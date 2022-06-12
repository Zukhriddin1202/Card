package com.example.card.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Kirim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private Card fromCard;
    @OneToOne
    private Card toCard;
    @Column(nullable = false)
    private double summa;
    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp timestamp;

}
