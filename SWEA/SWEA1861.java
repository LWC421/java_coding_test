import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class SWEA1861 {
	
	static int N;						//방의 크기
	static int[][] map;					//숫자가 적힌 방들, [N+2][N+2]크기로 초기화 한다
	static int number;					//숫자 입력 받을 때 사용 할 것
	static boolean[][] visited; 		//방문했는지 확인 용
	static PriorityQueue<int[]> q;		//어디부터 확인해볼건지 저장 [y, x, number]로 들어간다
	static int[] tmp;					//큐에서 꺼낼 때 임시로 저장할 곳
	
	static int y, x;			//현재 위치
	static int nextY, nextX;	//다음 위치
	static int count;			//몇 개나 방을 갔는지
	
	static int[] dy = new int[] {-1, 0, 0, 1};		//상, 좌, 우, 하 방문용
	static int[] dx = new int[] {0, -1, 1, 0};		//상, 좌, 우, 하 방문용
	
	static int max;			//현재 최장 방의 길이
	static int startNumber;	//시작 방
	
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");
		
		int T = Integer.parseInt(in.readLine());
		
		for(int test_case = 1; test_case <= T; test_case++) {
			q = new PriorityQueue<>((a, b) ->  {
				return a[2] - b[2];	//입력 받은 숫자를 기준으로 정렬하기
			});
			max = 0;
			startNumber = Integer.MAX_VALUE;
			
			//입력 받기
			N = Integer.parseInt(in.readLine());
			map = new int[N+2][N+2];
			for(int y = 1; y <= N; y++) {
				st = new StringTokenizer(in.readLine(), " ");
				for(int x = 1; x <= N; x++) {
					number = Integer.parseInt(st.nextToken());
					map[y][x] = number;
					q.add(new int[] {y, x, number});	//큐에 넣기
				}
			}
			visited = new boolean[N+2][N+2];
			//입력 받기 종료
			
			//확인 해볼 것들이 남아있으면 -> 숫자가 작은 곳부터 확인해보기
			while( !q.isEmpty() ) {
				//꺼내서
				tmp = q.poll();
				y = tmp[0];
				x = tmp[1];
				number = tmp[2];
				
				if(visited[y][x]) {	//이미 갔던 곳이면
					continue;	//굳이 검색 안한다
				}
				count = 1;	//현재 시작
				
				Loop: while(true) {
					visited[y][x] = true;	//방문했다고 체크
					
					//4방위 탐색
					for(int i = 0; i < 4; i++) {
						nextY = y + dy[i];
						nextX = x + dx[i];
						if( !visited[nextY][nextX] && 	//방문 했던 곳이 아니면서
							(map[nextY][nextX] - map[y][x]) == 1) {	//다음 갈 곳이 1만큼 크면은
							y = nextY;	//위치 이동
							x = nextX;
							count++;	//카운트 증가
							continue Loop;	//현재 4방위 탐색 종료 -> 다음 장소로 이동
						}
					}
					if(count > max) {	//새로운 최장 길이의 방을 찾은 경우다
						max = count;
						startNumber = number;
					}
					
					break;	//4방위 탐색을 하고 break가 안 되었을 경우다 -> 갈 곳이 없다는 뜻이다
				}
				
			}
			
			sb.append("#").append(test_case).append(" ").append(startNumber).append(" ").append(max).append("\n");
			
		}
		System.out.println(sb.toString());
	}
}
