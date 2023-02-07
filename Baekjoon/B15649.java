import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B15649{
	
	public static int N;
	public static int M;
	public static int numbers[];
	public static boolean[] visited;
	
	public static StringBuilder sb;
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				
		StringTokenizer st = new StringTokenizer(in.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		numbers = new int[M];
		visited = new boolean[N+1];
		
		sb = new StringBuilder();
	
		perm(0);

		System.out.println(sb.toString());
	}
	
	public static void perm(int cur) {
		if(cur == M) {
			for(int i = 0; i < M-1; i++) {
				sb.append(numbers[i] + " ");
			}
			sb.append(numbers[M-1] + "\n");
			
			return;
		}
		
		for(int i = 1; i <= N; i++) {
			if( !visited[i] ) {
				numbers[cur] = i;
				
				visited[i] = true;
				perm(cur+1);
				
				visited[i] = false;
			}
		}
	}
}