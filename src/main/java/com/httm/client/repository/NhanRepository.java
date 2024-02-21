package com.httm.client.repository;

import com.httm.client.entity.NhanDuDoan;
import org.springframework.data.repository.CrudRepository;

public interface NhanRepository extends CrudRepository<NhanDuDoan, Integer> {
}
