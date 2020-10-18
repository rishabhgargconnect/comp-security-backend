package com.example.ComputerSecurity.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "password_metadata")
public class PasswordMetadata {

    @Id
    @Column(name = "user_id")
    private String userId;
    @Column(name = "dynamic_salt")
    private String dynamicSalt;

}
