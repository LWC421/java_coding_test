import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

public class SWEA5432 {
	public static void main(String[] args) throws Exception {
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
		StringBuilder sb = new StringBuilder("");
		
		int T = Integer.parseInt(in.readLine());
		
		String str = null;
		for(int test_case = 1; test_case <= T; test_case++) {
			str = in.readLine();
			
			char c = '\u0000';
			int strLength = str.length();
			
			//stack을 사용
			//언제 넣었는지를 나타낸다
			Stack<Integer> stack = new Stack<>();
			
			int result = 0;
			
			//괄호에 대해 처리하기
			for(int i = 0; i < strLength; i++) {
				c = str.charAt(i);
				if(c == '(') {	//넣어야 된다
					stack.push(i);
				}
				else {	//닫는 괄호이다
					int left = stack.pop();
					if( (i - left) == 1) {	//1개 차이이면 레이저이다
						result += stack.size();
					}
					else {	//쇠막대이다
						result += 1;
					}
				}
			}
			
			sb.append("#").append(test_case).append(" ").append(result).append("\n");
		}
		
		System.out.println(sb.toString());
	}
}
