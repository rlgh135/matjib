package view;

import java.util.Scanner;

import controller.UserController;
import model.Session;
import model.dto.UserDTO;

public class LoginView {
	public LoginView() {
		Scanner sc = new Scanner(System.in);
		UserController controller = new UserController();
		System.out.print("아이디 : ");
		String userid = sc.next();
		System.out.print("비밀번호 : ");
		String userpw = sc.next();
		
		//컨트롤러로 아이디와 비밀번호를 넘겨주며 요청
		if(controller.login(userid, userpw)) {
			//이미 세션에 loginUser라는 Key로 객체 하나가 세팅되어 있는 상태
			UserDTO loginUser = (UserDTO)Session.getData("loginUser");
			System.out.println(loginUser.getUser_name()+"님 어서오세요~");
			new MainView();
		}
		else {
			System.out.println("로그인 실패 / 아이디 혹은 비밀번호를 다시 확인해주세요~");
		}
	}
}