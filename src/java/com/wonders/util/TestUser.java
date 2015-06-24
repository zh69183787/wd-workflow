package com.wonders.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * AfUser entity. @author MyEclipse Persistence Tools
 */

public class TestUser implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5398255942794045747L;
    // Fields
    private Integer id;
    private String name;
    private String password;

    private Long mobile1;

    private Long mobile2;
    private String telephone;

    private String email;
    private Integer removed;

    private Date date;

    // Constructors

    /** default constructor */
    public TestUser() {
        this.removed=0;
    }

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /** full constructor */
    public TestUser(String name, String password, Long mobile1,
                    Long mobile2, String telephone, String email, Integer removed) {
        this.name = name;
        this.password = password;
        this.mobile1 = mobile1;
        this.mobile2 = mobile2;
        this.telephone = telephone;
        this.email = email;
        this.removed = removed;
    }


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getMobile1() {
        return this.mobile1;
    }

    public void setMobile1(Long mobile1) {
        this.mobile1 = mobile1;
    }

    public Long getMobile2() {
        return this.mobile2;
    }

    public void setMobile2(Long mobile2) {
        this.mobile2 = mobile2;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Integer getRemoved() {
        return this.removed;
    }

    public void setRemoved(Integer removed) {
        this.removed = removed;
    }


}