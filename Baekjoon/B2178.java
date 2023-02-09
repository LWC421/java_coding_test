import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class B2178 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		//N은 행의 개수
		//M은 열의 개수
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		int[][] map = new int[N][M];
		boolean[][] visited = new boolean[N][M];
		
		//맵 구성하기
		for(int y = 0; y < N; y++) {
			String s = in.readLine();
			for(int x = 0; x < M; x++) {
				map[y][x] = Integer.parseInt(String.valueOf(s.charAt(x)));
			}
		}
		
		// [y, x, count]와 같이 2개씩 가지는 배열을 큐로 사용할 것
		Queue<Data> queue = new LinkedList<>();
		queue.add(new Data(0, 0, 1));
		
		
		int y = 0;
		int x = 0;
		int count = 0;
		Data current = null;
		while( !queue.isEmpty() ) {	//비어있지 않으면 계속
			
			//큐에서 꺼내서 쓰기
			current = queue.poll();
			y     = current.y;
			x     = current.x;
			count = current.count;
			
			if(visited[y][x] == true) {	//다른 곳에서 이미 방문했으면
				continue;
			}
			
			visited[y][x] = true;	//방문 했다고 표시
			
			if(y == N-1 && x == M-1) {	//도착한 경우
				System.out.println(count);	//몇 번만에 인지 찍고 나가기
				return;
			}
			
			//위로 갈 수 있으면
			if(y > 0 && map[y-1][x] == 1 && !visited[y-1][x]) {
				queue.add(new Data(y-1, x, count+1));
			}
			//아래로 갈 수 있으면
			if(y < N-1 && map[y+1][x] == 1 && !visited[y+1][x]) {
				queue.add(new Data(y+1, x, count+1));
			}
			//왼쪽으로 갈 수 있으면
			if(x > 0 && map[y][x-1] == 1 && !visited[y][x-1]) {
				queue.add(new Data(y, x-1, count+1));
			}
			//오른쪽으로 갈 수 있으면
			if(x < M-1 && map[y][x+1] == 1 && !visited[y][x+1]) {
				queue.add(new Data(y, x+1, count+1));
			}
		}
	}
}

class Data{
	int y;
	int x;
	int count;
	Data(int y, int x, int count){
		this.y = y;
		this.x = x;
		this.count = count;
	}
}
