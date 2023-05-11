import java.io.*;
import java.util.*;

public class B1520 {
	
	//리미트 설정
	final static int LIMIT = 1_000_000_000 + 1;
	
	//4방위 탐색용
	final static int[] dy = {1, 0, -1, 0};
	final static int[] dx = {0, 1, 0, -1};
	
	//높이들 저장
	static int[][] map;
	
	static int[][] dpTable;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		int yLength = Integer.parseInt(st.nextToken());
		int xLength = Integer.parseInt(st.nextToken());
		
		dpTable = new int[yLength+2][xLength+2];
		
		map = new int[yLength+2][xLength+2];
		for(int y = 1; y <= yLength; y++) {
			st = new StringTokenizer(in.readLine(), " ");
			for(int x = 1; x <= xLength; x++) {
				map[y][x] = Integer.parseInt(st.nextToken());
				dpTable[y][x] = LIMIT;	//일단 LIMIT로 설정
			}
		}
		//입력 종료
		
		//벽 치기
		for(int x = 0, last = xLength+1; x <= last; x++) {
			map[0][x] = LIMIT;
			map[yLength+1][x] = LIMIT;
		}
		for(int y = 0, last = yLength+1; y <= last; y++) {
			map[y][0] = LIMIT;
			map[y][xLength+1] = LIMIT;
		}
		//벽치기 종료
		
		
		int nY, nX, nHeight;
		int currHeight;
		for(int y = 1; y <= yLength; y++) {
			for(int x = 1; x <= xLength; x++) {
				currHeight = map[y][x];
				int count = 0;
				for(int d = 0; d < 4; d++) {
					nY = y + dy[d];
					nX = x + dx[d];
					nHeight = map[nY][nX];
					
					if(nHeight == LIMIT) continue;	//맵 밖이면
					if(currHeight > nHeight) {
						//여기서 갈 수 있는 것이 있으면 카운트
						count++;
					}
				}
				if(count == 0) {
					//만약 이 지점에서 빠져나갈 수 없으면
					dpTable[y][x] = 0;
				}
			}
		}
		dpTable[1][1] = 1;	//처음 지점은 1로 지정
		
//		for(int[] row: dpTable) {
//			System.out.println(Arrays.toString(row));
//		}
//		
		dpTable[yLength][xLength] = LIMIT;	//마지막 지점은 다시 초기화
		int result = dp(yLength, xLength);
		
		System.out.println(result);
	}
	
	static int dp(int y, int x) {
//		System.out.printf("[%d, %d]\n", y, x);
		if(dpTable[y][x] != LIMIT) {
			//저장된 값이 있으면
			return dpTable[y][x];
		}
		if(dpTable[y][x] == 0) {
			return 0;
		}
		
		dpTable[y][x] = 0;
		
		int currHeight = map[y][x];
		int nY, nX, nHeight;
		
		for(int d = 0; d < 4; d++) {
			nY = y + dy[d];
			nX = x + dx[d];
			nHeight = map[nY][nX];
			
			if(nHeight == LIMIT) {
				//맵 밖이면
				continue;
			}
			
			if(nHeight > currHeight) {
				//도착지점에서 역순으로 가므로 
				//더 높아지는 곳으로 가야한다
				dpTable[y][x] += dp(nY, nX);
			}
		}
		
		return dpTable[y][x];
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
			return y * 501 + x;
		}
		
		@Override
		public boolean equals(Object o) {
			Coord other = (Coord)o;
			return this.y == other.y && this.x == other.x;
		}
		
		@Override
		public String toString() {
			return String.format("[%d, %d]", y, x);
		}
	}
}
