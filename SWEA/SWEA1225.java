import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA1225 {
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));	
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));	
		
		StringBuilder sb = new StringBuilder("");
		
		String first = null;
		int test_case = 0;
		StringTokenizer st = null;	
		int[] numbers = new int[8];
		
		//읽을 게 있으면 계속
		while( (first = in.readLine()) != null) {
			test_case++;
			st = new StringTokenizer(in.readLine());
			
			//수 입력 받기
			for(int i = 0; i < 8; i++) {
				numbers[i] = Integer.parseInt(st.nextToken());
			}
			
			int cur = 0;
			int num = 4;
			while(true) {
				numbers[cur] = numbers[cur] - (5 - num);	//해당 숫자만큼 빼기
				num = (5 + num - 1) % 5;
				
				//탈출 조건
				if(numbers[cur] <= 0) {
					numbers[cur] = 0;
					cur = (cur + 1) % 8;
					break;
				}
				
				cur = (cur + 1) % 8;
			}
			
			sb.append("#").append(test_case).append(" ");
			//암호 찾기
			int last = (8+cur-1) % 8;
			for(int i = cur; i != last; i = (i+1) %8 ) {
				sb.append(numbers[i]).append(" ");
			}
			sb.append(numbers[last]).append("\n");
		}
		
		System.out.println(sb.toString());
	}
}
