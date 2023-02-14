import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class SWEA4008 {

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");
		
		int T = Integer.parseInt(in.readLine());
		
		int N;			//숫자의 개수
		int numSum;		//더하기의 개수
		int numMinus;	//마이너스의 개수
		int numMult;	//곱하기의 개수
		int numDiv;		//나누기의 개수
		
		//각각 연산자를 몇 개 사용했는지
		int sum;
		int minus;
		int mult;
		int div;
		
		int[] numbers;	//숫자들
		
		int[] tmp;		//큐에서 받은 값 임시 저장
		Queue<int[]> q;	//큐 사용, [현재까지 값, 현재 봐야되는 값의 인덱스, 사용한 더하기, 사용한 마이너스, 사용한 곱하기, 사용한 나누기]
		int number;		//큐에서 꺼낸 값
		int index;		//큐에서 꺼낸 인덱스값
		int max;		//최대값
		int min;		//최소값
		
		for(int test_case = 1; test_case <= T; test_case++) {
			max = Integer.MIN_VALUE;
			min = Integer.MAX_VALUE;
			
			// -----------입력 받기 ----------------
			N = Integer.parseInt(in.readLine());
			st  = new StringTokenizer(in.readLine());
			numSum = Integer.parseInt(st.nextToken());
			numMinus = Integer.parseInt(st.nextToken());
			numMult = Integer.parseInt(st.nextToken());
			numDiv = Integer.parseInt(st.nextToken());
			
			numbers = new int[N];
			st = new StringTokenizer(in.readLine());
			for(int i = 0; i < N; i++) {
				numbers[i] = Integer.parseInt(st.nextToken());
			}
			// -------------입력 받기 끝------------
			
			q = new ArrayDeque<>();
//			[현재까지 값, 현재 봐야되는 값의 인덱스, 사용한 더하기, 사용한 마이너스, 사용한 곱하기, 사용한 나누기]
			q.add(new int[] {numbers[0], 1, 0, 0, 0, 0});
			
			
			while( !q.isEmpty() ) {
				//큐에서 꺼내기
				tmp = q.poll();
				
				number = tmp[0];
				index = tmp[1];
				sum = tmp[2];
				minus = tmp[3];
				mult = tmp[4];
				div = tmp[5];
				
				if(index == N) {	//종료하기
					min = Math.min(min, number);
					max = Math.max(max, number);
					continue;
				}
				
				if(sum < numSum) {		//더하기를 사용할 수 있으면
					q.add(new int[] {number+numbers[index], index+1, sum+1, minus, mult, div});
				}
				if(minus < numMinus) {	//빼기를 사용할 수 있으면
					q.add(new int[] {number-numbers[index], index+1, sum, minus+1, mult, div});
				}
				if(mult < numMult) {	//곱하기를 사용할 수 있으면
					q.add(new int[] {number*numbers[index], index+1, sum, minus, mult+1, div});
				}
				if(div < numDiv) {		//나누기를 사용할 수 있으면
					q.add(new int[] {number/numbers[index], index+1, sum, minus, mult, div+1});
				}
				
			}
			
			
			// 출력 하기
			sb.append("#").append(test_case).append(" ").append(max - min).append("\n");
		}
		System.out.println(sb.toString());
	}

}
