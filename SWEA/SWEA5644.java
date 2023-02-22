

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SWEA5644 {
	
	static int limitTime;		//총 이동 시간 ( [20 ~ 100] )
	static int numCharger;		//배터리의 개수 ( [1 ~ 8] )
	static Charger[] chargers;	//배터리의 정보
	static Coord[] person1;		//사람 A
	static Coord[] person2;		//사람 B
	static int currentCharge;	//현재까지 충전한 량
	

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");
		
		
		int T = Integer.parseInt(in.readLine());
		
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(in.readLine());
			currentCharge = 0;	//충전량 초기화
			
			limitTime = Integer.parseInt(st.nextToken());	//사용자의 총 이동시간
			numCharger = Integer.parseInt(st.nextToken());	//충전기의 개수
		
			//배터리 배열 초기화
			chargers = new Charger[numCharger];
			
			//사람의 좌표 정보를 시간별로 저장 -> 끽해봐야 100개니까 다 저장하기
			person1 = new Coord[limitTime+1];
			person2 = new Coord[limitTime+1];
			
			//A, B의 시작위치 넣기
			person1[0] = new Coord(1, 1);
			person2[0] = new Coord(10, 10);
			
			//A의 이동 정보 받기
			int direction = 0;
			int y = 1;
			int x = 1;
			st = new StringTokenizer(in.readLine());
			for(int i = 1; i <= limitTime; i++) {
				direction = Integer.parseInt(st.nextToken());
				//상, 우, 하, 좌
				if(direction == 1) y--;
				else if(direction ==2) x++;
				else if(direction ==3) y++;
				else if(direction ==4) x--;
				
				person1[i] = new Coord(y, x);
			}
			//B의 이동 정보 받기
			y = 10;
			x = 10;
			st = new StringTokenizer(in.readLine());
			for(int i = 1; i <= limitTime; i++) {
				direction = Integer.parseInt(st.nextToken());
				//상, 우, 하, 좌
				if(direction == 1) y--;
				else if(direction ==2) x++;
				else if(direction ==3) y++;
				else if(direction ==4) x--;
				
				person2[i] = new Coord(y, x);
			}
			
			//배터리 정보 받기
			int area, power;
			for(int i = 0; i < numCharger; i++) {
				st = new StringTokenizer(in.readLine());
				x = Integer.parseInt(st.nextToken());
				y = Integer.parseInt(st.nextToken());
				area = Integer.parseInt(st.nextToken());
				power = Integer.parseInt(st.nextToken());
				
				chargers[i] = new Charger(y, x, area, power);
			}
			
			//출력이 쎈거부터 오도록 하기
			Arrays.sort(chargers, (a, b) -> {
				return -(Integer.compare(a.power, b.power));
			});
			
			
			//이동 경로에 따라 배터리 수혈받기
			Coord p1, p2;	//좌표 정보 가져오기 위해
			Charger c1A, c2A;	//A가 먼저 선택했을때
			Charger c1B, c2B;	//B가 먼저 선택했을 때
			int aCharge, bCharge;	//A가 먼저 선택했을 때 전력량과 B가 먼저 선택했을 때 전력량 비교용
			
			for(int i = 0; i <= limitTime; i++) {
				c1A = null;
				c2A = null;
				c1B = null;
				c2B = null;
				
				aCharge = 0;
				bCharge = 0;
				
				p1 = person1[i];
				p2 = person2[i];
				
				//A부터 충전기를 찾아보기
				//A가 충전가능한거 있는지 확인
				for(int j = 0; j < numCharger; j++) {
					//해당 경로에 대해 충전 가능한지 확인
					if(chargers[j].chargeable(p1)) {
						//A는 충전이 가능하면 무조건 충전하기 -> 출력이 강한거부터 검사하므로
						c1A = chargers[j];
						c1A.used = true;	//사용중으로 표시
						break;
					};
				}
				
				//B가 충전가능한 충전기가 있는지 확인
				for(int j = 0; j < numCharger; j++) {
					if(chargers[j].chargeable(p2) && !chargers[j].used) {
						//충전 가능하면서 A가 사용하고 있지 않은 충전기를 우선 찾기
						c2A = chargers[j];
						break;
					};
				}
				
				if(c1A != null) {
					//A가 충전기를 찾은 경우
					aCharge += c1A.power;	//출력만큼 더하기
					c1A.used = false;	//사용중 아니라고 다시 되돌리기
				}
				if(c2A != null) {
					//B가 충전기를 찾은 경우
					aCharge += c2A.power;	//출력만큼 더하기
				}
				
				//B부터 충전기를 찾아보기
				//B가 충전가능한 충전기가 있는지 확인
				for(int j = 0; j < numCharger; j++) {
					//해당 경로에 대해 충전 가능한지 확인
					if(chargers[j].chargeable(p2)) {
						//B는 충전이 가능하면 무조건 충전하기 -> 출력이 강한거부터 검사하므로
						c1B = chargers[j];
						c1B.used = true;	//사용중으로 표시
						break;
					};
				}
				
				//A가 충전가능한 충전기가 있는지 확인
				for(int j = 0; j < numCharger; j++) {
					if(chargers[j].chargeable(p1) && !chargers[j].used) {
						//충전 가능하면서 B가 사용하고 있지 않은 충전기를 우선 찾기
						c2B = chargers[j];
						break;
					};
				}
				
				if(c1B != null) {
					//A가 충전기를 찾은 경우
					bCharge += c1B.power;	//출력만큼 더하기
					c1B.used = false;	//사용중 아니라고 다시 되돌리기
				}
				if(c2B != null) {
					//B가 충전기를 찾은 경우
					bCharge += c2B.power;	//출력만큼 더하기
				}
				currentCharge += Math.max(aCharge, bCharge);
			}
			
			sb.append("#").append(test_case).append(" ").append(currentCharge).append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	//좌표
	static class Coord{
		int y;
		int x;
		
		Coord(int y, int x){
			this.y = y;
			this.x = x;
		}
		
		//다른 좌표와의 거리 구하기
		public int getDistance(Coord other) {
			return Math.abs(this.y - other.y) + Math.abs(this.x - other.x);
		}
		
		@Override
		public boolean equals(Object obj) {
			// TODO Auto-generated method stub
			Coord c = (Coord)obj;
			return this.y == c.y && this.x == c.x;
		}
	}
	
	//충전기
	static class Charger extends Coord{
		int area;	//영향 범위
		int power;	//충전 성능
		boolean used;	//몇 명 사용중인지
		
		Charger(int y, int x, int area, int power){
			super(y, x);
			this.area = area;
			this.power = power;
			this.used = false;	//처음엔 사용 안함
		}
		
		//충전 가능한지
		public boolean chargeable(Coord other) {
			int distance = getDistance(other);
			if(distance <= area) {
				//범위 안에 있으면
				return true;
			}
			
			return false;
		}

		@Override
		public String toString() {
			return "[area=" + area + ", power=" + power + ", used=" + used + "]";
		}
	}
}
