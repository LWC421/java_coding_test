import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class B17070 {

	static int length;
	static int[][] map;
	static int[][][] dp;

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;

		length = Integer.parseInt(in.readLine());	//N, 집의 크기 [3 ~ 16]
		map = new int[length][length];

		for(int y = 0; y < length; y++) {
			st = new StringTokenizer(in.readLine());
			for(int x = 0; x < length; x++) {
				map[y][x] = Integer.parseInt(st.nextToken());
			}
		}
		//입력 종료
		dp = new int[length][length][3]; //0이면 가로, 1 대각, 2 세로

		for(int x = 1; x < length; x++) {
			if(map[0][x] == 1) {
				break;
			}
			dp[0][x][0] = 1;	//처음
		}


		for(int y = 1; y < length; y++) {
			for(int x = 2; x < length; x++) {
				//현재 위치를 가로로 올려면
				// 1. 그전 위치를 가로 또는 대각선으로 온 경우
				// 2. 현재 칸 및 왼쪽이 1(벽)이 아닌 경우
				if(map[y][x] == 0 && map[y][x-1] == 0) {
					dp[y][x][0] += dp[y][x-1][0];
					dp[y][x][0] += dp[y][x-1][1];
				}

				//현재 위치를 대각선으로 올려면
				// 1. 그전 위치가 가로, 대각, 세로 전부 가능
				// 2. 현재칸 및 왼쪽 위가 1(벽)이 아닌 경우
				if(map[y][x] == 0 && map[y][x-1] == 0 && map[y-1][x] == 0) {
					dp[y][x][1] += dp[y-1][x-1][0];
					dp[y][x][1] += dp[y-1][x-1][1];
					dp[y][x][1] += dp[y-1][x-1][2];
				}

				//현재 위치를 세로로 올려면
				// 1. 그전 위치를 대각, 세로로 왔을 경우
				// 2. 현재 칸 및 위쪽이 1(벽)이 아닌 경우
				if(map[y][x] == 0 && map[y-1][x] == 0) {
					dp[y][x][2] += dp[y-1][x][1];
					dp[y][x][2] += dp[y-1][x][2];
				}
			}
		}

		int finish = length-1;
		
		int result = dp[finish][finish][0] + dp[finish][finish][1] + dp[finish][finish][2];

		System.out.println(result);
	}
}
