import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class B1261 {
	static int yLength;
	static int xLength;
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		st = new StringTokenizer(in.readLine());
		xLength = Integer.parseInt(st.nextToken());
		yLength = Integer.parseInt(st.nextToken());
		
		//맵 정보
		int[][] map = new int[yLength+2][xLength+2];
		//방문 정보
		//해당 위치까지 몇  개의 벽을 뚫어야 하는지 저장
		int[][] count = new int[yLength+2][xLength+2];
		
		String s = null;
		for(int y = 1; y <= yLength; y++) {
			s = in.readLine();
			for(int x = 1; x <= xLength; x++) {
				map[y][x] = Integer.parseInt(String.valueOf(s.charAt(x-1)));
				count[y][x] = Integer.MAX_VALUE;	//카운트들을 일단 -1로 저장
			}
		}
		
		//못가는 곳으로 표기
		for(int y = 0; y <= yLength+1; y++) {
			map[y][0] = -1;	
			map[y][xLength+1] = -1;
		}
		for(int x = 0; x <= xLength+1; x++) {
			map[0][x] = -1;	
			map[yLength+1][x] = -1;
		}
		
		//큐를 사용, [y, x, 뚫은 벽]
		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[] {1, 1, 0});
		
		//큐 운용을 위한 값들 선언
		int[] tmp;
		int curY;
		int curX;
		int curCount;
		
		int nextY;
		int nextX;
		
		int[] dy = {-1, 0, 0, 1};
		int[] dx = {0, -1, 1, 0};
		
		//BFS하기
		while( !q.isEmpty() ) {
			tmp = q.poll();
			curY 	 = tmp[0];
			curX 	 = tmp[1];
			curCount = tmp[2];
			
			//기준 카운트보다 현재 왔던 길의 카운트가 크거나 같으면 -> 굳이 갈 필요 없다
			if( count[curY][curX] <= curCount ) {
				continue;
			}
			
			count[curY][curX] = curCount;	//현재 장소의 카운트 갱신
			for(int i = 0; i < 4; i++) {
				nextY = curY + dy[i];
				nextX = curX + dx[i];
				
				if(map[nextY][nextX] == -1) {
					//못 가는 경우
					continue;
				}
				else if(map[nextY][nextX] == 0) {
					//뚫려 있는 곳일 경우
					q.add(new int[] { nextY, nextX, curCount });
				}
				else {
					//벽이 있는 경우 -> 뚫고 가야한다
					q.add(new int[] { nextY, nextX, curCount+1 });
				}
			}
		}
		
		System.out.println(count[yLength][xLength]);
	}
}
