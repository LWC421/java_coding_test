import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class SWEA1249_dijkstra {

	public static void main(String[] args) throws Exception{
		// 출발지 S에서 도착지 G로 빠른 시간에 가야한다
		// 도로가 파여진 깊이에 비례해 복구 시간 증가
		// 출발지 -> 도착지 일때, 복구 시간이 가장 짧은 경로에 대해 총 복구 시간 구하기
		// 깊이 1 -> 복구 시간 1이라고 한다
		// 거리는 신경 안 써도 된다 
		// 좌상단 -> 우하단으로 가야한다
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		StringBuilder sb = new StringBuilder("");
		
		int T = Integer.parseInt(in.readLine());
		int length;	    //맵의 크기
		int[][] map;	//맵 정보
		int[][] costMap;//비용에 대한 맵 
		
		//우, 하, 좌, 상 순으로 돌기
		int[] dy = {0, 1, 0, -1};
		int[] dx = {1, 0, -1, 0};
		
		//다익스트라용 pq
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b)->{
			return (a[2]-b[2]);	//cost순으로 정렬
		});
		
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
			
			pq.clear();
			pq.add(new int[] {1, 1, 0});
			
			int[] tmp;
			int y, x, cost;		//큐에서 빼낸거 확인요
			int nY, nX, nCost;	
			while( !pq.isEmpty() ) {
				tmp = pq.poll();
				y 	 = tmp[0];	//큐에서 빼내고
				x 	 = tmp[1];
				cost = tmp[2];
				if(cost >= costMap[y][x]) continue;	//만약 비용이 더 큰 경우
				costMap[y][x] = cost;
				
				for(int d = 0; d < 4; d++) {
					nY = y + dy[d];
					nX = x + dx[d];
					
					if(map[nY][nX] == -1) continue;	//벽일 경우
					nCost = cost + map[nY][nX];	//다음코스트에 대해 계산
					if(nCost < costMap[nY][nX]) pq.add(new int[] {nY, nX, nCost});
				}
			}
			
			
			sb.append(String.format("#%d %d\n", test_case, costMap[length][length]));
		}
		System.out.println(sb.toString());
	}
}
