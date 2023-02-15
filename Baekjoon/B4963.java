import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class B4963 {
	static int w;				//지도의 너비
	static int h;				//지도의 높이
	
	static boolean[][] map;		//지도, false -> 바다(벽포함), true -> 땅
	static boolean[][] visited;	//방문체크, fasle -> 미방문, true -> 방문
	
	static boolean target;		//입력 받을때 사용 할 거
	static int remainVisited;	//남은 방문 횟수
	static Queue<int[]> q;		//BFS를 위한 큐
	static int[] tmp;			//큐에서 꺼낼 때 사용할 거
	
	static int curY;	//현재 위치
	static int curX;	//현재 위치
	static int count;	//방문 횟수 표시
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");
		
		while(true) {
			st = new StringTokenizer(in.readLine(), " ");
			w = Integer.parseInt(st.nextToken());
			h = Integer.parseInt(st.nextToken());
			q = new ArrayDeque<>();	//큐 초기화
			count = 0;				//카운트 초기화
			
			//입력의 종료 
			if(w == 0) {
				break;
			}
			
			//맵 초기화
			map = new boolean[h+2][w+2];
			visited = new boolean[h+2][w+2];
			remainVisited = h*w;	//일단 h*w만큼 방문체크 해봐야 한다고 표시
			for(int y = 1; y <= h; y++) {
				st = new StringTokenizer(in.readLine());
				//true / false로 초기화하기
				for(int x = 1; x <= w; x++) {
					target = st.nextToken().equals("1") ? true : false;
					map[y][x] = target;
					visited[y][x] = !target;			//바다일 경우 방문했다고 표시해버리기
					remainVisited -= target ? 0 : 1;	//바다일 경우 방문해야 되는거에서 1빼기
				}
			}
				
			//확인 시작해보기
			Loop: for(int y = 1; y <= h; y++) {
				for(int x = 1; x <= w; x++) {
					if(visited[y][x]) {	//이미 방문했엇으면
						continue;
					}
					bfs(y, x);	//방문한적 없으므로 체크하기
					count++;
					
					if(remainVisited == 0) {	//다 체크했으면
						break Loop;	//순회 종료
					}
				}
			}
			sb.append(count).append("\n");
		}
		System.out.println(sb.toString());
	}
	
	public static void bfs(int curY, int curX) {
		q.add(new int[] { curY, curX });	//일단 현재위치 넣기
		
		while( !q.isEmpty() ) {
			tmp = q.poll();
			curY = tmp[0];
			curX = tmp[1];
			
			if(visited[curY][curX]) {	//이미 방문 된 곳이면 넘기기
				continue;
			}
			else{ //방문 한적이 없으면
				remainVisited--;	//남은 방문 횟수 줄이기
				visited[curY][curX] = true;	//방문했다고 표시
			}
			
			
			//8방위 탐색 시작
			for(int y = -1; y <= 1; y++) {
				for(int x = -1; x <= 1; x++) {
					if(x == 0 && y == 0) continue;	//자기 자신 위치이면 안가기
					if( !visited[curY + y][curX + x] &&	//방문 한 적이 없으면서
							map[curY + y][curX + x]) {	//방문이 가능하면(땅이면)
						q.add(new int[] {curY+y, curX+x});	//해당 장소 방문하기
					}
				}
			}
		}
	}
}
