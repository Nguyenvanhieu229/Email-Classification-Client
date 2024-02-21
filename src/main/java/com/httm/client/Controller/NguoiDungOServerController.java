package com.httm.client.Controller;

import com.httm.client.Service.NguoiDungOServerService;
import com.httm.client.Service.NguoiDungService;
import com.httm.client.Service.ServerMailService;
import com.httm.client.entity.ServerMail;
import com.httm.client.entity.NguoiDung;
import com.httm.client.entity.NguoiDungOServer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NguoiDungOServerController {

    @Autowired private NguoiDungOServerService service;
    @Autowired private NguoiDungService nguoiDungService;
    @Autowired private ServerMailService serverMailService;

    @GetMapping("nguoi-dung/moi")
    public String showDangKi() {
        return "dang_ki";
    }
    @PostMapping("/nguoi-dung/dang-ki")
    public String dangKi(HttpServletRequest request, Model model) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String tenMayChu = request.getParameter("tenMayChu");
        String tenDayDu = request.getParameter("tenDayDu");
        String diaChiTrenMayChu = request.getParameter("diaChiTrenMayChu");
        String matKhauTrenMayChu = request.getParameter("matKhauTrenMayChu");
        String soDienThoai = request.getParameter("soDienThoai");
        String gioiTinh = request.getParameter("gioiTinh");

        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setPassword(password);
        nguoiDung.setUsername(username);
        nguoiDung.setSoDienThoai(soDienThoai);
        nguoiDung.setGioiTinh(gioiTinh);
        nguoiDung.setHoTen(tenDayDu);

        NguoiDung da_ton_tai = nguoiDungService.find(nguoiDung);
        if(da_ton_tai == null) {
            nguoiDung = nguoiDungService.save(nguoiDung);
        }

        ServerMail serverMail = serverMailService.findByName(tenMayChu);

        NguoiDungOServer nguoiDungOServer = new NguoiDungOServer();
        nguoiDungOServer.setNguoiDung(nguoiDung);
        nguoiDungOServer.setServerMail(serverMail);
        nguoiDungOServer.setDiaChiTrenMayChu(diaChiTrenMayChu);
        nguoiDungOServer.setMatKhauTrenMayChu(matKhauTrenMayChu);
        nguoiDungOServer = service.dangKi(nguoiDungOServer);

        model.addAttribute("nguoiDungOServer", nguoiDungOServer);
        return "redirect:/nguoi-dung/giao-dien-chinh";
    }

    @PostMapping("/nguoi-dung/dang-nhap")
    public String dangNhap(@ModelAttribute("nguoidung") NguoiDungOServer nguoidung, RedirectAttributes re) {
        NguoiDungOServer nd_check =  service.checkLogin(nguoidung);
        if(nd_check != null) {
            re.addAttribute("nguoidung", nd_check);
            return "redirect:/nguoi-dung/giao-dien-chinh";
        }
        else {
            re.addFlashAttribute("message", "Thông tin đăng nhập sai!");
            return "dang_nhap";
        }
    }
}
