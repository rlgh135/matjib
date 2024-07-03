package view;

import java.util.Scanner;

import controller.UserController;
import model.dto.UserDTO;

public class JoinView {
	public JoinView() {
		Scanner sc = new Scanner(System.in);
		UserController controller = new UserController();
		System.out.println("======회원가입 페이지 입니다======");
		System.out.print("아이디 : ");
		String user_id = sc.next();
		//위에서 입력받은 userid 값을 controller에 넘겨주며 체크 요청
		if(controller.checkId(user_id)) {
			System.out.print("비밀번호 : ");
			String user_pw = sc.next();
			System.out.print("비밀번호 확인 : ");
			String user_pw_re = sc.next();
			//비밀번호 체크는 백 과의 소통이 필요 없고, 입력받은 정보로 모두 해결 가능
			//controller의 메소드 호출이 없음
			if(user_pw.equals(user_pw_re)) {
				System.out.print("이름 : ");
				String user_name = sc.next();
				System.out.print("핸드폰 번호 : ");
				String user_phone = sc.next();
				System.out.print("주소 : ");
				sc = new Scanner(System.in);
				String user_addr = sc.nextLine();
				
				//입력받은 정보들을 하나의 객체(DTO)로 포장
				UserDTO user = new UserDTO(user_id, user_pw, user_name, user_phone, user_addr);
				
				//정보를 담고 있는 DTO를 컨트롤러에 넘겨주며 가입 처리 요청
				if(controller.join(user)) {
					System.out.println("회원가입 성공!");
				}
				else {
					System.out.println("회원가입 실패 / 다음에 다시 시도해 주세요~");
				}
			}
			else {
				System.out.println("비밀번호 확인을 다시 해주세요!");
			}
		}
		else {
			System.out.println("중복된 아이디가 있습니다!");
		}
	}
}