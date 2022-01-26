package com.ianhein.immiblog2.Service;

import com.ianhein.immiblog2.dao.UserRepository;
import com.ianhein.immiblog2.domain.User;
import com.ianhein.immiblog2.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Util.code(password));
        return user;
    }

    public static void main(String[] args) {
        String code = MD5Util.code("forever1997!");
        System.out.println(code);
    }
}
