import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B12865 {

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		int numTarget = Integer.parseInt(st.nextToken());	//N, 물품의 수
		int maxWeight = Integer.parseInt(st.nextToken());	//K, 최대 무게
		
		Data[] targets = new Data[numTarget+1];
		int[][] dp = new int[numTarget+1][maxWeight+1];
		
		for(int i = 1; i <= numTarget; i++) {
			st = new StringTokenizer(in.readLine());
			targets[i] = new Data(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		//입력 종료
		
		for(int t = 1; t <= numTarget; t++) {
			Data target = targets[t];	//어느 물건인지 
			for(int w = 1; w <= maxWeight; w++) {
				//무게를 조절해보면서
				dp[t][w] = dp[t-1][w];	//일단 이 물건 고려안한거 가져오기
				if(w - target.weight >= 0) {
					//해당 물건을 해당 무게에서 넣을 수 있으면
					int tmpValue = dp[t-1][w-target.weight] + target.value;
					if(tmpValue > dp[t][w]) {
						//만약 해당 물건을 넣는게 더 가치가 높으면
						dp[t][w] = tmpValue;
					}
				}
			}
		}
		System.out.println(dp[numTarget][maxWeight]);
	}
	
	//
	static class Data{
		int weight;	//무게
		int value;	//가치
		
		public Data(int weight, int value) {
			this.weight = weight;
			this.value = value;
		}
	}
}
