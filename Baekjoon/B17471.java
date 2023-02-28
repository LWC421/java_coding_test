import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class B17471 {

	//인구 -> 점수
	static int numArea;					//구역의 개수, [2 ~ 10]
	static int[] scores;				//각 구역의 점수, 각 원소는 [1 ~ 100]
	static boolean[][] map;				//연결 여부
	static List<int[]>	set1;	//부분집합 저장용1
	static List<int[]>	set2;	//부분집합 저장용2
	static List<Integer> currentSet1;	//현재 어느 set만드는 중인지
	static List<Integer> currentSet2;	//어느 set만드는 중인지 2
	static int minDiff;					//최소 차이
	
	
	public static void main(String[] args) throws Exception{
		//각 구역을 연결되게 분할
		//각 구역의 점수 차이를 최소화 하기
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		numArea = Integer.parseInt(in.readLine());
		scores = new int[numArea+1];
		map = new boolean[numArea+1][numArea+1];	//1번부터 시작하므로 +1로 초기화
		StringTokenizer st = new StringTokenizer(in.readLine());
		for(int i = 1; i <= numArea; i++) {
			//점수 정보 받기
			scores[i] = Integer.parseInt(st.nextToken());
		}
		
		set1 = new LinkedList<>();
		set2 = new LinkedList<>();
		currentSet1 = new ArrayList<>();
		currentSet2 = new ArrayList<>();
		minDiff = Integer.MAX_VALUE;	//일단 최대값으로 설정
		
		int to;	//어느 도시와 연결되는지 저장용
		int numConnected;	//연결된 도시의 개수
		for(int from = 1; from <= numArea; from++) {
			//연결 정보 받기
			st = new StringTokenizer(in.readLine());
			numConnected = Integer.parseInt(st.nextToken());
			
			for(int j = 0; j < numConnected; j++) {
				//i에 연결된 도시들의 정보 받아오기
				to = Integer.parseInt(st.nextToken());
				map[from][to] = true;	//굳이 양방향으로 연결 안해도된다
			}
		}
		//입력 종료
		
		//부분집합 만들기 -> 2^numArea - 2 만큼 생긴다
		powerSet(1);
		
		int numSet = set1.size();
		int[] area1, area2;
		for(int i = 0; i < numSet; i++) {
			//set을 돌면서
			area1 = set1.get(i);
			area2 = set2.get(i);
			bfs(area1, area2);	//각 영역의 집합을 가져오기
		}
		
		//2군데로 나누는게 안되면
		System.out.println(minDiff == Integer.MAX_VALUE ? "-1" : minDiff);
	}
	
	public static void bfs(int[] area1, int[] area2) {
		int curArea = area1[0];	//어느 구역을 합쳐볼건지
		Set<Integer> set = new HashSet<>();
		
		for(int a: area1) {
			set.add(a);	//해당 구역 넣기
		}
		
		boolean[] visited = new boolean[numArea+1];
		visited[curArea] = true;
		int curCombined = 0;	//현재 몇 구역이나 통합되었는지

		Queue<Integer> q = new ArrayDeque<>();
		q.add(curArea);	//일단 처음거 넣기
		while( !q.isEmpty() ) {
			curArea = q.poll();
			curCombined++;	//통합된 구역에 넣기
			
			for(int x = 1; x <= numArea; x++) {
				//연결된 지점 찾기
				if( !visited[x] && map[curArea][x] && set.contains(x)) {
					//방문 하지 않았고, 연결되어 있으며, 해당 집합에서 넣어야 하는 거면
					q.add(x);	//해당지점 큐에 넣기
					visited[x] = true;	//방문 예정이라고 표시
				}
			}
		}
		
		if(curCombined < area1.length) {
			//만약 통합이 덜 되었으면 -> 해당 구역은 연결이 안된다는 소리다
			return;
		}
		
		//두번째 구역도 해보기 -> 일단 그전에 썼던거 초기화부터
		curArea = area2[0];
		set.clear();
		for(int a: area2) {
			set.add(a);
		}
		for(int i = 1; i <= numArea; i++) {
			visited[i] = false;
		}
		visited[curArea] = true;
		curCombined = 0;
		
		q.add(curArea);
		while( !q.isEmpty() ) {
			curArea = q.poll();
			curCombined++;	//통합된 구역에 넣기
			
			for(int x = 1; x <= numArea; x++) {
				//연결된 지점 찾기
				if( !visited[x] && map[curArea][x] && set.contains(x)) {
					//방문 하지 않았고 연결되어 있으며 해당 집합에서 넣어야 하는 거면
					q.add(x);	//해당지점 큐에 넣기
					visited[x] = true;	//방문 예정이라고 표시
				}
			}
		}
		
		if(curCombined < area2.length) {
			return;	//통합이 안되었으면
		}
		
		//여기서부터는 통합이 되었다는 소리다
		int score1 = 0;
		int score2 = 0;	//해당 구역의 점수들 초기화
		
		for(int x: area1) {
			score1 += scores[x];	//1번째 구역 점수 더해주기
		}
		for(int x: area2) {
			score2 += scores[x];	//2번째 구역 점수 더해주기
		}
		
		minDiff = Math.min(minDiff, Math.abs(score1 - score2));	//차이값 갱신해주기
	}
	
	public static void powerSet(int current) {
		if(current == numArea+1) {
			if(currentSet1.size() == 0 || currentSet2.size() == 0) {
				//둘중에 아무것도 선택이 안된 경우가 있으면
				return;
			}
			
			//다 완성된 경우
			int size = currentSet1.size();
			int[] tmp = new int[currentSet1.size()];
			for(int i = 0; i < size; i++) {
				//복사하기
				tmp[i] = currentSet1.get(i);
			}
			set1.add(tmp);	//set에다가 넣기
			
			//set2에다가도 넣기
			size = currentSet2.size();
			tmp = new int[currentSet2.size()];
			for(int i = 0; i < size; i++) {
				//복사하기
				tmp[i] = currentSet2.get(i);
			}
			set2.add(tmp);
			return;
		}
		
		//set1에 넣거나, set2에 넣거나 둘중하나 하기
		currentSet1.add(current);	//set1에 넣은 경우
		powerSet(current+1);
		
		currentSet1.remove(currentSet1.size() - 1);	//원상 복구
		currentSet2.add(current);	//set2에 넣은 경우
		powerSet(current+1);
		currentSet2.remove(currentSet2.size() - 1);	//원상 복구
	}
}
