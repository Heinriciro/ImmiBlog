package com.ianhein.immiblog2.Service;

import com.ianhein.immiblog2.domain.User;
import org.springframework.stereotype.Service;


@Service
public interface UserService {

    User checkUser(String username, String password);
}
