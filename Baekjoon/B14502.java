import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class B14502 {
	
	//4방위 탐색용
	public static final int[] dy = {0, 1, 0, -1};
	public static final int[] dx = {1, 0, -1, 0};
	public static List<Coord> safes;		//안전구역(0)의 위치들
	public static int numSafe;				//초기 안전구역(0)의 개수
	public static List<int[]> combiSafes;	//벽을 치는 조합들
	public static int[] currentCombi;
	
	//맵 크기
	static int yLength; //N, 세로 크기
	static int xLength; //M, 가로 크기
	
	
	public static void main(String[] args) throws Exception{
		// [N, M]의 직사각형 연구소
		// 빈칸, 벽
		// 어떤 칸은 바이러스 존재 -> 4방위로 퍼져나감
		// 벽은 3개 세울 수 있다 -> 무조건 3개는 세워야 한다
		// 0: 빈칸, 1: 벽, 2: 바이러스
		
		//벽을 세웠을 때 바이러스가 퍼지지 않는 안전영역의 최대값
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		yLength = Integer.parseInt(st.nextToken());	//N, 세로 크기
		xLength = Integer.parseInt(st.nextToken());	//M, 가로 크기
		
		//length -> [3~8]
		
		int[][] map = new int[yLength+2][xLength+2];	//+2로해서 맵을 만들자
		combiSafes = new ArrayList<>();
		currentCombi = new int[3];	//현재 만들고 있는 조합 확인용
		
		int num;	//입력 받기용
		numSafe = 0;
		safes = new ArrayList<>();
		Set<Coord> virusCoords = new HashSet<>();	//바이러스 위치 저장용
		for(int y = 1; y <= yLength; y++) {
			st = new StringTokenizer(in.readLine());
			for(int x = 1; x <= xLength; x++) {
				num = Integer.parseInt(st.nextToken());
				map[y][x] = num;
				
				if(num == 2) {
					//바이러스 일 경우 -> 위치를 넣어주자
					virusCoords.add(new Coord(y, x));
				}
				if(num == 0) {
					//빈칸(안전구역)일 경우
					safes.add(new Coord(y, x));
					numSafe++;
				}
			}
		}
		//입력 종료
		
		
		//가장자리 벽으로 치기
		for(int y = 0, limit=yLength+1; y <= limit; y++) {
			map[y][0] 		  = 1;
			map[y][xLength+1] = 1;
		}
		for(int x = 0, limit=xLength+1; x <= limit; x++) {
			map[0][x] 		  = 1;
			map[yLength+1][x] = 1;
		}
		//벽치기 종료

		combination(0, 0);
		
		int result = 0;
		
		for(int[] combi: combiSafes) {
			int[][] copyMap = copyMap(map, combi);
			
			int tmp = bfs(copyMap, virusCoords);
			result = Math.max(result, tmp);
		}
		
		System.out.println(result);
	}
	
	public static void combination(int count, int start){
		if(count == 3) {
			//벽은 3개 쳐야 한다 -> 3개를 다 쳤으면
			int[] combi = new int[3];
			for(int i = 0; i < 3; i++) {
				combi[i] = currentCombi[i];	//콤비를 복사해서
			}
			combiSafes.add(combi);	//해당 조합을 넣자
			return;
		}
		
		for(int i = start; i < numSafe; i++) {
			currentCombi[count] = i;
			combination(count+1, i+1);
		}
	}
	
	public static int[][] copyMap(int[][] map, int[] combi){
		int[][] copyMap = new int[yLength+2][xLength+2];	//+2로해서 맵을 만들자
		
		for(int y = 0, yLimit = yLength+1; y <= yLimit; y++) {
			for(int x = 0, xLimit = xLength+1; x <= xLimit; x++) {
				copyMap[y][x] = map[y][x];
			}
		}
		
		for(int index: combi) {
			Coord coord = safes.get(index);
			copyMap[coord.y][coord.x] = 1;	//해당 위치에 벽 치기
		}
		
		return copyMap;
	}
	
	public static int bfs(int[][] map, Set<Coord> virusCoords) {
		Queue<Coord> q = new ArrayDeque<>();
		for(Coord virus: virusCoords) {
			q.add(virus);	//바이러스 초기위치 넣기
		}
		
		Coord virus = null;	//큐에서 꺼낸거 받기용
		int nY, nX;	//다음 좌표 저장용
		while( !q.isEmpty() ) {
			virus = q.poll();	//큐에서 꺼내서
			
			for(int d = 0; d < 4; d++) {
				nY = virus.y + dy[d];
				nX = virus.x + dx[d];
				
				if(map[nY][nX] == 1 || map[nY][nX] == 2) {
					//이미 바이러스이거나 벽이면 가지 않는다
					continue;
				}
				
				map[nY][nX] = 2;			//다음위치를 미리 바이러스로 지정해주고
				q.add(new Coord(nY, nX));	//큐에 넣자
			}
		}
		
		//안전구역 확인해보기
		int result = 0;
		for(int y = 1; y <= yLength; y++) {
			for(int x = 1; x <= xLength; x++) {
				if(map[y][x] == 0) {
					//안전 구역이면
					result++;
				}
			}
		}
		
		return result;
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
			return y*10 + x;
		}

		@Override
		public boolean equals(Object obj) {
			//hash코드로 비교하자
			return this.hashCode() == ((Coord)obj).hashCode();
		}
		
		public String toString() {
			return String.format("[%d, %d]", y, x);
		}
	}
}
