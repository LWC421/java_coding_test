import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		int T = Integer.parseInt(in.readLine());
		StringTokenizer st = null;
		
		for(int test_case = 1; test_case <= T; test_case++) {
			int length = Integer.parseInt(in.readLine());	//N, 수열의 길이
			st = new StringTokenizer(in.readLine());
			int[] inputs = new int[length];		//입력값들
			for(int i = 0; i < length; i++) {
				inputs[i] = Integer.parseInt(st.nextToken());
			}
			
			int[] dp = new int[length];
			dp[0] = inputs[0];
			int index = 0;	//0번부터 시작한다
			for(int i = 1; i < length; i++) {
				if(dp[index] < inputs[i]) {
					//증가가 가능한 경우
					index++;
					dp[index] = inputs[i];
				} else {
					//증가가 불가능한 경우
					int lower = lowerBound(dp, index, inputs[i]);
					dp[lower] = inputs[i];
				}
			}
			
			sb.append(String.format("#%d %d\n", test_case, index + 1));
		}
		
		System.out.println(sb.toString());
	}
	
	public static int lowerBound(int[] dp, int end, int targetNum) {
		int start = 0;
		
		while(start < end) {
			int mid = (start + end) / 2;
			if(dp[mid] >= targetNum) {
				end = mid;
			} else {
				start = mid + 1;
			}
		}
		
		return end;
	}
}
