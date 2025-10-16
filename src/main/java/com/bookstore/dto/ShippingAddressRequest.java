package com.bookstore.dto;

import jakarta.validation.constraints.*;

public class ShippingAddressRequest {

    @NotNull
    private Long userId;

    @NotBlank private String name;
    @NotBlank private String surname;

    @NotBlank
    @Pattern(regexp = "^[0-9+\\- ]{8,20}$", message = "invalid telephone")
    private String telephone;

    @NotBlank private String address1;
    private String address2;

    @NotBlank private String province;
    @NotBlank private String district;

    @NotBlank
    @Pattern(regexp = "^[0-9]{5}$", message = "postcode must be 5 digits")
    private String postcode;

    // getters & setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getAddress1() { return address1; }
    public void setAddress1(String address1) { this.address1 = address1; }
    public String getAddress2() { return address2; }
    public void setAddress2(String address2) { this.address2 = address2; }
    public String getProvince() { return province; }
    public void setProvince(String province) { this.province = province; }
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    public String getPostcode() { return postcode; }
    public void setPostcode(String postcode) { this.postcode = postcode; }
}