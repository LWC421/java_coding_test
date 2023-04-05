import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class B3055 {
	
	//각 칸마다 정의
	public static final char EMPTY = '.';
	public static final char WATER = '*';
	public static final char ROCK = 'X';
	public static final char FINISH = 'D';
	public static final char START = 'S';

	//4방위 탐색용
	public static final int[] dy = {0, 1, 0, -1};
	public static final int[] dx = {1, 0, -1, 0};
	
	public static char[][] map = null;
	public static Set<Coord> waters = null;	//물위치 저장용
	
	
	public static void main(String[] args) throws Exception{
		// R*C 맵
		// . : 빈칸
		// * : 물 -> 고슴도치가 이동 불가
		// X : 돌 -> 물, 고슴도치 이동 불가
		// D : 비버의 굴
		// S : 고슴도치의 위치
		
		// S -> D로 가야한다
		// 4방위 탐색
		// 물은 매분마다 비어있는 칸으로 확장
	
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		int yLength = Integer.parseInt(st.nextToken());	//R, 세로 길이
		int xLength = Integer.parseInt(st.nextToken());	//C, 가로 길이
		
		map = new char[yLength+2][xLength+2];
		waters = new HashSet<>();
		
		int startY = -1; 
		int startX = -1;
		
		for(int y = 1; y <= yLength; y++) {
			char[] row = in.readLine().toCharArray();
			for(int x = 1; x <= xLength; x++) {
				if(row[x-1] == START) {
					//만약 시작 지점이면
					startY = y;
					startX = x;
					map[y][x] = '.';	//빈칸으로 표시
					continue;
				}
				if(row[x-1] == WATER) {
					//물이면
					waters.add(new Coord(y, x));	//물의 위치 넣기
				}
				map[y][x] = row[x-1];
			}
		}
		//입력 종료
		
		//가장자리 돌로 벽치기
		for(int y = 0, limit=yLength+2; y < limit; y++) {
			map[y][0] = ROCK;
			map[y][xLength+1] = ROCK;
		}
		for(int x = 0, limit=xLength+2; x < limit; x++) {
			map[0][x] = ROCK;
			map[yLength+1][x] = ROCK;
		}
		//벽치기 종료
		
		boolean[][] visited = new boolean[yLength+2][xLength+2];
		
		Queue<Coord> q = new ArrayDeque<>();
		q.add(new Coord(startY, startX));	//시작지점 넣어주기
		visited[startY][startX] = true;		//첫 지점은 방문예정으로 넣기
		
		boolean finished = false;	//도착 여부 확인
		Coord curr = null;			//q에서 꺼낸거 확인용
		int time = 0;				//시간 측정용
		int nY, nX;
		Set<Coord> waterTmp = new HashSet<>();	//물 확장 된거 받기용
		Loop: while( !q.isEmpty() ) {
			//물 확장
			waters.forEach((water) -> {
				//각각의 물에 대해
//				System.out.println(water);
				map[water.y][water.x] = WATER;	//해당 지점은 물로 표기
				
				for(int d = 0; d < 4; d++) {
					int nextY = water.y + dy[d];
					int nextX = water.x + dx[d];
//					System.out.println(nextY + ", " + nextX + " -> " + map[nextY][nextX]);
					if(map[nextY][nextX] == EMPTY) {
						//빈칸일때만 확장하자
						waterTmp.add(new Coord(nextY, nextX));
					}
				}
			});
			
			waters.clear();				//그전에 있던건 비우고
			waters.addAll(waterTmp);	//물 확장된거 넣어주자
			waterTmp.clear();			//임시 물 확장은 비우자
			
			
			int qSize = q.size();
			
			//고슴도치의 이동
			for(int i = 0; i < qSize; i++) {
				curr = q.poll();	//큐에서 꺼내서
				if(map[curr.y][curr.x] == WATER) continue;	//만약 물로 찼었으면
				if(map[curr.y][curr.x] == FINISH) {
					finished = true;		//도착했다는 뜻
					break Loop;	//그만하자
				}
				
				for(int d = 0; d < 4; d++) {
					nY = curr.y + dy[d];
					nX = curr.x + dx[d];
					if(map[nY][nX] == ROCK) continue;	//돌이면 패스
					if(map[nY][nX] == WATER) continue;	//물이면 패스
					if(visited[nY][nX]) continue;		//방문했으면 패스
					q.add(new Coord(nY, nX));	//여기 방문할거라고 넣고
					visited[nY][nX] = true;		//방문 예정으로 체크
				}
			}
			
			
			
			time++;
		}
		
		//도착 여부에 따라 출력해주자
		System.out.println(finished ? time : "KAKTUS");
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
			return y * 51 + x;
		}


		@Override
		public boolean equals(Object obj) {
			Coord other = (Coord)obj;
			return this.y == other.y && this.x == other.x;
		}


		@Override
		public String toString() {
			return String.format("[%d, %d]", this.y, this.x);
		}
	}
}
