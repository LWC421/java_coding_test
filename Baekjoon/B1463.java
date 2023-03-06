import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class B1463 {

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(in.readLine());	//정수 N
		
		int[] visited = new int[N+1];
		Arrays.fill(visited, 1000000 + 1);
		//현재 숫자 넣기
		Queue<Integer> q = new ArrayDeque<>();
		q.add(1);	//처음 숫자 넣기
		int cur;
		int count = 0;
		int size;
		while( !q.isEmpty() ) {
			size = q.size();
			for(int s = 0; s < size; s++) {
				cur = q.poll();
				if(cur == N) {
					System.out.println(count);
					return;	//N에 도착했으면
				}
				if(count >= visited[cur]) {
					continue;
				}
				visited[cur] = count;	//횟수 넣기
				if(count < visited[cur+1]) {
					q.add(cur+1);
				}
				if(cur*2 <= N && count < visited[cur*2]) {
					q.add(cur*2);
				}
				if(cur*3 <= N && count < visited[cur*3]) {
					q.add(cur*3);
				}
			}
			count++;	//연산횟수 늘리기
		}
	}
}