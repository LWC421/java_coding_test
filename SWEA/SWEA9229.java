import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SWEA9229 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;
		
		int T = Integer.parseInt(in.readLine());
		int N;	//과자 봉지 개수
		int M;	//무게 합 제한
		
		int[] numbers;	//과자들의 무게
		int max;		//현재까지 최대 무게
		int sum;		//과자 두 개의 합
		
		for(int test_case = 1; test_case <= T; test_case++) {
			max = -1;
			
			//입력 받기
			st = new StringTokenizer(in.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			
			numbers = new int[N];
			
			st = new StringTokenizer(in.readLine());
			for(int i = 0; i < N; i++) {
				numbers[i] = Integer.parseInt(st.nextToken());
			}
			
			//입력 받기 종료
			
			//오름차순으로 정렬
			Arrays.sort(numbers);

			for(int left = 0; left < N-1; left++) {
				for(int right = left+1; right < N; right++) {
					sum = numbers[left] + numbers[right];
					if(sum <= M) {	//허용 무게 이면
						max = Math.max(max, sum);
					}
					else {	//더이상 더 좋은 수가 없으므로 다음 left로 넘어가기
						break;
					}
				}
			}
			sb.append("#").append(test_case).append(" ").append(max).append("\n");
		}
		
		System.out.println(sb.toString());
	}
}
