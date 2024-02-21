package com.httm.client.repository;


import org.springframework.data.repository.CrudRepository;
import com.httm.client.entity.NguoiDung;
public interface NguoiDungRepository extends
        CrudRepository<NguoiDung, Integer> {
    public Long countById(Integer id);

}
