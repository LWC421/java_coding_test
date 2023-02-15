import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class SWEA1868 {
	static int length;			//맵의 크기를 나타낸다
	static int[][] map;			//맵을 나타낸다, -1 : 지뢰가 없는곳 / 0 : 벽 / 1 : 지뢰 있는 곳
	static boolean[][] visited;	//방문 했는지 표시하기
	static int remainVisited;	//남은 방문해야 하는 방
	static int count;			//몇 번 눌러야 하는지 확인용

	static Queue<int[]> q;			//BFS돌 때 사용
	static PriorityQueue<int[]> pq;	//맵 순회하기 위해 사용
	static int[] current;			//큐에서 꺼낸거 받기
	static int curY, curX;			//현재 위치
	static int nextY, nextX;		//다음 위치

	static String[] tmp;	//입력 받을때 사용

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");

		int T = Integer.parseInt(in.readLine());
		for(int test_case = 1; test_case <= T; test_case++) {
			//-------------입력 받기--------------
			length = Integer.parseInt(in.readLine());
			map = new int[length+2][length+2];
			visited = new boolean[length+2][length+2];
			for(int i = 0; i < length+2; i++) {
				//visited에 벽치기
				visited[i][0] 		 = true;
				visited[i][length+1] = true;
				visited[0][i] 		 = true;
				visited[length+1][i] = true;
			}

			remainVisited = length * length;	//일단 length*length로 초기화
			count = 0;	//카운트 초기화
			q = new ArrayDeque<>();		//큐 초기화
			pq = new PriorityQueue<>((a, b) -> {
				return a[2] - b[2];	//주변 지뢰 갯수 순으로 정렬
			});	//우선순위 큐도 초기화

			//맵 초기화하기
			for(int y = 1; y <= length; y++) {
				st = new StringTokenizer(in.readLine());
				tmp = st.nextToken().split("");
				for(int x = 1; x <= length; x++) {
					if(tmp[x-1].equals(".")) {	
						//지뢰가 없으면
						map[y][x] = -1;
					}
					else {
						//지뢰가 있다
						map[y][x] = 1;	
						visited[y][x] = true;	//지뢰가 있는 곳은 볼 필요 없다
						remainVisited--;		//방문해야 되는 곳의 횟수를 줄인다
					}
				}
			}
			//-------------입력 종료--------------
			for(int y = 1; y <= length; y++) {
				for(int x = 1; x <= length; x++) {
					int numBomb = 0;	//주변에 지뢰 몇개 있는지

					for(int dy = -1; dy <= 1; dy++) {
						for(int dx = -1; dx <= 1; dx++) {
							nextY = y + dy;
							nextX = x + dx;
							if(map[nextY][nextX] == 1) {
								numBomb++;
							}
						}
					}
					pq.add(new int[] {y, x, numBomb});
				}
			}
			while( !pq.isEmpty() ) {
				current = pq.poll();
				int y = current[0];
				int x = current[1];
				
				if(visited[y][x]) continue;	//확인 해볼 필요 없으면 패스
				
				bfs(y, x);
				count++;
				if(remainVisited == 0) {
					break;
				}
			}
			sb.append("#").append(test_case).append(" ").append(count).append("\n");
		}

		System.out.print(sb.toString());
	}

	//지뢰찾기 하기
	public static void bfs(int y, int x) {
		q.add(new int[] {y, x});

		//지뢰찾기 시작
		while( !q.isEmpty() ) {
			current = q.poll();
			curY = current[0];
			curX = current[1];

			if(visited[curY][curX]) {
				//이미 방문 했던 곳일 경우
				continue;
			}
			remainVisited--;	//방문 해야 되는 곳 횟수 줄이기
			visited[curY][curX] = true;

			boolean flag = true;
			Loop: for(int dy = -1; dy <= 1; dy++) {
				for(int dx = -1; dx <= 1; dx++) {
					if(dy == 0 && dx == 0) continue;	//현재 위치인 경우
					nextY = curY + dy;
					nextX = curX + dx;

					if(map[nextY][nextX] == 1) {	//지뢰가 발견 된 경우!
						flag = false;	//얘는 여기서 그만 해야 된다
						break Loop;
					}
				}				
			}
			if(flag) {	//더 탐색해도 되면 -> 8방위에 지뢰가 없으면 이라는 뜻
				for(int dy = -1; dy <= 1; dy++) {
					for(int dx = -1; dx <= 1; dx++) {
						if(dy == 0 && dx == 0) continue;	//현재 위치인 경우
						nextY = curY + dy;
						nextX = curX + dx;

						if( !visited[nextY][nextX]) {//방문 했던 적이 없으면
							q.add(new int[] { nextY, nextX });
						}
					}				
				}
			}
		}
	}
}
