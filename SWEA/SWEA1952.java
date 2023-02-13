import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA1952 {
	static int[] prices = new int[4];		//이용권의 가격 저장
	static int[] numbers = new int[12];		//해당 달마다 몇 번 이용할 건지
	static int min;
	static int price;
	
	public static void main(String[] args) throws Exception {
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(in.readLine());
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;
		for(int test_case = 1; test_case <= T; test_case++) {
			price = 0;
			
			st = new StringTokenizer(in.readLine());
			for(int i = 0; i < 4; i++) {	//가격 정보 받아오기
				prices[i] = Integer.parseInt(st.nextToken());
			}
			
			//일단 년 이용권을 최소값으로 잡기
			min = prices[3];
			
			st = new StringTokenizer(in.readLine());
			for(int i = 0; i < 12; i++) {	//이용계획 받아오기
				numbers[i] = Integer.parseInt(st.nextToken());
			}
			
			dfs(0);
			sb.append("#").append(test_case).append(" ").append(min).append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	public static void dfs(int count) {
		if(count >= 12) {	//기저조건
			min = Math.min(min, price);
			return;
		}
		
		//1일
		price += numbers[count] * prices[0];
		dfs(count+1);
		price -= numbers[count] * prices[0];
		
		//1개월
		price += prices[1];
		dfs(count+1);
		price -= prices[1];
		
		//3개월
		price += prices[2];
		dfs(count+3);
		price -= prices[2];	
	}
}
