import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B1654 {

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int numLan, require;
		StringTokenizer st = new StringTokenizer(in.readLine());
		numLan = Integer.parseInt(st.nextToken());
		require = Integer.parseInt(st.nextToken());

		//랜선 입력 받기
		int[] lans = new int[numLan];
		int lan = 0;
		int limitLan = Integer.MIN_VALUE;
		for(int i = 0; i < numLan; i++) {
			lan= Integer.parseInt(in.readLine());
			lans[i] = lan;
			limitLan = Math.max(limitLan, lan);
		}
		
		//이분 탐색으로 하기
		long left = 1;
		long right = limitLan;
		long mid;
		long max = 0;	//랜선의 최대 길이 저장
		int currentLan = 0;	//현재 랜선의 개수 저장
		
		while(left <= right) {
			currentLan = 0;
			mid = (left + right) / 2;
			for(int i = 0; i < numLan; i++) {
				currentLan += lans[i] / mid;	//mid만큼 자르면 몇개로 잘리는지
			}
			
			if(currentLan >= require) {
				//필요한 랜선수 이상일 경우 -> 더 길게 자르기
				left = mid+1;
				max = mid;
			}
			else {
				//필요한 랜선수 미만일 경우 -> 더 짧게 자르기
				right = mid-1;
			}
		}
		
		System.out.println(max);
	}
}
