package com.example.theBot;

import javax.persistence.*;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn
    private ProductKey productKey;

    private Float amount;
}
