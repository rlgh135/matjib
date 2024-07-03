package view;

import java.util.Scanner;

public class IndexView {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("-----------★빅데이터 맛집 검색 -2조 다이닝★-----------");

		while (true) {
			System.out.println("1. 검색하기\n2. 로그인\n3. 회원가입\n4. 나가기");
			int choice = sc.nextInt();

			if (choice == 1) {
				new SearchView();
			} else if (choice == 2) {
				new LoginView();
			} else if (choice == 3) {
				new JoinView();
			} else if (choice == 4) {
				System.out.println("종료합니다.");
				break;
			}
		}
	}
}