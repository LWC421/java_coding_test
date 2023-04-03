import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class SWEA4192 {
	
	//4방위 탐색
	static final int[] dy = {0, 1, 0, -1};
	static final int[] dx = {1, 0, -1, 0};

	public static void main(String[] args) throws Exception{
		// N*N 수영장
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
			
			boolean[][] map = new boolean[length+2][length+2];
			for(int y = 1; y <= length; y++) {
				st = new StringTokenizer(in.readLine());
				for(int x = 1; x <= length; x++) {
					map[y][x] = Integer.parseInt(st.nextToken()) == 0 ? true : false;	//갈 수 있으면 true로 표기
				}
			}
			st = new StringTokenizer(in.readLine());
			Coord start = new Coord(Integer.parseInt(st.nextToken()) + 1, Integer.parseInt(st.nextToken()) + 1);
			st = new StringTokenizer(in.readLine());
			Coord end = new Coord(Integer.parseInt(st.nextToken()) + 1, Integer.parseInt(st.nextToken()) + 1);
			//입력 종료
			
			boolean[][] visited = new boolean[length+2][length+2];
			Queue<Coord> q = new ArrayDeque<>();
			q.add(start);	//시작지점 넣기
			visited[start.y][start.x] = true;	//시작지점 표기
			
			Coord curr = null;		//큐에서 빼낸거 사용
			boolean solved = false;	//도착여부
			int time = 0;			//시간 체크
			int nY, nX;
			Loop: while( !q.isEmpty() ) {
				int size = q.size();		//size개수만큼만 빼자
				for(int s = 0; s < size; s++) {
					curr = q.poll();
					if(curr.y == end.y && curr.x == end.x) {
						//도착했다!
						solved = true;
						break Loop;
					}
					for(int d = 0; d < 4; d++) {
						nY = curr.y + dy[d];
						nX = curr.x + dx[d];
						if( !map[nY][nX] ) continue;	//연결이 안 되어 있으면
						if(visited[nY][nX]) continue;	//이미 방문을 했으면
						visited[nY][nX] = true;			//방문 예정으로 넣기
						q.add(new Coord(nY, nX));		//다음 지점 넣기
					}
				}
				time++;
			}
			
			
			sb.append(String.format("#%d %d\n", test_case, solved ? time : -1));
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
	}
}
