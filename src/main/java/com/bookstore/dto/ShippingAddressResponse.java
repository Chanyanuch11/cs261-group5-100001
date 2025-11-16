package com.bookstore.dto;

import com.bookstore.model.ShippingAddress;

public class ShippingAddressResponse {
    private Long id;
    private Long userId;
    private String name;
    private String surname;
    private String telephone;
    private String address1;
    private String address2;
    private String province;
    private String district;
    private String postcode;

    public static ShippingAddressResponse from(ShippingAddress e) {
        ShippingAddressResponse r = new ShippingAddressResponse();
        r.id = e.getId();
        r.userId = e.getUser().getId();
        r.name = e.getName();
        r.surname = e.getSurname();
        r.telephone = e.getTelephone();
        r.address1 = e.getAddress1();
        r.address2 = e.getAddress2();
        r.province = e.getProvince();
        r.district = e.getDistrict();
        r.postcode = e.getPostcode();
        return r;
    }

    // getters
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getTelephone() { return telephone; }
    public String getAddress1() { return address1; }
    public String getAddress2() { return address2; }
    public String getProvince() { return province; }
    public String getDistrict() { return district; }
    public String getPostcode() { return postcode; }
}