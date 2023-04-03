import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA1263 {
	
	final static int INF = 1000 * 1000 * 2;

	public static void main(String[] args) throws Exception{
		// i -> j의 합으로 결정한다
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		StringBuilder sb = new StringBuilder("");
		int T = Integer.parseInt(in.readLine());
		
		StringTokenizer st = null;
		for(int test_case = 1; test_case <= T; test_case++) {
			//테스트 케이스 시작
			st = new StringTokenizer(in.readLine());
			
			int numPerson = Integer.parseInt(st.nextToken());	//N, 사람의 수
			int[][] map = new int[numPerson][numPerson];		//인접행렬
			int[][] dp = new int[numPerson][numPerson];			//플로이드-워셜 행렬
			
			int num;
			for(int y = 0; y < numPerson; y++) {
				for(int x = 0; x < numPerson; x++) {
					num = Integer.parseInt(st.nextToken());
					if(num == 0) num = INF;	//연결이 안되어 있을 경우
					if(y == x) num = 0;
					map[y][x] = num;
					dp[y][x] = num;
				}
			}
			//입력 종료
			
			int goAround;	//경유하는 경우
			for(int mid = 0; mid < numPerson; mid++) {
				for(int start = 0; start < numPerson; start++) {
					for(int end = 0; end < numPerson; end++) {
						goAround = dp[start][mid] +  dp[mid][end];
						if(goAround < dp[start][end]) {
							//만약 경유하는게 더 나으면
							dp[start][end] = goAround;
						}
					}
				}
			}
			
			int minCC = INF;		//가장 작은 CC값
			int rowSum;			//한 사람의 CC값 저장용
			for(int y = 0; y < numPerson; y++) {
				rowSum = 0;
				for(int x = 0; x < numPerson; x++) {
					rowSum += dp[y][x];
				}
				if(minCC > rowSum) minCC = rowSum;
			}
			
			sb.append(String.format("#%d %d\n", test_case, minCC));
		}
		System.out.println(sb.toString());
	}
}
