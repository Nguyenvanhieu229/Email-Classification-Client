package com.httm.client.Controller;

import com.httm.client.entity.NguoiDung;
import com.httm.client.entity.NguoiDungOServer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DangNhapController {

    @GetMapping("")
    public String showHomePage(Model model) {
        model.addAttribute("nguoidung", new NguoiDungOServer());
        return "dang_nhap";
    }

}
