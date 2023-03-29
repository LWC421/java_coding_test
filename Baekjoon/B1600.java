import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class B1600 {

	//4방위 탐색
	final static int[] dy = new int[] {0, 1, 0, -1};
	final static int[] dx = new int[] {1, 0, -1, 0};

	//말처럼 이동
	final static int[] horseDY = new int[] {1, 2, 2, 1, -1, -2, -2, -1};
	final static int[] horseDX = new int[] {2, 1, -1, -2, -2, -1, 1, 2};

	public static void main(String[] args) throws Exception{
		// K번만 말처럼 움직일 수 있다
		// 그 외에는 그냥 인접한 칸으로 이동(대각선X)
		// 좌상단에서 시작
		// 우하단으로 가야됨
		// 말처럼 또는 원숭이처럼 둘 다 한 번 움직이는 것
		// 0 평치, 1 장애물
		// 맵 크기는 200 * 200 이하이다

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int numHorse = Integer.parseInt(in.readLine());	//K, 말처럼 이동 가능한 횟수

		StringTokenizer st = new StringTokenizer(in.readLine());
		int xLength = Integer.parseInt(st.nextToken());
		int yLength = Integer.parseInt(st.nextToken());


		//해당 위치에서 말처럼 이동 가능한 횟수의 값들을 저장해두기
		int[][] remainMap = new int[yLength+2][xLength+2];
		
		int[][] map = new int[yLength+2][xLength+2];
		for(int y = 1; y <= yLength; y++) {
			st = new StringTokenizer(in.readLine());
			for(int x = 1; x <= xLength; x++) {
				map[y][x] = Integer.parseInt(st.nextToken());
				remainMap[y][x] = -1;	//일단 -1로 초기화 해야한다
			}
		}
		//입력 종료

		//맵에 벽치기
		for(int y = 0; y <= yLength; y++) {
			map[y][0] = 1;
			map[y][xLength+1] = 1;
		}
		for(int x = 0; x <= xLength; x++) {
			map[0][x] = 1;
			map[yLength+1][x] = 1;
		}
		//벽치기 종료

		Queue<Data> q = new ArrayDeque<>();
		q.add(new Data(1, 1, numHorse));	//처음 위치 넣기

		int size;
		int count = 0;
		Data cur = null;
		int nY, nX;

		while( !q.isEmpty() ) {
			size = q.size();
			for(int s = 0; s < size; s++) {
				//들어 있는 갯수만큼만 꺼내기 -> 동일 depth가 된다
				cur = q.poll();
				if(cur.y == yLength && cur.x == xLength) {
					//우하단에 도착한 경우 -> 출력하고 종료
					System.out.println(count);
					return;
				}
				
				if(cur.remainHorse <= remainMap[cur.y][cur.x]) {
					//현재 말 이동 횟수가 현재 위치의 말 횟수와 작거나 같은경우
					continue;
				}

				remainMap[cur.y][cur.x] = cur.remainHorse;

				for(int d = 0; d < 4; d++) {
					//4방위 탐색하기
					nY = cur.y + dy[d];
					nX = cur.x + dx[d];

					if(map[nY][nX] == 1) {
						//벽이면 못 간다
						continue;
					}
					if(cur.remainHorse <= remainMap[nY][nX]) {
						//말처럼 이동횟수가 더 적거나 같으면 굳이 갈 필요 없다
						continue;
					}
					q.add(new Data(nY, nX, cur.remainHorse));
				}

				//말처럼 이동 가능하면
				if(cur.remainHorse > 0) {
					//말처럼 이동하기
					for(int d = 0; d < 8; d++) {
						nY = cur.y + horseDY[d];
						nX = cur.x + horseDX[d];

						//맵 밖을 벗어나는 경우 체크
						if(nY <= 0 || nY > yLength) {
							continue;
						}
						if(nX <= 0 || nX > xLength) {
							continue;
						}

						if(map[nY][nX] == 1) {
							//벽이면 못 간다
							continue;
						}

						if(cur.remainHorse <= remainMap[nY][nX]) {
							//말처럼 이동횟수가 더 적거나 같으면 굳이 갈 필요 없다
							continue;
						}

						q.add(new Data(nY, nX, cur.remainHorse - 1));
					}
				}
			}
			count++;	//이동 횟수(depth) 늘리기
		}

		System.out.println(-1);
	}


	static class Data{
		int y;
		int x;
		int remainHorse;	//남은 말 이동
		public Data(int y, int x, int remainHorse) {
			super();
			this.y = y;
			this.x = x;
			this.remainHorse = remainHorse;
		}
	}

}
