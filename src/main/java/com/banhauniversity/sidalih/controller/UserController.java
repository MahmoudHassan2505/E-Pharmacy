package com.banhauniversity.sidalih.controller;

import com.banhauniversity.sidalih.dto.AddUser;
import com.banhauniversity.sidalih.entity.User;
import com.banhauniversity.sidalih.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy/users")
public class UserController {

    @Autowired private UserService userService;

    @GetMapping
    public List<User> findAll(){
        return userService.findAll();
    }

    @PostMapping()
    public User addUser(@RequestBody AddUser addUser){
        return userService.add(addUser);
    }

    @PostMapping("/deactivate")
    public String deactivateUser(@RequestParam String username){
        userService.deactivateUser(username);
        return "User deactivated Successfully";
    }

    @PostMapping("/activate")
    public String activateUser(@RequestParam String username){
        userService.activateUser(username);
        return "User activated Successfully";
    }
}
