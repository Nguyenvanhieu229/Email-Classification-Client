package com.httm.client.entity;

import jakarta.persistence.*;

@Entity
@Table(name="mail_server")
public class ServerMail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "tenMayChu")
    private String tenMayChu;

    @Column(nullable = false, name="diaChiMayChuNhan")
    private  String diaChiMayChuNhan;

    @Column(nullable = false, name="portNhan")
    private int portNhan;

    @Column(nullable = false, name="giaoThucNhan")
    private String giaoThucNhan;

    @Column(nullable = false, name="diaChiMayChuGui")
    private  String diaChiMayChuGui;

    @Column(nullable = false, name="portGui")
    private int portGui;

    @Column(nullable = false, name="giaoThucGui")
    private String giaoThucGui;

    public String getTenMayChu() {
        return tenMayChu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDiaChiMayChuNhan() {
        return diaChiMayChuNhan;
    }

    public void setDiaChiMayChuNhan(String diaChiMayChuNhan) {
        this.diaChiMayChuNhan = diaChiMayChuNhan;
    }

    public int getPortNhan() {
        return portNhan;
    }

    public void setPortNhan(int portNhan) {
        this.portNhan = portNhan;
    }

    public String getGiaoThucNhan() {
        return giaoThucNhan;
    }

    public void setGiaoThucNhan(String giaoThucNhan) {
        this.giaoThucNhan = giaoThucNhan;
    }

    public String getDiaChiMayChuGui() {
        return diaChiMayChuGui;
    }

    public void setDiaChiMayChuGui(String diaChiMayChuGui) {
        this.diaChiMayChuGui = diaChiMayChuGui;
    }

    public int getPortGui() {
        return portGui;
    }

    public void setPortGui(int portGui) {
        this.portGui = portGui;
    }

    public String getGiaoThucGui() {
        return giaoThucGui;
    }

    public void setGiaoThucGui(String giaoThucGui) {
        this.giaoThucGui = giaoThucGui;
    }

    public void setTenMayChu(String tenMayChu) {
        this.tenMayChu = tenMayChu;
    }
}
