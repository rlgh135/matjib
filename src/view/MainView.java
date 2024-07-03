package view;

import java.util.Scanner;

import model.Session;
import model.dto.UserDTO;

public class MainView {
	public MainView() {
		UserDTO loginUser = (UserDTO)Session.getData("loginUser");
		Scanner sc = new Scanner(System.in);
		
		System.out.println("======메인 화면======");
		while(loginUser != null) {
			System.out.println("1. 맛집 검색\n2. 유저 랭킹\n3. 맛집 리스트 확인\n"
					+ "4. 내 맛집 리스트 보기\n5. 다른 사람들 보기 \n6.내 정보 보기\n7. 로그아웃");
			
			int choice = sc.nextInt();
			if(choice == 7) {
				System.out.println(loginUser.getUser_id()+"님 다음에 또 오세요~");
				Session.setData("loginUser", null);
				break;
			}
			
			switch(choice) {
			case 1: 
				new SearchView(loginUser.getUser_id());
				break;
			case 2: 
				new RankingView();
//				new AddRestView();
				break;
			case 3: 
				new RestView();
//				new RemoveRestView();
				break;
			case 4: 
				new MyRestView(); 
				break;
			case 5:
				new OtherRestView();
				break;
			case 6: 
				//회원탈퇴는 이 케이스 안에서 new WithdrawView();로 만들어주세요
				new MyInfoView();
				loginUser = (UserDTO)Session.getData("loginUser");
				break;
			}
		}
	}
//	public MainView() {
//		UserDTO loginUser = (UserDTO)Session.getData("loginUser");
//		Scanner sc = new Scanner(System.in);
//		
//		System.out.println("======메인 화면======");
//		while(loginUser != null) {
//			System.out.println("1. 맛집 검색\n2. 맛집 추가\n3. 맛집 삭제\n"
//					+ "4. 내 맛집 리스트 보기\n5. 다른 사람들 맛집 리스트 보기 \n6.내 정보 수정\n7. 로그아웃");
//			
//			int choice = sc.nextInt();
//			if(choice == 7) {
//				System.out.println(loginUser.getUser_id()+"님 다음에 또 오세요~");
//				Session.setData("loginUser", null);
//				break;
//			}
//			
//			switch(choice) {
//			case 1: 
//				new SearchView(loginUser.getUser_id());
//				break;
//			case 2: 
//				new AddRestView();
//				break;
//			case 3: 
//				new RemoveRestView();
//				break;
//			case 4: 
//				new MyRestView(); 
//				break;
//			case 5:
//				new OtherRestView();
//				break;
//			case 6: 
//				//회원탈퇴는 이 케이스 안에서 new WithdrawView();로 만들어주세요
//				new MyInfoView();
//				loginUser = (UserDTO)Session.getData("loginUser");
//				break;
//			}
//		}
//	}
}