import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class SWEA5643 {

	public static void main(String[] args) throws Exception{
		// 1부터 N까지 학생 있다 -> 키는 모두 다르다
		// a는 b보다 작다가 M개만큼 주어진다
		// 자신의 키 순서가 몇 번째인지 확실히 아는 사람을 구하여라
		
		// 모순이 없이 주어진다 -> 사이클이 없다는 뜻이다
		// 어떤 한 노드를 기준으로 커지는 방향으로 나갈때는 커지는 방향으로만 방문
		//		작아지는 방향으로 나갈때는 작아지는 방향으로만 방문
		// 		모두 방문이 가능하면 나보다 큰 사람, 작은 사람이 모두 결정이 가능하다는 뜻이다
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;
		int T = Integer.parseInt(in.readLine());
		
		for(int test_case = 1; test_case <= T; test_case++) {
			//테스트 케이스 시작
			int numPerson = Integer.parseInt(in.readLine());		//N, 학생 수
			int numCompare = Integer.parseInt(in.readLine());		//M, 비교 횟수
			int smaller, bigger;
			
			int[][] map = new int[numPerson+1][numPerson+1];		//0번은 없으므로 +1로 풀자
			
			for(int i = 0; i < numCompare; i++) {
				st = new StringTokenizer(in.readLine());
				smaller = Integer.parseInt(st.nextToken());			//bigger보다 작은 사람
				bigger = Integer.parseInt(st.nextToken());			//smaller보다 큰 사람
				
				map[smaller][bigger] = 1;		//키가 커지는 방향이면 1
				map[bigger][smaller] = -1;		//키가 작아지는 방향이면 -1
			}
			//모든 입력 종료
			
			int resultCount = 0;		//결과값 저장용
			
			Queue<Data> q = new ArrayDeque<>();
			for(int from = 1; from <= numPerson; from++) {
				//start부터 시작해서 연결된 노드들 확인
				boolean[] visited = new boolean[numPerson+1];	//방문 체크용
				visited[from] = true;			//여기는 처음 시작지점이므로 방문했다고 넣자
				for(int to = 1; to <= numPerson; to++) {
					if(map[from][to] != 0) {
						q.add(new Data(to, map[from][to]));	//1이면 커지는 방향, -1이면 작아지는 방향
						visited[to] = true;		//여기는 방문 예정이다
					}
				}
				int visitCount = 1;	//start는 이미 방문 한거다
				
				
				Data curr = null;		//q에서 빼낸거 받기용
				while( !q.isEmpty() ) {
					curr = q.poll();
					visitCount++;
					
					for(int to = 1; to <= numPerson; to++) {
						if(curr.order == map[curr.target][to] && !visited[to]) {
							//커지는 방향 또는 작아지는 방향이 그전에 온 것과 일치하면서
							//방문 안했으면
							q.add(new Data(to, map[curr.target][to]));
							visited[to] = true;		//방문 예정으로 넣자
						}
						
					}
				}
				if(visitCount == numPerson) {
					//만약 사람 수만큼 돌수 있으면 -> 해당 노드는 키 순서를 알 수 있다는 뜻이다
					resultCount++;
				}
			}
			
			
			sb.append(String.format("#%d %d\n", test_case, resultCount));
		}
		
		System.out.println(sb.toString());
	}
	
	static class Data{
		int target;		//내가 누군지
		int order;		//순서 저장
		
		public Data(int target, int order) {
			this.target = target;
			this.order = order;
		}
	}
}
