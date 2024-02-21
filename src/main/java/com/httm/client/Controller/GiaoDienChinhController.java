package com.httm.client.Controller;

import com.httm.client.Service.EmailService;
import com.httm.client.entity.Email;
import com.httm.client.entity.NguoiDung;
import com.httm.client.entity.NguoiDungOServer;
import com.httm.client.repository.EmailRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller

public class GiaoDienChinhController {

    @Autowired EmailService service;
    @Autowired
    EmailRepository emailRepository;
    @GetMapping("/nguoi-dung/giao-dien-chinh")
    public String hienThiGiaoDinhChinh(@RequestParam("nguoidung") NguoiDungOServer nguoiDung, Model model) throws Exception {
        System.out.println(nguoiDung.getId());
        List<Email> danhSachMailMoi = service.layMailMoi(nguoiDung);
        danhSachMailMoi = service.locTrung(danhSachMailMoi, nguoiDung);
        danhSachMailMoi = service.duDoan(danhSachMailMoi);
        service.luuEmail(danhSachMailMoi);
        List<Email> danhSachMail = service.layTatCaMail(nguoiDung);
        Collections.reverse(danhSachMail);
        //model.addAttribute("soTrang", 1);
        model.addAttribute("danhSachMail",danhSachMail);
        model.addAttribute("id", nguoiDung.getId());
        System.out.println(danhSachMail.size());
        return "main_view";
    }

    @GetMapping("nguoi-dung/mail-theo-nhan")
    public String hienThiTheoNhan(@RequestParam("idNguoiDung") Integer idNguoiDung, @RequestParam("idNhan") Integer idNhan, Model model) {
        List<Email> danhSachMail = (List<Email>) service.layMailTheoNguoiDungVaNhan(idNguoiDung, idNhan);
        Collections.reverse(danhSachMail);
        model.addAttribute("id",idNguoiDung);
        model.addAttribute("danhSachMail", danhSachMail);
        return "main_view";

    }

    @GetMapping("nguoi-dung/xem-chi-tiet/{idEmail}")
    public String hienThiMail(@PathVariable("idEmail") Integer idEmail, Model model) {
        Email email = emailRepository.findById(idEmail).get();
        model.addAttribute("email", email);
        model.addAttribute("ngayNhan", email.getNgay_nhan().toString());
        return "chi_tiet";
    }
}
