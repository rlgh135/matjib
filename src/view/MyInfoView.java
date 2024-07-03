package view;

import java.util.Scanner;

import controller.UserController;
import model.Session;
import model.dto.UserDTO;

public class MyInfoView {
	public MyInfoView() {
		Scanner sc = new Scanner(System.in);
		UserController controller = new UserController();
		UserDTO loginUser = (UserDTO)Session.getData("loginUser");
		//세션에 있는 정보 띄워주기
		System.out.println("======"+loginUser.getUser_name()+"님의 회원정보======");
		System.out.println("아이디 : "+loginUser.getUser_id());
		System.out.println("비밀번호 : "+loginUser.getUser_pw());
		System.out.println("핸드폰 번호 : "+loginUser.getUser_phone());
		System.out.println("주소 : "+loginUser.getUser_addr());
		System.out.println("============================");
		
		System.out.println("1. 비밀번호 수정\n2. 핸드폰 번호 수정\n3. 주소 수정\n4. 수정 취소\n5. 회원탈퇴");
		int choice = sc.nextInt();
		if(choice == 4) {
			System.out.println("메인으로 돌아갑니다");
		}
		else if(choice == 5) {
			//회원탈퇴
			System.out.print("비밀번호 재입력 : ");
			String userpw = sc.next();
			//로그인된 유저의 비밀번호와 비교
			if(loginUser.getUser_pw().equals(userpw)) {
				//회원탈퇴 요청
				if(controller.userWithDraw(loginUser.getUser_id())) {
					System.out.println("그 동안 이용해주셔서 감사합니다...ㅠ_ㅠ");
					Session.setData("loginUser", null);
				}
			}
		}
		else {
			System.out.print("새로운 정보 : ");
			sc = new Scanner(System.in);
			String newData = sc.nextLine();
			
			//아이디, 무엇을 수정할지, 어떻게 수정할지
			if(controller.modifyUser(loginUser.getUser_id(), choice, newData)) {
				System.out.println("정보 수정 완료!");
			}
			else {
				System.out.println("정보 수정 실패 / 다음에 다시 시도해주세요.");
			}
		}
	}
}