import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class B11659 {
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		int[] numbers = new int[N+1];
		st = new StringTokenizer(in.readLine(), " ");
		for(int i = 1; i <= N; i++) {
			numbers[i] = Integer.parseInt(st.nextToken());
		}
		
		StringBuilder sb= new StringBuilder("");
		
		int[][] startEnd = new int[M][2];
		
		for(int i = 1; i <= N; i++) {
			numbers[i] += numbers[i-1];
		}
		
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(in.readLine(), " ");
			startEnd[i][0] = Integer.parseInt(st.nextToken());
			startEnd[i][1] = Integer.parseInt(st.nextToken());
		}
		
		int start = 0;
		int end = 0;
		
		int result = 0;
		
		for(int[] se: startEnd) {
			start = se[0];
			end = se[1];
			
			result = numbers[end];
			result -= numbers[start-1];
			
			sb.append(result + "\n");
		}

		System.out.println(sb.toString());
		in.close();
	}
}
