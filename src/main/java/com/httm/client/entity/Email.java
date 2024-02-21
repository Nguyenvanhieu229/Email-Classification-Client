package com.httm.client.entity;

import jakarta.persistence.*;


import java.sql.Timestamp;


@Entity
@Table(name = "email")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(name = "noi_dung", length = 2000000, nullable = false, columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String noiDung;

    @Lob
    @Column(name = "tieu_de", length = 2000000, nullable = false, columnDefinition = "LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String tieuDe;

    @Column(name = "nguoi_gui", nullable = false)
    private String nguoiGui;

    @Column(name = "ngay_nhan", nullable = false)
    private Timestamp ngay_nhan;

    @ManyToOne
    @JoinColumn(name = "id_nguoi_nhan")
    private NguoiDungOServer nguoiDungOServer;

    @ManyToOne
    @JoinColumn(name = "id_nhan")
    private NhanDuDoan nhanDuDoan;

    public Integer getId() {
        return id;
    }

    public String getNguoiGui() {
        return nguoiGui;
    }

    public void setNguoiGui(String nguoiGui) {
        this.nguoiGui = nguoiGui;
    }

    public Timestamp getNgay_nhan() {
        return ngay_nhan;
    }

    public void setNgay_nhan(Timestamp ngay_nhan) {
        this.ngay_nhan = ngay_nhan;
    }


    public String getTieuDe() {
        return tieuDe;
    }

    public NguoiDungOServer getNguoiDungOServer() {
        return nguoiDungOServer;
    }

    public void setNguoiDungOServer(NguoiDungOServer nguoiDungOServer) {
        this.nguoiDungOServer = nguoiDungOServer;
    }

    public NhanDuDoan getNhanDuDoan() {
        return nhanDuDoan;
    }

    public void setNhanDuDoan(NhanDuDoan nhanDuDoan) {
        this.nhanDuDoan = nhanDuDoan;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
