import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.ArrayDeque;


public class B2644 {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int numPerson = Integer.parseInt(in.readLine());
		
		StringTokenizer st = new StringTokenizer(in.readLine());
		int start, end;	//촌수 계산 해야 되는 사람
		start = Integer.parseInt(st.nextToken());
		end = Integer.parseInt(st.nextToken());
		
		//0번인 사람이 없으므로 +1만큼해서 관계 맺기
		boolean[][] map = new boolean[numPerson+1][numPerson+1];
		
		//부모 자식 관계의 개수
		int numEdge = Integer.parseInt(in.readLine());
		int x, y;	//x가 y의 부모이다
		for(int i = 0; i < numEdge; i++) {
			st = new StringTokenizer(in.readLine());
			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());
			
			map[y][x] = true;
			map[x][y] = true;
		}
		//입력받기 종료
		
		//방문체크용
		boolean[] visited = new boolean[numPerson+1];
		visited[start] = true;	//일단 시작하는 곳은 true로 변경
		
		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[] {start, 0});	//[현재 위치, 촌수]를 나타낸다
		
		int[] tmp;
		int current;	//현재 위치
		int chonsu;		//촌수
		
		while( !q.isEmpty() ) {
			tmp = q.poll();
			current = tmp[0];
			chonsu = tmp[1];
			
			if(current == end) {
				System.out.println(chonsu);
				return;
			}
			
			for(int dest = 1; dest <= numPerson ; dest++) {
				//관계가 맺어진 사람 찾아가기
				if(map[current][dest] && !visited[dest]) {
					//관계가 있으면서 안 가봤으면
					q.add(new int[] {dest, chonsu+1});
					visited[dest] = true;	//방문했다고 체크하기
				}
			}
		}
		
		//current == end인 조건을 못 만나서 종료가 안됐다 -> 서로 관계가 없다는 뜻
		System.out.println("-1");
	}
}
