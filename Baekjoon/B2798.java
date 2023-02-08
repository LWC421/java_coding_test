import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B2798 {
	
	static int N;		//N장의 카드 중 3개를 뽑는다
	static int M;		//고른 카드의 합이 M을 넘으면 안된다
	static int[] cards;	//숫자가 쓰여진 카드들
	static int sum;
	static int max;
	
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		cards = new int[N];
		
		//카드 입력까지 받기
		st = new StringTokenizer(in.readLine(), " ");
		for(int i = 0; i < N; i++) {
			cards[i] = Integer.parseInt(st.nextToken());
		}
		
		combi(0, 0);
		System.out.println(max);
	}
	
	public static void combi(int count, int start) {
		if(sum > M) {	//숫자 합을 넘은 경우 pass해야한다
			return;
		}
		if(count == 3) {	//3장 다 뽑은 경우
			max = Math.max(max, sum);	//현재 뽑은 수와  기존 max중 어느게 더 큰지
			return;
		}
		
		//N개 중에 뽑기
		for(int i = start; i < N; i++) {
			sum += cards[i];
			
			combi(count + 1, i + 1);
			
			sum -= cards[i];	//되돌리기
		}
	}
}
