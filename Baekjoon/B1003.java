import java.io.BufferedReader;
import java.io.InputStreamReader;

public class B1003 {
	static int N;
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(in.readLine());
		StringBuilder sb = new StringBuilder("");
		for(int test_case = 1; test_case <= T; test_case++) {
			N = Integer.parseInt(in.readLine());
			
			if(N == 0) {	//피보 0인경우만 따로 처리
				sb.append(1).append(" ").append(0).append("\n");
				continue;
			}
			
			//N피보에 대한 0, 1 출력하기
			int[][] pibos = new int[N+1][2];	//0은 0이 몇번 불리는지, 1은 1이 몇번불리는지
			pibos[0][0] = 1;
			pibos[1][1] = 1;	//0피보랑 1피보 각각 1로 초기화
			
			
			for(int i = 2; i <= N; i++) {
				pibos[i][0] = pibos[i-1][0] + pibos[i-2][0];
				pibos[i][1] = pibos[i-1][1] + pibos[i-2][1];
			}
			
			sb.append(pibos[N][0]).append(" ").append(pibos[N][1]).append("\n");
		}
		
		System.out.println(sb);
	}
}
