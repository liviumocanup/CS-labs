package com.utm.cslabs.hash;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import java.math.BigInteger;

@Entity
@ToString
@Accessors(chain = true)
@Getter
@Setter
@Table(name = "usersHash")
public class UserHash {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    private BigInteger publicKey;

    private BigInteger privateKey;
}
