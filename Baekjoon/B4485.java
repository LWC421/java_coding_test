import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class B4485 {
	
	final static int MAX_VALUE = 125*125*9;
	final static int[] dy = {0, 1, 0, -1};
	final static int[] dx = {1, 0, -1, 0};

	public static void main(String[] args) throws Exception{
		// N*N 크기의 동굴
		// 좌상단에서 시작
		// 우하단으로 이동해야 한다
		// 도둑루피를 만나면 도둑루피의 크기만큼 소지금을 잃게 된다
		// 잃는 금액을 최소로 하자
		// 4방위로만 이동한다
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		
		int test_case = 1;
		
		String inputLine = null;
		while( !(inputLine = in.readLine()).equals("0")) {
			//테스트 케이스 시작
			int length = Integer.parseInt(inputLine);	//N, 맵의 크기
			
			int[][] map = new int[length+2][length+2];		//+2로 맵을 만들자
			int[][] costMap = new int[length+2][length+2];	//비용 맵
			StringTokenizer st = null;
			for(int y = 1; y <= length; y++) {
				st = new StringTokenizer(in.readLine());
				for(int x = 1; x <= length; x++) {
					map[y][x] = Integer.parseInt(st.nextToken());
					costMap[y][x] = MAX_VALUE;	//최대값으로 초기화하자
				}
			}
			//하나의 입력 종료

			//벽으로 표시
			for(int i = 0, limit=length+1; i <= limit; i++) {
				map[i][0] = -1;
				map[i][limit] = -1;
				map[0][i] = -1;
				map[limit][i] = -1;
			}
			//벽치기 종료
			
			Queue<Data> q = new ArrayDeque<>();
			q.add(new Data(1, 1, map[1][1]));	//처음 위치 넣기
			costMap[1][1] = map[1][1];	//처음 위치 비용 다시 넣기
			Data cur = null;
			int nY, nX;	//다음 위치 저장용
			int nCost;	//다음 위치 갔을때 비용
			while( !q.isEmpty() ) {
				cur = q.poll();
				
				for(int d = 0; d < 4; d++) {
					nY = cur.y + dy[d];
					nX = cur.x + dx[d];
					
					if(map[nY][nX] == -1){
						//벽일 경우
						continue;
					}
					nCost = cur.cost + map[nY][nX];		//다음 위치로 갈때의 비용 저장
					if(nCost >= costMap[nY][nX]) {
						//가볼 필요가 없을 경우 안간다
						continue;
					}
					
					costMap[nY][nX] = nCost;		//비용맵 갱신
					q.add(new Data(nY, nX, nCost));	//큐에 넣기
				}
			}

			sb.append(String.format("Problem %d: %d\n", test_case, costMap[length][length]));
			
			test_case++;
		}
		
		System.out.println(sb.toString());
	}

	static class Data{
		int y;
		int x;
		int cost;
		public Data(int y, int x, int cost) {
			super();
			this.y = y;
			this.x = x;
			this.cost = cost;
		}
		@Override
		public String toString() {
			return String.format("[%d, %d] : %d", y, x, cost);
		}
	}
}
