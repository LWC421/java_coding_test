import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class B11003 {
	
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		int numNumber = Integer.parseInt(st.nextToken());	// N, 수의 개수
		int length = Integer.parseInt(st.nextToken());		// L, 길이
		
		st = new StringTokenizer(in.readLine());
		int[] numbers = new int[numNumber];
		
		for(int i = 0; i < numNumber; i++){
			numbers[i] = Integer.parseInt(st.nextToken());
		}
		//입력 종료
		
		ArrayDeque<Integer> dq = new ArrayDeque<>();
		dq.addFirst(numbers[0]);
		sb.append(numbers[0]).append(' ');
		//처음에 길이만큼 작업해주기
		for(int i = 1; i < length; i++) {
			int num = numbers[i];
			
			while(!dq.isEmpty() && dq.getLast() > num) {
				//현재 값보다 큰값들이 있으면 뒤에서부터 다 빼주기
				dq.pollLast();
			}
			dq.addLast(num);
			
			sb.append(dq.getFirst()).append(" ");
		}
		
		for(int i = length; i < numNumber; i++) {
			int prev = numbers[i - length];
			int current = numbers[i];
			
			int first = dq.getFirst();
			if(first == prev) {
				//만약 첫 숫자가 빠져야되는거면
				dq.pollFirst();
			}
			
			while(!dq.isEmpty() && dq.getLast() > current) {
				//현재 값보다 큰값들이 있으면 뒤에서부터 다 빼주기
				dq.pollLast();
			}
			dq.addLast(current);
			
			sb.append(dq.getFirst()).append(" ");
		}
		
		System.out.println(sb.toString());
	}
}
