import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SWEA2115 {
	
	static int selectLimit;	//M, 선택할 수 있는 벌통의 개수 [1 ~ 5];
	static int honeyLimit;	//C, 채취할 수 있는 꿀의양, [10 ~ 30]
	static Honey[][] map;		//맵 정보
	
	public static void main(String[] args) throws Exception{
		// N * N개의 벌통이 있다
		// 각 칸은 꿀의 양
		
		/*
		1. 일꾼은 두명이다, M만큼 벌통 채취
			각각의 일꾼은 가로로 연속되도록 M개 선택
			서로 겹칠 수 없다
		2. 선택한 영역에서 꿀 채취
			두 일꾼이 채취할 수 있는 꿀의 최대 양은 C이다
			꿀은 합치지 않고 각각 들고 있는다
		3. 각 꿀통의 제곱만큼이 얻게되는 수익이다
			이 제곱한 수들을 다 더한 것이 최종 수익이다
		*/
		
//		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");
		
		int length;		//N, 벌통의 길이, [3 ~ 10]
		//각 칸당 1~9이하의 정수
		
		int T = Integer.parseInt(in.readLine());
		int max = 0;	//수확의 최대값 저장
		
		for(int test_case = 1; test_case <= T; test_case++) {
			//테스트 케이스 시작
			st = new StringTokenizer(in.readLine());
			max = 0;
			
			//첫 줄 입력
			length = Integer.parseInt(st.nextToken());
			selectLimit = Integer.parseInt(st.nextToken());
			honeyLimit = Integer.parseInt(st.nextToken());
			
			//맵 입력
			map = new Honey[length][length];
			int number;
			for(int y = 0; y < length; y++) {
				st = new StringTokenizer(in.readLine());
				for(int x = 0; x < length; x++) {
					number = Integer.parseInt(st.nextToken());
					map[y][x] = new Honey(number, number*number);	//그냥 숫자와, 제곱한 숫자 넣기
				}
			}
			//입력 종료
			
			Coord c1 = new Coord(0, 0);
			Coord c2 = new Coord(0, 0);
			
			Max m1 = new Max();
			Max m2 = new Max();
			for(int y = 0; y < length; y++) {
				for(int x = selectLimit-1; x < length; x++) {
					//이거는 일꾼1의 선택
					c1.y = y;
					c1.x = x;
					go(c1.y, c1.x, m1);
					
					//같은 행에서 일꾼2가 선택할 수도 있다
					for(int x2 = x+selectLimit; x2 < length; x2++) {
						c2.y = y;
						c2.x = x2;
						go(c2.y, c2.x, m2);
//						System.out.printf("same : %d - %d\n", m1.value, m2.value);
						max = Math.max(max, m1.value + m2.value);
					}
					
					for(int cy = y+1; cy < length; cy++) {
						for(int cx = selectLimit-1; cx < length; cx++) {
							//다른 행에서 일꾼2가 선택하는 경우
							c2.y = cy;
							c2.x = cx;
							go(c2.y, c2.x, m2);
//							System.out.printf("%d - %d\n", m1.value, m2.value);
							max = Math.max(max, m1.value + m2.value);
						}
					}
				}
			}
			
			sb.append(String.format("#%d %d\n", test_case, max));
		}
		
		System.out.println(sb.toString());
	}
	
	public static void go(int y, int x, Max m) {
		m.reset();
		Honey[] selected = new Honey[selectLimit];	//뭐 선택하는지
		for(int dx = 0; dx < selectLimit; dx++) {
			//어디 선택한건지 넣기
			selected[dx] = map[y][x-dx];
		}
		
		perm(selected, 0, 0, 0, m);
	}
	
	public static void perm(Honey[] selected, int count, int sumHoney, int sumHoneySquare, Max m) {
		m.set(sumHoneySquare);
		if(count == selectLimit) {
			//다 선택한 경우
			return;
		}
		
		if(sumHoney + selected[count].honey <= honeyLimit) {
			//꿀을 가져갈 수 있는 경우
			perm(selected, count+1, sumHoney+selected[count].honey, sumHoneySquare+selected[count].honeySquare, m);	//선택 하는 경우
		}
		
		perm(selected, count+1, sumHoney, sumHoneySquare, m);	//선택하지 않는 경우
	}
	
	static class Max{
		int value;
		
		public Max() {
			this.value = 0;
		}
		
		public void set(int v) {
			this.value = Math.max(this.value, v);
		}
		
		public void reset() {
			this.value = 0;
		}
	}
	
	static class Coord{
		int y, x;
		
		public Coord(int y, int x) {
			this.y = y;
			this.x = x;
		}
	}
	
	static class Honey{
		int honey;			//꿀
		int honeySquare;	//꿀 제곱
		
		//각 칸마다의 정보 담기
		//생성자
		public Honey(int honey, int honeySquare) {
			this.honey = honey;
			this.honeySquare = honeySquare;
		}

		@Override
		public String toString() {
			return String.format("[%d, %d]", honey, honeySquare);
		}
	}
}
