import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class B10971 {
	
	static int numCity;								//N, 도시의 개수
	static int[][] costs;							//비용 행렬
	static int[][] dp;								//결과값 저장
	static final int MAX_VALUE = 1_000_000 * 11;	//비용의 최대값
	static int allPath;

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		numCity = Integer.parseInt(in.readLine());	//N, 도시의 개수
		costs = new int[numCity][numCity];			//비용행렬
		
		//전체를 다 둘러본 path를 설정
		allPath = (1 << numCity) - 1;
		dp = new int[allPath+1][numCity];	//비용값 저장용
		
		for(int y = 1; y <= allPath; y++) {
			for(int x = 0; x < numCity; x++) {
				dp[y][x] = MAX_VALUE;	//적당한 값으로 초기화
			}
		}
		
		StringTokenizer st = null;
		int num;
		for(int y = 0; y < numCity; y++) {
			//비용 행렬 받기
			st = new StringTokenizer(in.readLine());
			for(int x = 0; x < numCity; x++) {
				num = Integer.parseInt(st.nextToken());
				costs[y][x] = num;
			}
		}
		//입력 종료

		dp(allPath, 0);	//어느도시에서 끝마치든 결과는 같다 -> 임의로 0번에서 마친다고 하자


		System.out.println(dp[allPath][0]);
	}
	
	//path는 비트마스킹을 통한 경로
	//cur는 현재 위치한 곳
	public static int dp(int path, int cur) {
		if( (path ^ (1 << cur)) == 0) {
			//0010과 같은 경우 -> 0번에서 1번으로 가는 값을 반환해주면 된다
			return costs[0][cur] == 0 ? MAX_VALUE : costs[0][cur];
		}
		
		if(dp[path][cur] != MAX_VALUE) {
			//저장되어 있을 경우
			return dp[path][cur];
		}
		
		int prevPath = path ^ (1 << cur);	//현재 도시를 안온 path
		for(int prev = 0; prev < numCity; prev++) {
			if(costs[prev][cur] == 0) continue;		//연결이 안된 경우 skip
			if( (path & (1 << cur)) == 0) continue;	//현재 경로에서 존재하지 않는 도시이면 skip
			//현재 저장된 값  vs 이전 경로+(이전도시->현재도시)
			dp[path][cur] = Math.min(dp[path][cur], dp(prevPath, prev) + costs[prev][cur]);
		}
		
		return dp[path][cur];
	}
}
