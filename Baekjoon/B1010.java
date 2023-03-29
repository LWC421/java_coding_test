import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B1010 {
	
	public static int[][] dp;
	
	public static void main(String[] args) throws Exception{
		// 서쪽 N개의 사이트
		// 동쪽 M개의 사이트(N 이상이다) -> 조합아닌가?
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(in.readLine());
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;
		dp = new int[30+1][30+1];
		makeComb();
		
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(in.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			sb.append(dp[M][N]).append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	public static void makeComb() {
		//조합의 경우의 수 구하기
		for(int y = 0; y <= 30; y++) {
			for(int x = 0; x <= y; x++) {
				if(x == 0 || x == y) {
					dp[y][x] = 1;
				} else {
					dp[y][x] = dp[y-1][x-1] + dp[y-1][x];
				}
			}
		}
	}
}
