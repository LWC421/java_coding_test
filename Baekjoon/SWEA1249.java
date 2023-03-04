import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class SWEA1249 {

	public static void main(String[] args) throws Exception{
		// 출발지 S에서 도착지 G로 빠른 시간에 가야한다
		// 도로가 파여진 깊이에 비례해 복구 시간 증가
		// 출발지 -> 도착지 일때, 복구 시간이 가장 짧은 경로에 대해 총 복구 시간 구하기
		// 깊이 1 -> 복구 시간 1이라고 한다
		// 거리는 신경 안 써도 된다 
		// 좌상단 -> 우하단으로 가야한다
		
//		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		StringBuilder sb = new StringBuilder("");
		
		int T = Integer.parseInt(in.readLine());
		int length;	    //맵의 크기
		int[][] map;	//맵 정보
		int[][] costMap;//비용에 대한 맵 
		
		Queue<int[]> q = new ArrayDeque<>();;	//BFS를 위한 큐 생성
		
		//우, 하, 좌, 상 순으로 돌기
		int[] dy = {0, 1, 0, -1};
		int[] dx = {1, 0, -1, 0};
		
		for(int test_case = 1; test_case <= T; test_case++) {
			//테스트 케이스 시작
			length = Integer.parseInt(in.readLine());
			map = new int[length+2][length+2];		//위아래, 좌우는 -1로 막기
			costMap = new int[length+2][length+2];	//비용맵도 생성
			
			String str;
			for(int y = 1; y <= length; y++) {
				str = in.readLine();
				for(int x = 1; x <= length; x++) {
					map[y][x] = Integer.parseInt(String.valueOf(str.charAt(x-1)));
					costMap[y][x] = Integer.MAX_VALUE;	//최대값으로 만들어놓기
				}
			}
			//입력 종료
			
			//맵의 위아래, 좌우 벽치기
			for(int i = 0; i <= length+1; i++) {
				map[i][0]		 = -1;
				map[i][length+1] = -1;
				map[0][i] 		 = -1;
				map[length+1][i] = -1;
			}
			//맵 초기화 완료
			q.clear();	//큐 초기화
			
			//BFS시작, [y, x, cost]로 넣기
			costMap[1][1] = 0;	//시작지점의 cost 지정
			q.add(new int[] {1, 1, 0});
			int[] tmp;
			int y, x;
			int cost;	//큐에서 꺼낸거 사용하기 위해
			int nY, nX;	//다음 위치
			int nCost;	//다음 cost
			
			while( !q.isEmpty() ) {
				tmp = q.poll();	//큐에서 빼내서
				y = tmp[0];
				x = tmp[1];
				cost = tmp[2];
				
				for(int d = 0; d < 4; d++) {
					//4방위 탐색하기
					nY = y + dy[d];
					nX = x + dx[d];
					if(map[nY][nX] == -1) continue;	//벽이면 안감
					nCost = cost + map[nY][nX];
					if(nCost < costMap[nY][nX]) {
						//만약 여기서 가는게 더 이득일 경우
						q.add(new int[] {nY, nX, nCost});	//넣기
						costMap[nY][nX] = nCost;
					}
				}
			}
			
			sb.append(String.format("#%d %d\n", test_case, costMap[length][length]));
		}
		System.out.println(sb.toString());
	}
}
