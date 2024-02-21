package com.httm.client.Service;

import com.httm.client.entity.NguoiDungOServer;
import com.httm.client.repository.NguoiDungOServerRepository;
import com.httm.client.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class NguoiDungOServerService implements Serializable {
    @Autowired private NguoiDungOServerRepository repo;

    public NguoiDungOServer dangKi(NguoiDungOServer nguoiDungOServer) {
        return repo.save(nguoiDungOServer);
    }

    public NguoiDungOServer checkLogin(NguoiDungOServer nguoiDungOServer) {
        List<NguoiDungOServer> ds = (List<NguoiDungOServer>) repo.findAll();
        for(NguoiDungOServer i : ds) {
            if(i.getNguoiDung().getUsername().equals(nguoiDungOServer.getNguoiDung().getUsername()) &&
            i.getNguoiDung().getPassword().equals(nguoiDungOServer.getNguoiDung().getPassword()) &&
                    i.getServerMail().getTenMayChu().equals(nguoiDungOServer.getServerMail().getTenMayChu()))
                return i;
        }
        return null;
    }

}
