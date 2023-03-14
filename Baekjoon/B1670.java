import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class B1670 {

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(in.readLine());
		
		long[] dp = new long[N+1];
		dp[0] = 1;	//얘는 임시로 필요함
		dp[2] = 1;
		
		//N이 6이라고 하면 이를 2를 빼면 4가 된다
		// 이 4를 두개의 짝수로 나누는 방법은 ?
		// 0+4
		// 2+2
		// 4+0이다
		
		if(N <= 2) {
			//2보다 작으면 그냥 출력하자
			System.out.println(dp[N]);
			return;
		}
		
		int tmp;		//임시
		int left, right;//잘랐을때 왼쪽 오른쪽 저장
		long sum;		//해당 N에서의 가짓수 저장
		for(int i = 4; i <= N; i += 2) {
			sum = 0;	//값 초기화
			tmp = i-2;	//2뺀값을 넣는다
			for(left = 0; left <= tmp; left+=2) {
				right = tmp - left;	//right는 left를 제외한 부분이다
				sum += dp[left]*dp[right] % 987654321;	//나누고 왼쪽 오른쪽 부분 곱한게 가짓수의 일부이다
			}
			dp[i] = sum % 987654321;	//나눈 가짓수를 저장하자
		}
		System.out.println(dp[N]);
	}
}
