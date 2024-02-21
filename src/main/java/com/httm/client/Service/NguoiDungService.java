package com.httm.client.Service;

import com.httm.client.entity.NguoiDung;
import com.httm.client.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service

public class NguoiDungService implements Serializable {
    @Autowired private NguoiDungRepository repo;
    public List<NguoiDung>  listAll() {
        return (List<NguoiDung>)repo.findAll();
    }
    public NguoiDung save(NguoiDung nd) {
        NguoiDung daThem = repo.save(nd);
        return daThem;
    }

    public NguoiDung find(NguoiDung nd) {
        List<NguoiDung> ds = (List<NguoiDung>) repo.findAll();
        for(NguoiDung i : ds) {
            if(i.getUsername().equals(nd.getUsername()) && i.getUsername().equals(nd.getUsername()))
                return i;
        }
        return null;
    }

    public Boolean checkLogin(NguoiDung nguoiDung) {
        List<NguoiDung> ds = (List<NguoiDung>) repo.findAll();
        for(NguoiDung nd : ds) {
            if(nd.getUsername().equals(nguoiDung.getUsername()) && nd.getPassword().equals(nguoiDung.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public void delete(Integer id) {
        Long count = repo.countById(id);
        if( count != null && count > 0)
            repo.deleteById(id);
    }
}
