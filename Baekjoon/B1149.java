import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B1149 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(in.readLine());
		int[][] costs = new int[N][3];
		StringTokenizer st = null;
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(in.readLine());
			
			for(int j = 0; j < 3; j++) {
				costs[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		//입력 종료
		
		//1부터 시작한다
		for(int i = 1; i < N; i++) {
			costs[i][0] += Math.min(costs[i-1][1], costs[i-1][2]);
			costs[i][1] += Math.min(costs[i-1][0], costs[i-1][2]);
			costs[i][2] += Math.min(costs[i-1][0], costs[i-1][1]);
		}
		System.out.println(Math.min(Math.min(costs[N-1][0], costs[N-1][1]), costs[N-1][2]));
	}
}
