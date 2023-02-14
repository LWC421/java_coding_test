import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class B1697 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		//최대값 받기
		int max = Math.max(N, K);

		Queue<int[]> q = new ArrayDeque<>();
		boolean[] visited = new boolean[max * 2 + 1];	//최대 * 2로 visited 관리

		//현재 N에서 시작하여 0번째 카운트 라는 뜻
		q.add(new int[] {N, 0});
		int[] tmp;
		int current;
		int count;

		while( !q.isEmpty() ) {
			tmp = q.poll();
			current = tmp[0];
			count = tmp[1];

			//도착 했으면
			if(current == K) {
				System.out.println(count);
				return;
			}

			if(visited[current]) {
				continue;	//이미 방문 한 곳이면
			}
			visited[current] = true;

			// +1 이동이 가능하면
			if(current+1 <= max * 2 && !visited[current+1]) {
				q.add(new int[] {current+1, count+1});
			}
			// -1 이동이 가능하면
			if(current > 0 && !visited[current-1]) {
				q.add(new int[] {current-1, count+1});
			}
			// *2 이동이 가능하면
			if(current*2 <= max * 2 && !visited[current*2]) {
				q.add(new int[] {current*2, count+1});
			}
		}
	}
}
