import java.io.*;
import java.util.*;


public class B1726 {
	// 현재 보고 있는 방향으로 1 ~ 3칸을 간다
	// 왼쪽또는 오른쪽으로 90도 회전한다
	
	//맵은 
	// 0 : 갈 수 있다
	// 1 : 로봇이 갈 수 없다
	
	static final int LIMIT = 100 * 100 * 5 + 1;
	
	static int yLength;
	static int xLength;
	
	//동 서 남 북 순서 ->
	//동 남 서 북
	final static int[] dy = {0, 1, 0, -1};
	final static int[] dx = {1, 0, -1, 0};
	
	static int[][] map;		//맵
	static int[][][] visited;	//방문 체크용
	
	public static void main(String[] args) throws Exception {
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		yLength = Integer.parseInt(st.nextToken());	//세로의 길이
		xLength = Integer.parseInt(st.nextToken());	//가로의 길이
		
		map = new int[yLength+2][xLength+2];
		visited = new int[yLength+2][xLength+2][4];
		
		//맵 입력
		for(int y = 1; y <= yLength; y++) {
			st = new StringTokenizer(in.readLine(), " ");
			for(int x = 1; x <= xLength; x++) {
				map[y][x] = Integer.parseInt(st.nextToken());
			}
		}
		//맵 입력 종료
		
		for(int y = 1; y <= yLength; y++) {
			for(int x = 1; x <= xLength; x++) {
				for(int d = 0; d < 4; d++) {
					visited[y][x][d] = LIMIT;
				}
			}
		}
		
		int y, x, dir;
		st = new StringTokenizer(in.readLine());
		y = Integer.parseInt(st.nextToken());
		x = Integer.parseInt(st.nextToken());
		dir = Integer.parseInt(st.nextToken());
		if(dir == 1) dir = 0;		//동
		else if(dir == 2) dir = 2;	//서
		else if(dir == 3) dir = 1;	//남
		else if(dir == 4) dir = 3;	//북
				
		Robot start = new Robot(y, x, dir);	//시작지점에 대한 정보 저장
		
		st = new StringTokenizer(in.readLine());
		y = Integer.parseInt(st.nextToken());
		x = Integer.parseInt(st.nextToken());
		dir = Integer.parseInt(st.nextToken());
		if(dir == 1) dir = 0;		//동
		else if(dir == 2) dir = 2;	//서
		else if(dir == 3) dir = 1;	//남
		else if(dir == 4) dir = 3;	//북
		Robot finish = new Robot(y, x, dir);	//종료지점에 대한 정보 저장


		PriorityQueue<Data> pq = new PriorityQueue<>();
		pq.add(new Data(start.y, start.x, start.dir, 0));	//start에서 시작, 0번의 명령
		
		int result = LIMIT;
		//계속 반복
		int nY, nX, nCount;
		Data curr = null;
		while( !pq.isEmpty() ) {
			curr = pq.poll();
			if(curr.count > result) {
				//result보다 크면은
				break;
			}
			
			if(curr.y == finish.y && curr.x == finish.x) {
				//만약 도착점이면
				int minRotate = Math.min(Math.abs(curr.dir - finish.dir), Math.abs(curr.dir+4 - finish.dir));
				minRotate = Math.min(minRotate, Math.abs(curr.dir - (finish.dir + 4)));
				result = Math.min(curr.count + minRotate, result);
				continue;
			}
			
			Loop: for(int d = 0; d < 4; d++) {
				//4방위에 대해
				for(int dis = 1; dis <= 3; dis++){
					//1 ~ 3칸씩 전부 돌려보자
					nY = curr.y + (dy[d] * dis);
					nX = curr.x + (dx[d] * dis);
					if(nY <= 0 || nY > yLength) continue;
					if(nX <= 0 || nX > xLength) continue;	//맵 밖나가는 거 체크

					int minRotate = Math.min(Math.abs((curr.dir) + 4 - d), Math.abs(curr.dir - d));
					minRotate = Math.min(minRotate, Math.abs(curr.dir - (d + 4)));
					nCount = curr.count + (minRotate + 1);	//해당 칸으로 가는 데 필요한 명령 횟수
					
					if(nCount >= visited[nY][nX][d]) continue;	//만약 해당 칸으로 가는 게 이득이 아니면
					for(int dd = 1; dd <= dis; dd++) {
						if(map[curr.y + (dy[d] * dd)][curr.x + (dx[d] * dd)] == 1) continue Loop;		//벽일 경우
					}
					
					pq.add(new Data(nY, nX, d, nCount));	//해당 칸에 갔을때에 대해 체크
					visited[nY][nX][d] = nCount;	//해당 칸은 해당 명령으로 간다고 표시
				}
			}
		}
		System.out.println(result);
	}
	
	static class Data implements Comparable<Data>{
		int y;
		int x;
		int dir;
		int count;
		
		public Data(int y, int x, int dir, int count) {
			this.y = y;
			this.x = x;
			this.dir = dir;
			this.count = count;
		}
		
		@Override
		public int compareTo(Data other) {
			return this.count - other.count;
		}
		
		@Override
		public String toString() {
			String direction = "";
			if(dir == 0) direction = "동";
			else if(dir == 1) direction = "남";
			else if(dir == 2) direction = "서";
			else if(dir == 3) direction = "북";
			return String.format("[%d, %d] : %s, %dcount", y, x, direction, count);
		}
	}
	
	static class Robot{
		int y;
		int x;
		int dir;	//0 -> 동쪽 ...
		
		public Robot(int y, int x, int dir) {
			this.y = y;
			this.x = x;
			this.dir = dir;
		}
		
		@Override
		public String toString() {
			return String.format("[%d, %d] : %d", y, x, dir);
		}
	}
}
