package com.crmcoches.entity;

import com.crmcoches.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;


// guardar usuarios y datos en la tabla users
@Entity
@Data
@Table(name = "users")


public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String email;

    private String password;

    private UserRole userRole;
}
