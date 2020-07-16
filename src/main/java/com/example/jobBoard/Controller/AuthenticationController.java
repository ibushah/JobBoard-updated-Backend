package com.example.jobBoard.Controller;

import com.example.jobBoard.Commons.ApiResponse;
import com.example.jobBoard.Commons.AuthToken;
import com.example.jobBoard.Config.JwtTokenUtil;
import com.example.jobBoard.Dto.LoginUser;
import com.example.jobBoard.Dto.UserDto;
import com.example.jobBoard.Model.User;
import com.example.jobBoard.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ApiResponse<AuthToken> register(@RequestBody LoginUser loginUser) throws AuthenticationException {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));

        final User user = userService.findOne(loginUser.getUsername());
        if(user!=null){
            final String token = jwtTokenUtil.generateToken(user);

            return new ApiResponse<>(200, "success",new AuthToken(token,user.getName(),user.getUserType(),user.getEmail()));
        }

      return new ApiResponse<>(404, "RECORD NOT FOUND",new AuthToken("","","",""));
    }


    @PostMapping("/user")
    public ApiResponse<User> saveUser(@RequestBody UserDto user){

        return new ApiResponse<>(HttpStatus.OK.value(), "User saved successfully.",userService.save(user));
    }

    @RequestMapping(value = "/getusers", method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return this.userService.findAll();
    }

    @PutMapping("/update/{id}")
    public ApiResponse<User> updateUser(@PathVariable("id") Long id,@RequestBody UserDto user){

        return new ApiResponse<>(HttpStatus.OK.value(),"User updated successfully",userService.update(user,id));
    }



    @DeleteMapping("/delete/{id}")
    public List<User> deleteUserById(@PathVariable Long id){
        return this.userService.getActiveUsers(id);

    }
}
