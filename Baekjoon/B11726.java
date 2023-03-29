import java.io.BufferedReader;
import java.io.InputStreamReader;

public class B11726 {
	public static void main(String[] args) throws Exception{
		int N = Integer.parseInt( new BufferedReader(new InputStreamReader(System.in)).readLine() );
	
		int[] dp = new int[N+2];
		
		dp[1] = 1;
		dp[2] = 2;
		
		for(int i = 3; i <= N; i++) {
			dp[i] = (dp[i-1] + dp[i-2]) % 10_007;
		}
		
		System.out.println(dp[N]);
	}
}
