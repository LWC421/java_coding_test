import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class SWEA8382 {

	//4방위 탐색용
	final static int[] dy = {-1, 1, 0, 0};
	final static int[] dx = {0, 0, -1, 1};
	
	public static void main(String[] args) throws Exception{
		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;
		Set<Data> used = new HashSet<>();
		Queue<Data> q = new ArrayDeque<>();
		
		int T = Integer.parseInt(in.readLine());
		for(int test_case = 1; test_case <= T; test_case++) {
			//테스트 케이스 시작
			st = new StringTokenizer(in.readLine());
			
			int startX = Integer.parseInt(st.nextToken());
			int startY = Integer.parseInt(st.nextToken());
			int finishX = Integer.parseInt(st.nextToken());
			int finishY = Integer.parseInt(st.nextToken());
			//입력 종료
			
			used.clear();
			q.clear();
			
			q.add(new Data(startY, startX, 0));	//처음장소 넣기
			q.add(new Data(startY, startX, 1));	//처음장소 넣기
			used.add(new Data(startY, startX, 0));
			used.add(new Data(startY, startX, 1));
			
			int moveCount = 0;
			
			Data curr = null;
			int nY, nX;
			Loop: while( !q.isEmpty() ) {
				int size = q.size();
				
				for(int i = 0; i < size; i++) {
					curr = q.poll();	//큐에서 받아서
					if(curr.y == finishY && curr.x == finishX) {
						//도착 했으면
						break Loop;
					}
					
					if(curr.dir == 1) {
						//그전에 가로로 왔었으면
						for(int d = 0; d < 2; d++) {
							//세로 이동 -> dir가 0
							nY = curr.y + dy[d];
							nX = curr.x + dx[d];
							if( !used.contains(new Data(nY, nX, 0)) ){
								//이렇게 온적이 없을 때만 간다
								q.add(new Data(nY, nX, 0));
								used.add(new Data(nY, nX, 0));
							}
						}
					}

					else {
						//그전에 세로로 왔었으면
						for(int d = 2; d < 4; d++) {
							//가로 이동 -> dir가 1
							nY = curr.y + dy[d];
							nX = curr.x + dx[d];
							if( !used.contains(new Data(nY, nX, 1)) ){
								//이렇게 온적이 없을 때만 간다
								q.add(new Data(nY, nX, 1));
								used.add(new Data(nY, nX, 1));
							}
						}
					}
				}
				moveCount++;
			}
			
			
			sb.append(String.format("#%d %d\n", test_case, moveCount));
		}
		
		System.out.println(sb.toString());
	}
	
	static class Data{
		int y;
		int x;
		int dir;
		
		public Data(int y, int x, int dir) {
			this.y = y;
			this.x = x;
			this.dir = dir;
		}

		@Override
		public int hashCode() {
			return this.y*10000 + this.x*10 + this.dir;
		}

		@Override
		public boolean equals(Object obj) {
			Data other = (Data)obj;
			return this.y == other.y && this.x == other.x && this.dir == other.dir;
		}
	}
}
