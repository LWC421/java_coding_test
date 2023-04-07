import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SWEA6782 {

	final static int LIMIT = 1_000_000 + 1;
	final static long[] pows = new long[LIMIT+1];
	final static long[] base = new long[LIMIT+1];
	
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		int T = Integer.parseInt(in.readLine());
		
		//제곱수들 다 넣기
		//베이스값 넣기
		for(int i = 2, lim = LIMIT+1; i < lim; i++) {
			pows[i] = (long)Math.pow(i, 2);
			base[i] = (long)i;
		}
		
		for(int test_case = 1; test_case <= T; test_case++) {
			//테스트 케이스 시작
			long N = Long.parseLong(in.readLine());
			
			long result = 0;
			if(N == 2) {
				//입력이 2면 그냥 바로 1
				result = 0;
			}
			else {
				while(N != 2) {
					int ceilSQRT = findCeilSQRT(N);
					result += (pows[ceilSQRT] - N);		//자신보다 큰 제곱수와의 값 차이를 더하기
					N = ceilSQRT;	//자신보다 큰 제곱수의 루트로 변경
					result++;		//제곱근으로 변경하는 횟수 +1
				}
			}

			
			sb.append(String.format("#%d %d\n", test_case, result));
		}
		
		System.out.println(sb.toString());
	}
	
	//자신보다 큰 제곱수의 루트를 반환
	public static int findCeilSQRT(long target) {
		int left = 2;
		int right = LIMIT;
		
		int mid = -1;
		while(left < right) {
			mid = left + ((right - left) / 2);
			if(pows[mid] < target) {
				left = mid+1;
			}
			else {
				right = mid;
			}
		}
		
		return right;
	}
}
