import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class SWEA2382 {
	
	//상 하 좌 우 순 4방위
	final static int[] dy = {-1, 1, 0, 0};
	final static int[] dx = {0, 0, -1, 1};

	public static void main(String[] args) throws Exception{
		// N*N 지도
		// 가장바깥에는 약품 칠했다
		// 각 군집(한칸 차지)은 y, x, 미생물 수, 이동방향을 가진다
		// 시간마다 방향따라 다음 셀로 이동
		// 약품구역에 도착하게 되면 미생물이 절반으로 줄고, 이동방향 반대로
		// 약품에 도달할때 1마리만 있었으면 군집은 사라진다
		
		// 군집이 모일 경우 하나의 군집으로 합쳐진다(이 과정은 모든 이동이 끝난 후 이루어진다)
		// 미생물 수는 합친 거
		// 이동 방향은 미생물 수가 많은 군집의 이동방향
		// 합쳐질 때 수가 같은 경우는 주어지지 않는다
		
		// M시간동안 격리한 후 남아 있는 미생물 수의 총합 구하기
		
		// 지도는 100*100 이하
		// 군집 수는 1_000이하
		// 격리시간은 1_000이하
		
		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");
		Map<Coord, PriorityQueue<Goon>> map = new HashMap<>();
		
		int T = Integer.parseInt(in.readLine());
		
		for(int test_case = 1; test_case <= T; test_case++) {
			//테스트 케이스 시작
			st = new StringTokenizer(in.readLine());
			map.clear();
			
			int mapLength = Integer.parseInt(st.nextToken());	//N, 맵 길이
			int maxTime = Integer.parseInt(st.nextToken());		//M, 격리 시간
			int numGoon = Integer.parseInt(st.nextToken());		//K, 군집의 개수
			
			int y, x, misaeng, dir;
			
			Goon[] goons = new Goon[numGoon];
			for(int i = 0; i < numGoon; i++) {
				st = new StringTokenizer(in.readLine());
				y = Integer.parseInt(st.nextToken());
				x = Integer.parseInt(st.nextToken());
				misaeng = Integer.parseInt(st.nextToken());
				dir = Integer.parseInt(st.nextToken()) - 1;		//방향은 -1로 받기
				
				goons[i] = new Goon(i, y, x, misaeng, dir);
			}
			//입력 종료
			
			Goon goon;
			int nY, nX;
			for(int t = 0; t < maxTime; t++) {
				//시간 흐르기
				map.clear();		//맵 정보 초기화
				for(int i = 0; i < numGoon; i++) {
					goon = goons[i];	//이 군집에 대해 조작하기
					if(goon == null) {
						//null이라는 뜻은 해당군은 죽었다는 소리다
						continue;
					}
					
					//다음 위치 계산해보기
					goon.y += dy[goon.dir];
					goon.x += dx[goon.dir];
					
					//맵 끝에 도달한 경우 -> 방향 전환 및 미생물 수 줄이기
					if(goon.y == 0 || goon.y == mapLength-1) {
						goon.dir = (goon.dir + 1) % 2;
						goon.misaeng /= 2;
						if(goon.misaeng == 0) {
							//해당 군은 죽었다
							goons[i] = null;
							continue;
						}
					}
					if(goon.x == 0 || goon.x == mapLength-1) {
						goon.dir = 2 + (goon.dir + 1) % 2;
						goon.misaeng /= 2;
						if(goon.misaeng == 0) {
							//해당 군은 죽었다
							goons[i] = null;
							continue;
						}
					}
					

					
					if(!map.containsKey(new Coord(goon.y, goon.x))) {
						//해당 위치에 다른 애들이 없으면 -> PQ넣기
						map.put(new Coord(goon.y, goon.x), new PriorityQueue<Goon>());
					}
					map.get(new Coord(goon.y, goon.x)).add(goon);	//해당 군을 그 위치에 넣기
				}
				
				//군집 이동 완료
				map.forEach((coord, pq)->{
					if(pq.size() > 1) {
						// 1보다 크다 -> 해당 칸에 군들이 여러개 있다는 소리다
						// -> 군집 합치는 과정 수행
						Goon maxGoon = pq.poll();

						Goon otherGoon = null;
						while( !pq.isEmpty() ) {
							//다른 애들 다 꺼내자
							otherGoon = pq.poll();
							maxGoon.misaeng += otherGoon.misaeng;
							
							//다른 애들 다 죽이자
							goons[otherGoon.id] = null;
						}
					}
				});
			}
			
			int result = 0;	//남은 미생물 수 계산
			for(int i = 0; i < numGoon; i++) {
				if(goons[i] == null) continue;	//죽은 군집이면 패스
				result += goons[i].misaeng;
			}
			
			sb.append(String.format("#%d %d\n", test_case, result));
		}
		
		System.out.println(sb.toString());
	}
	
	static class Coord{
		int y;
		int x;
		public Coord(int y, int x) {
			this.y = y;
			this.x = x;
		}
		@Override
		public int hashCode() {
			return this.y*101 + this.x;
		}
		@Override
		public boolean equals(Object obj) {
			Coord other = (Coord)obj;
			return this.y == other.y && this.x == other.x;
		}
	}
	
	static class Goon implements Comparable<Goon>{
		int id;
		int y;
		int x;
		int misaeng;	//미생물의 수
		int dir;
		
		public Goon(int id, int y, int x, int misaeng, int dir) {
			this.id = id;
			this.y = y;
			this.x = x;
			this.misaeng = misaeng;
			this.dir = dir;
		}
		
		@Override
		public int compareTo(Goon other) {
			return -(this.misaeng - other.misaeng);
		}
	}
}
