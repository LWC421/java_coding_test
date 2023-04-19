import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

public class B1184{
	
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		int mapLength = Integer.parseInt(in.readLine());
		
		if(mapLength == 1) {
			//맵 크기가 1이면 바로 리턴
			System.out.println(0);
			return;
		}
		
		int[][] map = new int[mapLength+2][mapLength+2];
		//해당 좌표에서 
		Map<Integer, Integer>[][] ltMap = new HashMap[mapLength+1][mapLength+1];
		Map<Integer, Integer>[][] rtMap = new HashMap[mapLength+1][mapLength+1];
		Map<Integer, Integer>[][] lbMap = new HashMap[mapLength+1][mapLength+1];
		Map<Integer, Integer>[][] rbMap = new HashMap[mapLength+1][mapLength+1];
		
		for(int y = 1; y <= mapLength; y++) {
			st = new StringTokenizer(in.readLine());
			for(int x = 1; x <= mapLength; x++) {
				map[y][x] = Integer.parseInt(st.nextToken());
				ltMap[y][x] = new HashMap<>();
				rtMap[y][x] = new HashMap<>();
				lbMap[y][x] = new HashMap<>();
				rbMap[y][x] = new HashMap<>();
			}
		}
		//입력 종료
		
		//누적합으로 변경
		for(int y = 1; y <= mapLength; y++) {
			for(int x = 1; x <= mapLength; x++) {
				map[y][x] += map[y-1][x] + map[y][x-1] - map[y-1][x-1];
			}
		}
		
		//각 위치마다 크기만큼의 누적합을 구해보기
		for(int y = 1; y <= mapLength; y++) {
			for(int x = 1; x <= mapLength; x++) {
				Map<Integer, Integer> lt = ltMap[y][x];
				Map<Integer, Integer> rt = rtMap[y][x];
				Map<Integer, Integer> lb = lbMap[y][x];
				Map<Integer, Integer> rb = rbMap[y][x];

				//Left-Top방향으로 가는 영역에 대해 처리
				for(int dy = 0; dy < y; dy++) {
					for(int dx = 0; dx < x; dx++) {
						int cost = map[y][x] - map[dy][x] - map[y][dx] + map[dy][dx];
						lt.computeIfPresent(cost, (key, value) -> {
							return value+1;
						});	//해당 값에 대해 있으면 +1
						lt.computeIfAbsent(cost, (key) -> {
							return 1;
						}); //없을 경우 1 넣어주기
					}
				}
				
				//Right - Top방향
				for(int dy = 0; dy < y; dy++) {
					for(int dx = x; dx <= mapLength; dx++) {
						int cost = map[y][dx] - map[y][x-1] - map[dy][dx] + map[dy][x-1];
						rt.computeIfPresent(cost, (key, value) -> {
							return value+1;
						});	//해당 값에 대해 있으면 +1
						rt.computeIfAbsent(cost, (key) -> {
							return 1;
						}); //없을 경우 1 넣어주기
					}
				}
				
				//Left - Bottom 방향
				for(int dy = y; dy <= mapLength; dy++) {
					for(int dx = 0; dx < x; dx++) {
						int cost = map[dy][x] - map[y-1][x] - map[dy][dx] + map[y-1][dx];
						lb.computeIfPresent(cost, (key, value) -> {
							return value+1;
						});	//해당 값에 대해 있으면 +1
						lb.computeIfAbsent(cost, (key) -> {
							return 1;
						}); //없을 경우 1 넣어주기
					}
				}
				
				//Right - Bottom 방향
				for(int dy = y; dy <= mapLength; dy++) {
					for(int dx = x; dx <= mapLength; dx++) {
						int cost = map[dy][dx] - map[y-1][dx] - map[dy][x-1] + map[y-1][x-1];
						rb.computeIfPresent(cost, (key, value) -> {
							return value+1;
						});	//해당 값에 대해 있으면 +1
						rb.computeIfAbsent(cost, (key) -> {
							return 1;
						}); //없을 경우 1 넣어주기
					}
				}
			}
		}
		
		//결과값 저장
		int result = 0;

		for(int y = 1; y < mapLength; y++) {
			for(int x = 1; x < mapLength; x++) {
				Map<Integer, Integer> lt = ltMap[y][x];
				Map<Integer, Integer> rb = rbMap[y+1][x+1];
				
				Set<Entry<Integer, Integer>> ltSet = lt.entrySet();
				for(Entry<Integer, Integer> ltE: ltSet) {
					int ltKey = ltE.getKey();
					if(rb.containsKey(ltKey)) {
						//동일한 모양의 땅이 오른쪽 아래땅에도 있으면
						result += (ltE.getValue() * rb.get(ltKey));
					}
				}
			}
		}
		
		for(int y = 1; y < mapLength; y++) {
			for(int x = 2; x <= mapLength; x++) {
				Map<Integer, Integer> rt = rtMap[y][x];
				Map<Integer, Integer> lb = lbMap[y+1][x-1];
				
				Set<Entry<Integer, Integer>> rtSet = rt.entrySet();
				for(Entry<Integer, Integer> rtE: rtSet) {
					int rtKey = rtE.getKey();
					if(lb.containsKey(rtKey)) {
						result += (rtE.getValue() * lb.get(rtKey));
					}
				}
			}
		}
		
		System.out.println(result);
//		Map<Integer, Integer> test = ltMap[3][3];
//		Set<Entry<Integer, Integer>> t = test.entrySet();
//		
//		for(Entry<Integer, Integer> s : t) {
//			System.out.printf("%d : %d\n", s.getKey(), s.getValue());
//		}
	}
}