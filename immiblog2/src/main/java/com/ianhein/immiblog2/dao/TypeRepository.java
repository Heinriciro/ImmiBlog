package com.ianhein.immiblog2.dao;

import com.ianhein.immiblog2.domain.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<Type,Long> {
    Type findByName(String name);

    @Query("select t from t_type t")
    List<Type> findTop(Pageable pageable);

}
