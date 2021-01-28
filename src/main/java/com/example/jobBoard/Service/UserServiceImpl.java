package com.example.jobBoard.Service;

import com.example.jobBoard.Dto.ResetPasswordDTO;
import com.example.jobBoard.Dto.RiderStatusDTO;
import com.example.jobBoard.Model.Profile;
import com.example.jobBoard.Model.ResetPassword;
import com.example.jobBoard.Repository.ProfileRepository;
import com.example.jobBoard.Repository.ResetPasswordRepository;
import com.example.jobBoard.Repository.UserDaoRepository;
import com.example.jobBoard.Commons.ApiResponse;
import com.example.jobBoard.Dto.UserDto;
import com.example.jobBoard.Model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;


@Service(value = "myUserService")
public class UserServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserDaoRepository userDaoRepository;


	@Autowired
	private ResetPasswordRepository resetPasswordRepository;

	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	ProfileRepository profileRepository;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Value("${linkToForgotPassword}")
	private String linkToForgotPassword;

	@Value("${spring.mail.username}")
	private String senderEmail;



	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDaoRepository.findByEmail(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user.getUserType()));
	}

	private List<SimpleGrantedAuthority> getAuthority(String role) {
		return Arrays.asList(new SimpleGrantedAuthority(role));
	}

	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userDaoRepository.findByActive().iterator().forEachRemaining(list::add);
		return list;
	}

	public ApiResponse<List<User>> delete(Long id) {
		Optional<User> userOptional = userDaoRepository.findById(id);
		if(userOptional.isPresent()){
			userDaoRepository.deleteById(id);
		}
		return new ApiResponse<>(HttpStatus.OK.value(), "User saved successfully.",	userDaoRepository.findAll());
	}

	public User findOne(String username) {

		 User user = userDaoRepository.findByEmailAndActive(username,Boolean.TRUE);
		 return user;

	}

	public User findById(Long id) {
		Optional<User> optionalUser = userDaoRepository.findById(id);

		return optionalUser.isPresent() ?  optionalUser.get() : null;
	}

    public UserDto update(UserDto userDto, Long id) {
        User user = findById(id);
        if(user != null) {
            BeanUtils.copyProperties(userDto, user, "password");
            userDaoRepository.save(user);
        }
        return userDto;
    }

	public ApiResponse save(UserDto user) {
		User founduser = userDaoRepository.findByEmail(user.getEmail());
		if(founduser == null) {
			User newUser = new User();
			newUser.setEmail(user.getEmail());
			newUser.setName(user.getName());
			newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
			newUser.setUserType(user.getUserType());
			newUser.setActive(user.getActive());
			newUser.setProfileActive(false);
			newUser.setUserOnlineStatus(false);

			if(user.getUserType().equals("employer")){
				Profile companyProfile = new Profile();
				companyProfile.setUser(newUser);
				newUser.setName(user.getLegalCompanyName());
				companyProfile.setName(user.getLegalCompanyName());
				companyProfile.setContactName(user.getName());
				newUser.setProfileActive(true);
				profileRepository.save(companyProfile);
			}
			return new ApiResponse<>(HttpStatus.OK.value(), "User saved successfully.",	userDaoRepository.save(newUser));//return ;
		}else{
			return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "User Already exsist.",null);//return ;
		}
	}


	public List<User> getActiveUsers(Long id){
		Optional<User> user = userDaoRepository.findById(id);
		if(user.isPresent()){
			User user1 = user.get();
			user1.setActive(false);
			userDaoRepository.save(user1);

			List<User> activeUsers = userDaoRepository.findByActive();
			return activeUsers;
 		}

		return null;
	}

	public ApiResponse forgotPasswordEmail(String email){
		User user = userDaoRepository.findByEmail(email);
		if(user!=null){
			UUID uuid = UUID.randomUUID();
			ResetPassword resetPassword=new ResetPassword();
			resetPassword.setToken(uuid.toString());
			resetPassword.setUserId(user.getId());
			resetPasswordRepository.save(resetPassword);
			try {
				mailToUser(email,uuid.toString());
				return new ApiResponse(200, "A link has been sent to your email! Kindly check");
			}catch (Exception e ){
				return new ApiResponse(421, "Error sending email");
			}


		}else {
			return new ApiResponse(404,"Please Enter Correct Email");
		}



	}
	void mailToUser(String recevierEmail,String token) throws UnknownHostException {
//		InetAddress myIP= InetAddress.getLocalHost();
//		String linktoResetPassword=myIP.getHostAddress()+":4200/#/resetPassword?token="+token;

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(recevierEmail);
		msg.setFrom(senderEmail);

		msg.setSubject("Change Password ");
		msg.setText("Click here to reset your password :"+linkToForgotPassword+token);

		javaMailSender.send(msg);

	}

	public ApiResponse resetPassword(ResetPasswordDTO resetPasswordDTO){
		ResetPassword resetPassword=resetPasswordRepository.findByToken(resetPasswordDTO.getToken());
		if(resetPassword!=null){
			User founduser=userDaoRepository.getOne(resetPassword.getUserId());
			founduser.setPassword(bcryptEncoder.encode(resetPasswordDTO.getPassword()));
			userDaoRepository.save(founduser);
			return new ApiResponse(200,"Password Successfuly Updated ");

		}else {
			return new ApiResponse(500,"Password Reset Failed ! Some Error Occured");
		}


	}

	public  ApiResponse changingUserOnlineStatus(RiderStatusDTO riderStatusDTO){
	  	Optional<User> foundUser=userDaoRepository.findById(riderStatusDTO.getUserId());
		if (foundUser.isPresent()) {
			 User userToUpdate=foundUser.get();
			userToUpdate.setUserOnlineStatus(riderStatusDTO.getUserOnlineStatus());
			 userDaoRepository.save(userToUpdate);
			 	return new ApiResponse(200,"You Are Active Now");

		}else {
			return new ApiResponse(500," ! Some Error Occured");
		}


	}


}
