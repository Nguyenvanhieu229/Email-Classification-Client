package com.httm.client.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.httm.client.entity.*;
import com.httm.client.repository.EmailRepository;
import com.httm.client.repository.NhanRepository;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.FileEntity;
import com.google.gson.Gson;
import org.apache.http.entity.*;
import org.apache.http.impl.client.CloseableHttpClient;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.Utilities;

@Service
public class EmailService {
    @Autowired EmailRepository repo;
    @Autowired NhanRepository nhanrepo;

    public List<Email> layTatCaMail(NguoiDungOServer nguoiDungOServer) {
        List<Email> res = new ArrayList<>();
        List<Email> ds = (List<Email>) repo.findAll();
        for(Email i : ds) {
            if(i.getNguoiDungOServer().getId() == nguoiDungOServer.getId())
                res.add(i);
        }
        return res;
    }

    public List<Email> locTrung(List<Email> ds, NguoiDungOServer nguoidung) {
        List<Email> sanCo = this.layTatCaMail(nguoidung);
        List<Email> res = new ArrayList<>();
        for(Email i : ds) {
            int c = 0;
            for(Email j : sanCo) {
                if(i.getNoiDung().equals(j.getNoiDung()) && i.getNgay_nhan().getTime() == j.getNgay_nhan().getTime()){
                    c = 1;
                    break;
                }
            }
            if(c == 0) res.add(i);
        }
        return res;
    }

    public List<Email> layMailTheoNguoiDungVaNhan(Integer idNguoiDung, Integer idNhan) {
        List<Email> ds = (List<Email>) repo.findAll();
        List<Email> res = new ArrayList<>();
        for(Email i : ds) {
            if(i.getNguoiDungOServer().getId() == idNguoiDung && i.getNhanDuDoan().getId() == idNhan)
                res.add(i);
        }
        return res;
    }

    public Timestamp layNgayMuonNhat(NguoiDungOServer nguoiDungOServer) {

        List<Email> ds = (List<Email>) repo.findAll();
        if (ds.size() == 0)
            return null;
        Timestamp goc = ds.get(0).getNgay_nhan();
        for(Email email : ds) {
            if(email.getNguoiDungOServer().getDiaChiTrenMayChu().equals(nguoiDungOServer.getDiaChiTrenMayChu())) {
                Long t1 = goc.getTime();
                Long t2 = email.getNgay_nhan().getTime();
                if (t2 > t1) {
                    goc = new Timestamp(t2);
                }
            }
        }
        return goc;
    }

    public List<Email> layMailMoi(NguoiDungOServer nguoiDungOServer) {


        //khoi tao ket qua tra ve
        List<Email> res = new ArrayList<>();

        //Tao doi tuong dataobject de chuan bi chuyen thanh json
        DataObject dataObject = new DataObject();
        dataObject.setServer(nguoiDungOServer.getServerMail().getDiaChiMayChuNhan());
        dataObject.setEmail(nguoiDungOServer.getDiaChiTrenMayChu());
        dataObject.setPassword(nguoiDungOServer.getMatKhauTrenMayChu());

        //lay ngay muon nhat cua email trong danh sach
        Timestamp ngay_muon_nhat = this.layNgayMuonNhat(nguoiDungOServer);
        if(ngay_muon_nhat != null) {
            //chuyen doi sang localdate de lay ngay thang nam
            LocalDateTime tmp = ngay_muon_nhat.toLocalDateTime();
            dataObject.setNam(tmp.getYear());
            dataObject.setNgay(tmp.getDayOfMonth());
            dataObject.setThang(tmp.getMonthValue());
        }
        else {
            dataObject.setThang(9);
            dataObject.setNgay(22);
            dataObject.setNam(2023);
        }

        //Tao json_data
        Gson gson = new Gson();
        String json_data = gson.toJson(dataObject);

        //Tao httpclient va httppost de gui api
        String url = "http://192.168.105.130:2209/get-email";
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        //Dua json_data vao httppost
        httpPost.setHeader("Content-Type", "application/json");
        StringEntity jsonString = new StringEntity(json_data, "UTF-8");
        httpPost.setEntity(jsonString);

        //thuc hien gui va nhan du lieu

        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            String responceContent = EntityUtils.toString(httpResponse.getEntity());

            JsonObject jsonResponce = JsonParser.parseString(responceContent).getAsJsonObject();
            for(String email_id : jsonResponce.keySet()) {
                JsonObject emailResponce = jsonResponce.getAsJsonObject(email_id).getAsJsonObject();
                Email tmp = new Email();
                tmp.setTieuDe(emailResponce.get("Subject").getAsString());
                tmp.setNgay_nhan(Timestamp.valueOf(emailResponce.get("Date").getAsString()));
                tmp.setNguoiGui(emailResponce.get("From").getAsString());
                tmp.setNoiDung(emailResponce.get("Message").getAsString());
                tmp.setNguoiDungOServer(nguoiDungOServer);
                res.add(tmp);
            }


        }catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public List<Email> duDoan(List<Email> ds) {

        //kiem tra so luong email can du doan
        if(ds.size() == 0)
            return ds;

        //ghep noi dung email thanh mot chuoi duy nhat, voi phan phan tach *Phan_de_phan_tach*

        String mail_data = "";
        for(Email email : ds) {
            mail_data = mail_data + email.getNoiDung() +  "*Phan_de_phan_tach*";
        }
        //bo di doan *Phan_de_phan_tach* cuoi cung
        mail_data = mail_data.substring(0, mail_data.length() - 19);
        // thay doi vi json khong chap nhan ky tu '\n'
        mail_data = mail_data.replaceAll("\n", "\\n");

        //Tao chuoi json tu mail_data thong qua dataobjectmail va gson

        Gson gson = new Gson();
        DataObjectMail dataObjectMail = new DataObjectMail();
        dataObjectMail.setNoi_dung(mail_data);
        String json_data = gson.toJson(dataObjectMail);

        // Tao doi tuong httpclient, httppost de gui du lieu
        String url = "http://192.168.105.130:2209/classification";
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        //Set header va dua chuoi json vao noi dung httppost
        httpPost.setHeader("Content-Type", "application/json");
        StringEntity stringEntity = new StringEntity(json_data, "UTF-8");
        httpPost.setEntity(stringEntity);

        //thuc hien gui va nhan lai du lieu

        try {
            HttpResponse response = httpClient.execute(httpPost);
            String responceContent = EntityUtils.toString(response.getEntity());
            JsonObject jsonResponce = JsonParser.parseString(responceContent).getAsJsonObject();
            String[] dsNhan = jsonResponce.get("nhan").getAsString().split(" ");
            for(int i = 0; i < dsNhan.length; i++) {
                NhanDuDoan tmp = nhanrepo.findById(Integer.parseInt(dsNhan[i])).get();
                ds.get(i).setNhanDuDoan(tmp);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public void luuEmail(List<Email> ds) {
        for(Email i : ds) {
            repo.save(i);
        }
    }

}
