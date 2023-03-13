import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B17208 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		int numOrder = Integer.parseInt(st.nextToken());			//주문 개수
		int remainCheese = Integer.parseInt(st.nextToken());		//남은 치즈버거 개수
		int remainPotato = Integer.parseInt(st.nextToken());		//남은 감자튀김 개수
		
		
		int[][] inputs = new int[numOrder+1][2];	//[치즈버거 요구, 감자튀김 요구] -> 편하게 하기 위해 +1로 하기
		for(int i = 1; i <= numOrder; i++) {
			//주문 정보 받기
			st = new StringTokenizer(in.readLine());
			inputs[i][0] = Integer.parseInt(st.nextToken());
			inputs[i][1] = Integer.parseInt(st.nextToken());
		}
		//입력 종료
		
		// 주문 -> 치즈버거 -> 감자튀김 순으로 차원이 내려간다
		int[][][] dp = new int[numOrder+1][remainCheese+1][remainPotato+1];
		
		// 3차원 DP를 한다
		int targetCheese, targetPotato;	//현재 봐야되는 cheese와 potato 
		int prevCheese, prevPotato;		//현재 주문에 대한 이전 치즈 및 감자 값
		int prevMax;					//그전 최대값 저장
		for(int order = 1; order <= numOrder; order++) {
			//해당 주문에 대해 고려해보자
			targetCheese = inputs[order][0];
			targetPotato = inputs[order][1];
			for(int cheese = 1; cheese <= remainCheese; cheese++) {
				//치즈가 늘어나면?
				prevCheese = cheese - targetCheese;	//현재 주문에 대해 치즈를 뺀 것
				for(int potato = 1; potato <= remainPotato; potato++) {
					//감자가 늘어나면?
					prevPotato = potato - targetPotato;	//현재 주문에 대해 감자를 뺀 것
					prevMax = Math.max(dp[order-1][cheese][potato], dp[order][cheese][potato-1]);	//현재 주문 고려안한것과 현재 주문에서 감자 한개 뺀거랑 비교
					prevMax = Math.max(prevMax, dp[order][cheese-1][potato]);						//위에서 계산한 맥스와 치즈 한개 뺀거랑 비교
					if( prevCheese >= 0 && prevPotato >= 0) {	
						//치즈와 감자를 뺀 것을 고려해볼 수 있으면
						dp[order][cheese][potato] = Math.max(dp[order-1][prevCheese][prevPotato] + 1, prevMax);	//이번 주문을 고려안하고 계산한 애랑 최대값 후보랑 비교해보자
					}
					else {
						//고려해볼 수 없으면 그냥 넣자
						dp[order][cheese][potato] = prevMax;
					}
				}
			}
		}
		
		System.out.println(dp[numOrder][remainCheese][remainPotato]);	//DP의 마지막 배열에 존재하는 값이 최대값이다
	}
}
