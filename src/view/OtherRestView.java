package view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import controller.UserController;
import model.Session;
import model.dto.RestaurantDTO;
import model.dto.UserDTO;

public class OtherRestView {
	public OtherRestView() {
		Scanner sc = new Scanner(System.in);
		UserDTO loginUser = (UserDTO)Session.getData("loginUser");
		UserController controller = new UserController();
		//로그인 유저를 제외한 유저들의 user_id를 key, 그 유저들의 맛집리스트를 value로 하는 hashmap
		HashMap<String, ArrayList<RestaurantDTO>> listByOther = new HashMap<String, ArrayList<RestaurantDTO>>();
		boolean checker = false;
		
		ArrayList<UserDTO> idlist = controller.getOtherList(loginUser);
		
		for (int i = 0; i < idlist.size(); i++) {
			ArrayList<RestaurantDTO> list = controller.getListByUDTO(idlist.get(i));
			listByOther.put(idlist.get(i).getUser_id(), list);
		}

		while(true) {
			System.out.println("=======다른 회원님들 리스트 확인=======");
			for (int i = 0; i < idlist.size(); i++) {
				System.out.println((i+1)+". "+idlist.get(i).getUser_id()+"님의 점수: "+idlist.get(i).getUser_point());
			}
			//추가부분
			System.out.println("===========================");
			System.out.println("1. 정렬하기\t2. 해당 유저 확인하기");
			int choice = sc.nextInt();
			//user_point순으로 정렬
			
			Collections.sort(idlist, (o1,o2) -> o1.getUser_point() - o2.getUser_point());
				
			System.out.println("확인하실 유저 번호를 골라주세요.");
			choice = sc.nextInt();
			System.out.println("===========================");
			int k = 1;
			
			for (RestaurantDTO rdto : listByOther.get(idlist.get(choice-1).getUser_id())) {
				System.out.printf("%d %s - %s\n 주소: %s\n 맛집 전화번호 : %s (%s님 등록 맛집)", k++, rdto.getName(),
						rdto.getCategory(), rdto.getAddr(),
						rdto.getPhone(), idlist.get(choice-1).getUser_id());
				System.out.println("===========================");
			}
			
			System.out.println("===========================");
			System.out.println("다른 회원님들의 리스트를 보시겠습니까?");
			System.out.println("1. Y\t2. N");
			int select = sc.nextInt();
			
			if(select == 2)
				break;
		}
	}
}