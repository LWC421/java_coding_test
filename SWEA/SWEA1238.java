import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {


	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;

		int numInput;		//입력 개수
		int startVertex;	//시작점
		for(int test_case = 1; test_case <= 10; test_case++) {
			//테스트 케이스 시작

			//정점 개수, 시작 지점 받기
			st = new StringTokenizer(in.readLine());
			numInput = Integer.parseInt(st.nextToken()) / 2;	//입력 개수
			startVertex = Integer.parseInt(st.nextToken());		//시작점

			//1부터 시작하므로 +1로 초기화
			boolean[][] map = new boolean[100+1][100+1];	//맵 정보, 최대 100이므로 100+1

			int max = -1;			//최대값 저장
			int maxTarget = -1;		//최대값일때 타겟

			//연결정보 받기
			int from, to;
			st = new StringTokenizer(in.readLine());
			for(int i = 0; i < numInput; i++) {
				from = Integer.parseInt(st.nextToken());
				to = Integer.parseInt(st.nextToken());
				map[from][to] = true;	//연결정보 저장
			}
			//입력 받기 종료

			//[위치, 카운트]로 이루어짐
			Queue<int[]> q = new ArrayDeque<>();
			q.add(new int[] {startVertex, 0});

			//BFS돌기
			int[] tmp;
			int vertex, count;	//큐에서 빼낸 정보 받기
			boolean[] visited = new boolean[100+1];	//방문 체크용

			while( !q.isEmpty() ) {
				tmp = q.poll();
				vertex = tmp[0];
				count = tmp[1];

				//거쳐온 횟수에 따라 타겟 바꿔주기
				if(count > max) {
					maxTarget = vertex;
					max = count;
				}
				else if(count == max) {
					maxTarget = Math.max(vertex, maxTarget);
				}

				for(int i = 1; i <= 100; i++) {
					if( !visited[i] && map[vertex][i]) {
						//방문안했고 연결되어있으면
						q.add(new int[] {i, count+1});	//큐에 방문 정보 넣기
						visited[i] = true;	//방문 예정으로 넣기
					}
				}
			}


			sb.append(String.format("#%d %d\n", test_case, maxTarget));
		}

		System.out.println(sb.toString());
	}
}
