package com.model2.mvc.web.user;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;


//==> 회원관리 RestController
@RestController
@RequestMapping("/api/users/*")
public class UserRestController {
	
	///Field
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	//setter Method 구현 않음
		
	public UserRestController(){
		System.out.println(this.getClass());
	}
	
	@GetMapping( value="session")
	public User getUser( HttpSession httpSession ) throws Exception{
		
		System.out.println("/api/users : GET");
		User returnUser = (User)httpSession.getAttribute("user");
		
		if(returnUser!=null) {
			returnUser.setPassword("");
			
		} else {
			returnUser = new User();
		}
		System.out.println("여기는GET myData"+returnUser);
		//Business Logic
		return returnUser;
	}
	@DeleteMapping("session")
	public String signOut( HttpSession session, HttpServletResponse response) throws Exception{
		session.setAttribute("user", null);
		System.out.println("여기는DelMETHOD"+session.getAttribute("user"));
		
		return "SignOut OK";
	}
	@PostMapping( value="session")
	public String signIn(	@RequestBody User user,
									HttpSession session, HttpServletResponse response ) throws Exception{
	
		System.out.println("api/users/session : POST");
		//Business Logic
		System.out.println("::"+user);
		User dbUser=userService.getUser(user.getUserId());
		System.out.println("AfterLogin"+dbUser);
		////////////////////////////////////////////////////////////////////////////////
		// 아이디가 없다면.
		if( dbUser==null ) {
			//dbUser = new User();
			return "아이디가 존재하지 않습니다.";
		}
		/////////////////////////////////////////////////////////////////////////////////
		
		if( user.getPassword().equals(dbUser.getPassword())){
			session.setAttribute("user", dbUser);
		
		} else { 
			return "비밀번호가 맞지 않습니다.";
		}
		
		System.out.println((User)session.getAttribute("user"));
		return "로그인 성공";
	}
}
