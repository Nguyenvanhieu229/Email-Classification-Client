package com.httm.client.Service;

import com.httm.client.entity.ServerMail;
import com.httm.client.repository.ServerMailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service

public class ServerMailService implements Serializable {
    @Autowired private ServerMailRepository repo;
    public ServerMail findByName(String ten_may_chu) {
        List<ServerMail> ds = (List<ServerMail>) repo.findAll();
        for(ServerMail i : ds) {
            if(i.getTenMayChu().equals(ten_may_chu))
                return i;
        }
        return null;
    }
}
