package com.lsy.test.spring.jpa.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * Created by lisiyu on 2020/4/8.
 */
@Data
@EqualsAndHashCode
@Entity
@Table(name = "t_user")
public class TUser {

    private static final long serialVersionUID = 562533893937074594L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")protected String id;
    @Column(name = "avatar", length = 255, nullable = false)
    private String avatar;
    @Column(name = "email", length = 255)
    private String email;
    @Column(name = "password", length = 45)
    private String password;
    @Column(name = "phone", length = 45)
    private Long phone;
    @Column(name = "username", length = 45)
    private String username;

}
