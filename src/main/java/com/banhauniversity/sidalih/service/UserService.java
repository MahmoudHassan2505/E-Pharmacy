package com.banhauniversity.sidalih.service;

import com.banhauniversity.sidalih.dto.AddUser;
import com.banhauniversity.sidalih.entity.Authority;
import com.banhauniversity.sidalih.entity.User;
import com.banhauniversity.sidalih.exception.CustomException;
import com.banhauniversity.sidalih.exception.ExceptionMessage;
import com.banhauniversity.sidalih.repository.AuthorityRepository;
import com.banhauniversity.sidalih.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthorityRepository authorityRepository;


    public User add(AddUser addUser) {

        User user = new User();
        user.setUsername(addUser.getUsername());
        user.setPassword("{noop}"+addUser.getPassword());
        user.setEnabled((short)1);
        user.setPhone(addUser.getPhone());

        userRepository.save(user);

        String authorityString = "ROLE_"+addUser.getAuthority().toUpperCase();
        Authority authority = new Authority();
        authority.setUsername(addUser.getUsername());
        authority.setAuthority(authorityString);
        authorityRepository.save(authority);
        if (addUser.getAuthority().toUpperCase().equals("ADMIN")){
            Authority authority2 = new Authority();
            authority2.setUsername(addUser.getUsername());
            authority2.setAuthority("ROLE_EMPLOYEE");
            authorityRepository.save(authority2);
        }

        return userRepository.findByUsername(user.getUsername()).orElseThrow(()->new CustomException(ExceptionMessage.CANNOT_CREATE_USER));
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(()->new CustomException(ExceptionMessage.ID_Not_Found));
    }

    public void deactivateUser(String username){
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setEnabled((short) 0);
            userRepository.save(user);
        }else {
            throw new CustomException(ExceptionMessage.ID_Not_Found);
        }

    }

    public void activateUser(String username){
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setEnabled((short) 1);
            userRepository.save(user);
        }else {
            throw new CustomException(ExceptionMessage.ID_Not_Found);
        }

    }

}
