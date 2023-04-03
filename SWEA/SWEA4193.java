import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class SWEA4193 {

	//4방위 탐색
	static final int[] dy = {0, 1, 0, -1};
	static final int[] dx = {1, 0, -1, 0};

	public static void main(String[] args) throws Exception{
		// N*N 수영장 [2 ~ 15]
		// 0: 빈칸, 1: 장애물
		// 시작위치 [A, B]
		// 도착위치 [C, D]
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;
		int T = Integer.parseInt(in.readLine());


		for(int test_case = 1; test_case <= T; test_case++) {
			//테스트 케이스 시작
			int length = Integer.parseInt(in.readLine());	//N, 맵의 크기

			int[][] map = new int[length+2][length+2];
			for(int y = 1; y <= length; y++) {
				st = new StringTokenizer(in.readLine());
				for(int x = 1; x <= length; x++) {
					map[y][x] = Integer.parseInt(st.nextToken());	
				}
			}
			st = new StringTokenizer(in.readLine());
			Data start = new Data(Integer.parseInt(st.nextToken()) + 1, Integer.parseInt(st.nextToken()) + 1, 0);
			
			st = new StringTokenizer(in.readLine());
			int finishY, finishX;		//끝점 받기
			finishY = Integer.parseInt(st.nextToken()) + 1;
			finishX = Integer.parseInt(st.nextToken()) + 1;
			//입력 종료
			
			//방문체크
			boolean[][] visited = new boolean[length+2][length+2];
			for(int i = 0, limit=length+1; i <= limit; i++) {
				map[0][i] = 1;
				map[limit][i] = 1;
				map[i][0] = 1;
				map[i][limit] = 1;
				
				visited[0][i] = true;
				visited[limit][i] = true;
				visited[i][0] = true;
				visited[i][limit] = true;
			}
			
			PriorityQueue<Data> pq = new PriorityQueue<>();
			pq.add(start);	//시작지점 넣기
			
			Data curr = null;			//큐에서 꺼낸거 사용하기 용
			int nY, nX;
			int nTime;
			boolean solved = false;		//도착했다는 소리
			int lastTime = 0;
			while( !pq.isEmpty() ) {
				curr = pq.poll();
				
				if(curr.y == finishY && curr.x == finishX) {
					//도착했다
					solved = true;
					lastTime = curr.time;	//이시간에 도착
					break;	//그만 탐색하자
				}
				
				if( visited[curr.y][curr.x] ) {
					//다른 곳에서 이미 방문했으면
					continue;
				}
				
				visited[curr.y][curr.x] = true;	//실제로 도착했을 때만 방문 체크
				
				for(int d = 0; d < 4; d++) {
					nY = curr.y + dy[d];
					nX = curr.x + dx[d];
					
					if(map[nY][nX] == 1) continue;		//벽이면
					if(visited[nY][nX] ) continue;		//이미 방문 했으면
					
					if(map[nY][nX] == 2) {
						//허리케인이면
						nTime = 3 - (curr.time % 3);
						pq.add(new Data(nY, nX, curr.time  + nTime));		//이 시간에 도착하게된다
					} else {
						//그냥 바다이면
						pq.add(new Data(nY, nX, curr.time+1));	//단순히 +1시간에 도착하게된다
					}
				}
			}

			sb.append(String.format("#%d %d\n", test_case, solved ? lastTime : -1));
		}

		System.out.println(sb.toString());
	}

	static class Data implements Comparable<Data>{
		int y;
		int x;
		int time;

		public Data(int y, int x, int time) {
			this.y = y;
			this.x = x;
			this.time = time;
		}
		
		public int compareTo(Data other) {
			return this.time - other.time;
		}
	}
}
