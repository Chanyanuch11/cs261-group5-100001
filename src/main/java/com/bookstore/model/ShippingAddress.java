package com.bookstore.model;

import jakarta.persistence.*;

@Entity
@Table(name = "shipping_addresses")
public class ShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // เชื่อมโยงกับผู้ใช้
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String address1;

    private String address2; // optional

    @Column(nullable = false)
    private String province;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String postcode;

    // --- Getters and Setters ---
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress1() {
        return address1;
    }
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPostcode() {
        return postcode;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}