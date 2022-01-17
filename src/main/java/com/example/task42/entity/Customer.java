package com.example.task42.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "customer")
public class Customer {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false, unique = true)
        private int id;

        @Column(name = "name", nullable = false)
        private String surname;

        @Column(name = "email", nullable = false)
        private String email;

        @Column(name = "balance", nullable = false)
        private int balance;

}
