import java.io.*;
import java.util.*;

public class B1043 {
	
	static List<Integer>[] edges; 	//간선 저장용
	static boolean[] visited;		//방문 체크용
	
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		int numHuman = Integer.parseInt(st.nextToken());	//사람의 수
		int numParty = Integer.parseInt(st.nextToken());	//파티의 개수
		
		edges = new LinkedList[numHuman+1];	//0번은 사용안할 것이므로 +1로
		for(int i = 1; i <= numHuman; i++) {
			edges[i] = new LinkedList<>();
		}
		visited = new boolean[numHuman+1];	//방문 체크용
		
		st = new StringTokenizer(in.readLine(), " ");
		
		//진실 아는사람 입력 받기
		int numTrue = Integer.parseInt(st.nextToken());		//진실을 아는 사람
		int[] trues = new int[numTrue];				//진실을 아는 사람들의 번호 저장
		
		for(int i = 0; i < numTrue; i++) {
			trues[i] = Integer.parseInt(st.nextToken());
		}
		
		//파티 입력 받기
		
		List<Integer>[] parties = new LinkedList[numParty];	//파티 입력받기
		
		for(int i = 0; i < numParty; i++) {
			st = new StringTokenizer(in.readLine(), " ");
			
			int numPatience = Integer.parseInt(st.nextToken());	//파티의 참가자
			List<Integer> patience = new LinkedList<>();
			
			for(int j = 0; j < numPatience; j++) {
				//해당 파티의 참가자들 넣어주기
				patience.add(Integer.parseInt(st.nextToken()));
			}
			
			//각 파티 참가자들끼리 연간관계 넣어주기
			for(int x = 0; x < numPatience; x++) {
				for(int y = x+1; y < numPatience; y++) {
					edges[patience.get(x)].add(patience.get(y));
					edges[patience.get(y)].add(patience.get(x));	//역방향도 넣기
				}
			}
			
			parties[i] = new LinkedList(patience);	//파티 넣기
		}
		//모든 입력 종료
		Queue<Integer> q = new ArrayDeque<>();
		int curr;
		for(int trueHuman: trues) {
			q.add(trueHuman);	//진실을 아는 사람을 넣고
			while( !q.isEmpty() ) {
				//BFS를 하자
				curr = q.poll();
				if(visited[curr]) continue;	//이미 방문 했으면 넘기기
				visited[curr] = true;	//방문 예정으로 넣기
				
				for(int connected: edges[curr]) {
					//연결된 정점에 대해
					q.add(connected);
				}
			}
		}
		
		int result = 0;
		loop: for(List<Integer> party: parties) {
			for(int human: party) {
				//각각의 파티에 참가한 참가자들에 대해
				if(visited[human]) {
					//진실을 아는 사람이면
					continue loop;
				}
			}
			result++;
		}
		
		System.out.println(result);
		
	}
}