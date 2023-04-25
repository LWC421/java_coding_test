import java.io.*;
import java.util.*;

public class B1102 {

	static int numBal;			//발전소의 개수
	static int[][] weights;		//각 발전소를 고치는데 필요한 비용들 저장
	static int targetActive;	//목표하는 켜진 발전소
	static int[] dp;
	
	static final int LIMIT = 16 * 36 + 1;
	
	static final int MSB = 15;	//발전소의 개수는 최대 16개이므로
	
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		int activeCount = 0;
		
		numBal = Integer.parseInt(in.readLine());
		weights = new int[numBal][numBal];
		
		int all = (1 << numBal) + 1;
		dp = new int[all];
		
		for(int from = 0; from < numBal; from++) {
			//발전소들 정보 가져오기
			st = new StringTokenizer(in.readLine());
			
			for(int to = 0; to < numBal; to++) {
				weights[from][to] = Integer.parseInt(st.nextToken());
			}
		}
		char[] line = in.readLine().toCharArray();
		
		int initActive = 0;
		for(int i = 0; i < numBal; i++) {
			//발전소 on/off 받기
			if(line[i] == 'Y') {
				initActive = (initActive | (1 << i));
				activeCount++;
			}
		}
		
		targetActive = Integer.parseInt(in.readLine());
		//입력 종료
		
		if(activeCount >= targetActive) {
			//이미 조건을 만족하면
			System.out.println('0');
			return;
		}
		
		if(activeCount == 0) {
			System.out.println(-1);
			return;
		}
		
		for(int i = 0; i < all; i++) {
			dp[i] = LIMIT;
		}
		
		//시작 발전소에 있으면
		int weight = dp(initActive);
		
		System.out.println(weight);
	}
	
	public static int dp(int actived) {
		int count = 0;
		
		//현재 켜진 발전소의 개수 체크
		for(int i = 0; i < numBal; i++) {
			if( (actived & (1 << i)) != 0 ) {
				count++;
			}
		}
		//목표 발전소 만큼 다 켰으면
		if(count == targetActive) {
			return 0; //더 이상의 비용이 없다
		}
		
		//저장된 값이 있으면
		if(dp[actived] != LIMIT) {
			return dp[actived];
		}
		
		int nActived;
		for(int i = 0; i < numBal; i++) {
			//각각의 발전소에 대해
			if( (actived & (1 << i)) == 0) {
				//아직 연결 안 된 곳이면
				int minWeight = 36;
				nActived = actived | (1 << i);
				for(int j = 0; j < numBal; j++) {
					if( (actived & (1 << j)) != 0) {
						//현재 발전소에서 nActived로 가는 최소의 비용을 계산해보자
						minWeight = Math.min(minWeight, weights[j][i]);
					}
				}
				
				dp[actived] = Math.min(dp[actived], dp(nActived) + minWeight) ;
			}
		}
		
		return dp[actived];
	}
}
