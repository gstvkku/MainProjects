package com.gparaiso.auth_cviper.model;

import com.gparaiso.auth_cviper.enums.AuthProvider;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_user")
public class UserModel implements Serializable {
    private  static  final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userID;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

}
