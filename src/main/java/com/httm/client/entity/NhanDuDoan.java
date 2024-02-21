package com.httm.client.entity;

import jakarta.persistence.*;


@Entity
@Table(name="nhan_du_doan")
public class NhanDuDoan {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(name="ten_nhan", nullable = false)
    private String tenNhan;

    @Column(name = "moTa", nullable = true)
    private String moTa;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenNhan() {
        return tenNhan;
    }

    public void setTenNhan(String tenNhan) {
        this.tenNhan = tenNhan;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
