import java.util.Scanner;

public class SWEA1954 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();

		for(int test_case = 1; test_case <= T; test_case++) {
			int N = sc.nextInt();

			int[][] map = new int[N][N];	//N * N 맵 생성

			//움직이는 거 정의
			int[] dy = { 0, 1, 0, -1 };
			int[] dx = { 1, 0, -1, 0 };
			int curD = 0;	//현재 어떻게 이동할건지
			int cur = 1;

			//현재 위치
			int curY = 0;
			int curX = 0;

			int nextY = 0;
			int nextX = 0;

			while(true) {
				map[curY][curX] = cur++;	//현재 위치의 값 넣기

				//다음 위치 보기
				nextY = curY + dy[curD];
				nextX = curX + dx[curD];

				//방향 바꿔야 되는지 확인 해보기
				if(nextY == N) {
					curD = (curD + 1) % 4;				//Y가 맨밑에 도착한 경우
				}
				else if(nextX == N || nextX == -1) {	//X가 좌우로 벗어나는 경우
					curD = (curD + 1) % 4;
				}
				else if(map[nextY][nextX] > 0) {		//숫자가 이미 있는 경우에도
					curD = (curD + 1) % 4;
				}

				//다음 위치로 변경
				curY += dy[curD];
				curX += dx[curD];


				if(cur == N*N + 1) {
					break;	//다채웠다 -> 탈출조건
				}
			}

			StringBuilder sb = new StringBuilder("");
			for(int[] row: map) {
				for(int c: row) {
					sb.append(c).append(" ");
				}
				sb.append("\n");
			}

			System.out.printf("#%d\n", test_case);
			System.out.print(sb);
		}

	}
}
