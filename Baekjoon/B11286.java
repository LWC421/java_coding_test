import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class B11286 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		
		int N = Integer.parseInt(in.readLine());	//연산의 개수
		PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> {
			return a[1] == b[1] ? Integer.compare(a[0], b[0]) : a[1] - b[1];
		});	//숫자를 보관할 큐이다, [그냥 값, 절대값]으로 넣기
		
		int target;	//입력 받는 값
		for(int i = 0; i < N; i++) {
			target = Integer.parseInt(in.readLine());
			if(target == 0) {	//입력값이 0일때에 대해 -> 뽑아서 출력
				if(q.isEmpty()) {	//비어있을 경우
					sb.append(0).append("\n");
				}
				else {	//가장 작은 값 뽑기
					sb.append(q.poll()[0]).append("\n");
				}
			}
			else {	//입력값이 0이 아닐때에 대해 -> 넣기 
				q.add(new int[] {target, Math.abs(target)});
			}
		}
		
		System.out.println(sb.toString());
	}
}
