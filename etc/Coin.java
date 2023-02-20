import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Coin {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(in.readLine());
		StringBuilder sb = new StringBuilder("");
		
		for(int test_case = 1; test_case <= T; test_case++) {
			int length = Integer.parseInt(in.readLine());
			int[] numbers = new int[length];
			
			StringTokenizer st = new StringTokenizer(in.readLine());
			for(int i = 0; i < length; i++) {
				numbers[i] = Integer.parseInt(st.nextToken());
			}
			
			int left = 0;
			int right = length;
			int mid = (left + right) / 2;
			
			//가운데 자른거 기준 왼쪽, 오른쪽 합 구하기
			int leftSum = 0;
			int rightSum = 0;
			while(right - left != 1) {	//left, right차이가 1이면 현재 범위가 1개라는 뜻이다
				leftSum = rightSum = 0;	//값 초기화
				
				//왼쪽, 오른쪽부분 값 더해보기
				for(int l = left; l < mid; l++) {
					leftSum += numbers[l];
				}
				for(int r = mid; r < right; r++) {
					rightSum += numbers[r];
				}
				
				if(leftSum < rightSum) {
					//왼쪽에 가짜 동전이 있는 경우
					right = mid;
				}
				else {
					//오른쪽에 가짜 동전이 있는 경우
					left = mid;
				}
				mid = (left+right)/2;
			}
			
			sb.append("#").append(test_case).append(" ").append(left).append("\n");
		}
		
		System.out.println(sb.toString());
	}
}
