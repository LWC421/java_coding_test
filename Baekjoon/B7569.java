import java.io.*;
import java.util.*;

public class B7569 {
	
	// 1 : 익은 토마토
	// 0 : 익지 않은 토마토
	// -1 : 토마토가 들었지 않은 칸
	static int[][][] map;					//토마토의 상태
	static boolean[][][] visited;			//방문 체크용
	static int yLength, xLength, zLength;	//맵 크기 받기용
	final static int[] dz = {1, -1, 0, 0, 0, 0};
	final static int[] dy = {0, 0, 1, -1, 0, 0};
	final static int[] dx = {0, 0, 0, 0, 1, -1};
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		xLength = Integer.parseInt(st.nextToken());
		yLength = Integer.parseInt(st.nextToken());
		zLength = Integer.parseInt(st.nextToken());
		
		map = new int[zLength][yLength][xLength];
		visited = new boolean[zLength][yLength][xLength];
		
		List<Coord> initTomatos = new LinkedList<>();
		
		int num;
		int remainTomato = 0;	//익어야 되는 토마토의 개수 저장
		
		
		for(int z = 0; z < zLength; z++) {
			for(int y = 0; y < yLength; y++) {
				st = new StringTokenizer(in.readLine());
				for(int x = 0; x < xLength; x++) {
					num = Integer.parseInt(st.nextToken());
					map[z][y][x] = num;
					
					if(num == 1) {
						//익은 토마토이면
						initTomatos.add(new Coord(z, y, x));
					}
					else if(num == 0) {
						remainTomato++;	//익어야 되는 토마토의 개수 추가하기
					}
				}
			}
		}
		//입력 종료
		
		Queue<Coord> q = new ArrayDeque<>();
		//초기 토마토 넣기
		for(Coord c : initTomatos) {
			q.add(c);
			visited[c.z][c.y][c.x] = true;	//방문 할 예정으로 넣기
		}
		
		int time = 0;		//익는데 걸리는 시간
		Coord curr = null;	//스택에서 빼낸거 받기용
		int nZ, nY, nX;		//다음 위치 확인용
		
		while( !q.isEmpty() && (remainTomato != 0) ) {
			int size = q.size();
			
			for(int i = 0; i < size; i++) {
				curr = q.poll();			//익은 토마토 중 하나 가져와서
				
				//6방향 탐색
				for(int d = 0; d < 6; d++) {
					nZ = curr.z + dz[d];
					nY = curr.y + dy[d];
					nX = curr.x + dx[d];
					
					//벗어나는 경우 체크
					if( nZ < 0 || nZ >= zLength) continue;
					if( nY < 0 || nY >= yLength) continue;
					if( nX < 0 || nX >= xLength) continue;
					
					//이미 방문한 경우
					if(visited[nZ][nY][nX]) continue;
					if(map[nZ][nY][nX] == 0) {
						//다음 칸이 익지 않은 토마토이면
						visited[nZ][nY][nX] = true;	//방문 예정으로 넣고
						q.add(new Coord(nZ, nY, nX));	//스택에 넣기
						remainTomato--;			//남은 토마토의 개수 줄이기
					}
				}
			}
			
			time++;
		}
		
		if(remainTomato == 0) {
			//모두 제대로 익었으면
			System.out.println(time);
		}
		else {
			//덜 익었으면
			System.out.println(-1);
		}
	}
	
	static class Coord{
		int z, y, x;
		
		public Coord(int z, int y, int x) {
			this.z = z;
			this.y = y;
			this.x = x;
		}
		@Override
		public String toString() {
			return String.format("[%d, %d, %d]", this.z, this.y, this.x);
		}
	}
}
