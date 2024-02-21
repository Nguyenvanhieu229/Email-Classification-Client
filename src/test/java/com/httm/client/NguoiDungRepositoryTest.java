package com.httm.client;

import com.httm.client.Service.EmailService;
import com.httm.client.entity.Email;
import com.httm.client.entity.NguoiDung;
import com.httm.client.entity.NguoiDungOServer;
import com.httm.client.repository.NguoiDungRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class NguoiDungRepositoryTest {
    @Autowired private NguoiDungRepository repo;

    @Test
    public void testAdd() throws Exception{
        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setGioiTinh("nam");
        nguoiDung.setHoTen("Nguyen Duc Hoan");
        nguoiDung.setUsername("abcdef");
        nguoiDung.setPassword("23445655");
        nguoiDung.setSoDienThoai("0378787689");
        NguoiDung nd = repo.save(nguoiDung);

        Assertions.assertSame(nguoiDung, nd);
    }

    @Test
    public void listAll() {
        List<NguoiDung> ds = (List<NguoiDung>) repo.findAll();
        Assertions.assertEquals(ds.size(), 2);

        for(NguoiDung i : ds) {
            System.out.println(i.toString());
        }
    }

    @Test
    public void testUpdate() {
        Integer ngid = 1;
        NguoiDung nd = repo.findById(ngid).get();
        nd.setPassword("888888");
        repo.save(nd);

        NguoiDung updatend = repo.findById(ngid).get();
        Assertions.assertEquals(nd, updatend);
    }

//    @Test
//    public void getMail() {
//        EmailService service = new EmailService();
//        List<Email> ds = service.layDanhSachMail(new NguoiDungOServer());
//        for(Email email : ds) {
//            System.out.println(email.getNgay_nhan().toString());
//        }
//        Assertions.assertNotNull(ds);
//    }

}
