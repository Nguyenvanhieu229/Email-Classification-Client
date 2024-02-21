package com.httm.client.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "nguoi_dung_o_server_mail")

public class NguoiDungOServer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name = "dia_chi_tren_may_chu")
    private String diaChiTrenMayChu;

    @Column(nullable = false, name="mat_khau_tren_may_chu")
    private String matKhauTrenMayChu;

    @ManyToOne
    @JoinColumn(name="id_nguoi_dung")
    private NguoiDung nguoiDung;

    @ManyToOne
    @JoinColumn(name="id_server_mail")
    private ServerMail serverMail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public NguoiDung getNguoiDung() {
        return nguoiDung;
    }

    public String getDiaChiTrenMayChu() {
        return diaChiTrenMayChu;
    }

    public String getMatKhauTrenMayChu() {
        return matKhauTrenMayChu;
    }

    public void setDiaChiTrenMayChu(String diaChiTrenMayChu) {
        this.diaChiTrenMayChu = diaChiTrenMayChu;
    }

    public ServerMail getServerMail() {
        return serverMail;
    }

    public void setServerMail(ServerMail serverMail) {
        this.serverMail = serverMail;
    }

    public void setMatKhauTrenMayChu(String matKhauTrenMayChu) {
        this.matKhauTrenMayChu = matKhauTrenMayChu;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }
}
