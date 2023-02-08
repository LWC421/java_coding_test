import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B15650 {

	static int N;
	static int M;
	static int[] numbers;
	static StringBuilder sb = null;
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		numbers = new int[M];
		sb = new StringBuilder("");
		
		combi(0, 1);
		
		System.out.println(sb.toString());
	}
	
	//조합 해보기
	public static void combi(int count, int start) {
		if(count == M) {	//다 뽑았다
			for(int num: numbers) {
				sb.append(num).append(" ");
			}
			sb.append("\n");
			return;
		}
		
		for(int i = start; i <= N; i++) {
			numbers[count] = i;
			combi(count+1, i+1);
		}
	}
}
