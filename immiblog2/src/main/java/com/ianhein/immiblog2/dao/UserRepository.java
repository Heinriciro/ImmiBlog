package com.ianhein.immiblog2.dao;

import com.ianhein.immiblog2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username, String password);

}
