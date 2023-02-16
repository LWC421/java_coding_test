import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class B7576 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		int max = Integer.MIN_VALUE;
		
		int yLength, xLength;	//상자의 크기
		st = new StringTokenizer(in.readLine());
		xLength = Integer.parseInt(st.nextToken());
		yLength = Integer.parseInt(st.nextToken());
		//[y, x, count]의 형태로 큐에 넣기
		Queue<int[]> q = new ArrayDeque<>();
		
		//1은 익은 토마토, 0은 익지 않은 토마토, -1은 토마토가 들어있지 않은 칸
		int[][] map = new int[yLength+2][xLength+2];
		boolean[][] visited = new boolean[yLength+2][xLength+2];
		
		int number;
		for(int y = 1; y <= yLength; y++) {
			st = new StringTokenizer(in.readLine());
			for(int x = 1; x <= xLength; x++) {
				number = Integer.parseInt(st.nextToken());
				map[y][x] = number;
				if(number == 1) {	//토마토이면
					q.add(new int[] { y, x, 0});	//시작지점으로 넣기
				}
			}
		}
		
		//벽치기
		for(int y = 0; y <= yLength+1; y++) {
			map[y][0] 		  = -1;	//토마토가 들어있지 않다고 표시하기
			map[y][xLength+1] = -1;
		}
		for(int x = 0; x <= xLength+1; x++) {
			map[0][x] 		  = -1;
			map[yLength+1][x] = -1;
		}
		
		//BFS하기
		int[] tmp;
		int curY;
		int curX;
		int count;
		int nextY;
		int nextX;
		
		//4방위 탐색
		int[] dy = {-1, 0, 0, 1};
		int[] dx = {0, -1, 1, 0};
		while( !q.isEmpty() ) {
			tmp = q.poll();
			curY = tmp[0];
			curX = tmp[1];
			count = tmp[2];
			
			if(visited[curY][curX]) {
				continue;	//이미 방문한 곳이면
			}
			
			max = Math.max(max, count);	//날짜 갱신해주기
			visited[curY][curX] = true;	//방문 했고
			map[curY][curX] = 1;	//익었다고 표시
			
			for(int i = 0; i < 4; i++) {
				nextY = curY + dy[i];
				nextX = curX + dx[i];
				if( !visited[nextY][nextX] &&	//방문하지 않았고
						map[nextY][nextX] == 0) {	//익지 않은 토마토이면
					q.add(new int[] {nextY, nextX, count+1});	//방문하기
				}
			}
		}

		for(int y = 1; y <= yLength; y++) {
			for(int x = 1; x <= xLength; x++) {
				if(map[y][x] == 0) {	//익지 않은 토마토가 있는지 검사
					System.out.println("-1");	
					return;
				}
			}
		}
		System.out.println(max);
	}
}
