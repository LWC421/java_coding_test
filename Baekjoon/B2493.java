import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class B2493 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(in.readLine());
		Stack<Integer> stack = new Stack<>();
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		for(int i = 0; i < N; i++) {
			stack.push(Integer.parseInt(st.nextToken()));
		}
		
		int cur = -1;
		Stack<int[]> prev = new Stack<>();
		int[] result = new int[N+1];
		
		prev.push(new int[] {stack.pop(), N});
		
		for(int i = N-1; i > 0; i--) {
			cur = stack.pop();	//현재 탑의 높이
			//낮은 송신탑의 높이가 있다면 -> 빼서 넣기
			while( !prev.isEmpty() && prev.peek()[0] <= cur) {
				result[prev.pop()[1]] = i;
			}
			prev.push(new int[] {cur, i});
		}
			
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i < result.length; i++) {
			sb.append(result[i]).append(" ");
		}
		
		System.out.println(sb.toString());
	}
}
