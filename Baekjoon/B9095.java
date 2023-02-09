import java.io.BufferedReader;
import java.io.InputStreamReader;

public class B9095 {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		StringBuilder sb = new StringBuilder("");
		int[] map = new int[11];	//0 ~ 10까지 사용
		
		map[1] = 1;
		map[2] = 2;
		map[3] = 4;
		
		for(int i = 4; i < 11; i++) {
			map[i] = map[i-1] + map[i-2] + map[i-3];
		}
		
		int T = Integer.parseInt(in.readLine());
		for(int test_case = 1; test_case <= T; test_case++) {
			int n = Integer.parseInt(in.readLine());
			
			sb.append(map[n]).append("\n");
		}
		
		System.out.println(sb);
	}
}
