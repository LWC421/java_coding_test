import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class B17472 {
	
	final static int INF = 10000;
	
	//4방위 탐색용
	final static int[] dy = {0, 1, 0, -1};
	final static int[] dx = {1, 0, -1, 0};
	
	static int[][] map;
	static boolean[][] visited = null;	//방문 체크용
	static List<List<Coord>> islands = null;
	static int[][] distances = null;		//섬들간의 거리 체크용

	public static void main(String[] args) throws Exception{
		// N * M 지도
		// 섬 <-> 섬 연결
		// 다리는 일직선으로만 간다
		// 다리 길이는 무조건 2이상이어야 한다
		// 섬과 연결 -> 가로방향이면 섬의 좌우측 / 세로방향이면 섬의 상하측이 연결된다는 소리
		// 다리가 겹치면 따로따로 계산한다
		
		// 맵 크기 1 ~ 10/ 최대 3칸 이상은 보장
		// 섬 개수 2 ~ 6
		
		// 땅은 1, 바다는 0
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		int yLength = Integer.parseInt(st.nextToken());
		int xLength = Integer.parseInt(st.nextToken());
		
		int yLimit = yLength+2;
		int xLimit = xLength+2;
		
		map = new int[yLimit][xLimit];	//편하게하기위해 +2로 잡기
		
		for(int y = 1; y <= yLength; y++) {
			st = new StringTokenizer(in.readLine());
			for(int x = 1; x <= xLength; x++) {
				map[y][x] = Integer.parseInt(st.nextToken());
			}
		}
		//모든 입력 종료
		islands = new LinkedList<>();
		
		//벽치기 시작
		for(int y = 0; y < yLimit; y++) {
			map[y][0] 	   = -1;
			map[y][xLimit-1] = -1;
		}
		for(int x = 0; x < xLimit; x++) {
			map[0][x] = -1;
			map[yLimit-1][x] = -1;
		}
//		벽치기 종료
		
		visited = new boolean[yLength+2][xLength+2];
		
		//섬인거 체크하기
		for(int y = 1; y <= yLength; y++) {
			for(int x = 1; x <= xLength; x++) {
				if(map[y][x] == 1 && !visited[y][x]) {
					//땅이면서 체크 안한거면
					findIsland(y, x);	//현재지점에서 섬을 구해라
				}
			}
		}
		//섬체크 종료
		
		getEdge();	//섬들의 테두리만 남기자
		
		//섬과 섬들간의 거리를 측정하여서 거리에 넣어주자
		int islandsSize = islands.size();
		distances = new int[islandsSize][islandsSize];
		for(int y = 0; y < islandsSize; y++) {
			for(int x = 0; x < islandsSize; x++) {
				distances[y][x] = INF;	//일단 거리 행렬을 최대값으로 초기화
			}
		}
		getDistance();		//섬들간의 거리를 측정하자
//		for(int[] row: distances) {
//			System.out.println(Arrays.toString(row));
//		}
		
		System.out.println(MST());
		
		
	}
	
	//해당지점에서 섬을 찾아서 연결한 후 반환
	static void findIsland(int y, int x) {
		Queue<Coord> q = new ArrayDeque<>();
		q.add(new Coord(y, x));
		visited[y][x] = true;	//여기는 방문예정이다
		
		Coord curr = null;
		List<Coord> currentIsland = new LinkedList<>();
		int nY, nX;
		while( !q.isEmpty() ) {
			curr = q.poll();	//큐에서 빼내서
			currentIsland.add(curr);	//현재지점을 현재 섬의 일부분으로 넣기
			
			for(int d = 0; d < 4; d++) {
				nY = curr.y + dy[d];
				nX = curr.x + dx[d];
				if(map[nY][nX] == -1) continue;	//맵밖이면
				if(visited[nY][nX]) continue;	//이미 방문했으면
				
				if(map[nY][nX] == 1) {
					q.add(new Coord(nY, nX));
					visited[nY][nX] = true;		//여기는 방문 예정이다
				}
			}
		}
		
		islands.add(currentIsland);	//섬 리스트에 현재의 섬의 구성을 넣어주자
	}


	//섬의 테두리만 남겨두기
	static void getEdge() {
		int islandCount = islands.size();
		int nY, nX;
		boolean isEdge;	//테두리인지 확인용
		for(int i = 0; i < islandCount; i++) {
			List<Coord> island = islands.get(i);
			for(int j = 0; j < island.size(); j++) {
				Coord curr = island.get(j);	//현재 지점 가져와서
				isEdge = false;
				for(int d = 0; d < 4; d++) {
					nY = curr.y + dy[d];
					nX = curr.x + dx[d];
					if(map[nY][nX] == 0) {
						//4방위 중 바다가 있으면 -> 테두리라는 뜻이다
						isEdge = true;
					}
				}
				if( !isEdge ) {
					//만약 테두리가 아니면
					island.remove(j);
					j--;
				}
			}
		}
	}
	
	
	static void getDistance() {
		int islandSize = islands.size();
		
		List<Coord> srcIsland = null;
		List<Coord> destIsland = null;
		for(int src = 0; src < islandSize; src++) {
			srcIsland = islands.get(src);
			int srcSize = srcIsland.size();	//src의 섬들의 좌표들을 가져오자
			for(int dest = 0; dest < islandSize; dest++) {
				if(src == dest) continue;	//같은 섬에서 같은섬으로 갈 필요 없다
				destIsland = islands.get(dest);	//섬 정보 가져와서
				Coord srcCoord = null;
				Coord destCoord = null;
				
				int destSize = destIsland.size();	//dest의 섬들의 좌표들을 가져오자
				
				for(int s = 0; s < srcSize; s++) {
					srcCoord = srcIsland.get(s);		//얘를 가져와서
					for(int d = 0; d < destSize; d++) {
						destCoord = destIsland.get(d);	//상대 좌표와 비교해보자
						int distance = calcDistance(srcCoord, destCoord);
						if(distance < distances[src][dest]) {
							distances[src][dest] = distance;	//거리가 더 가까우면 갱신
						}
					}
				}
			}
		}
	}
	
	static int calcDistance(Coord src, Coord dest) {
		if(src.y == dest.y) {
			//y축 선상에 있으면
			int absDx = Math.abs(src.x - dest.x);
			if(absDx == 2) return INF;	//섬과 섬간의 다리 길이가 1이라서 놓을 수 없다
			
			int left = Math.min(src.x, dest.x);
			int right = Math.max(src.x, dest.x);	//왼쪽 오른쪽의 x좌표를 구한후
			
			for(int x = left+1; x < right; x++) {
				//left +1부터 -> 바다부터 봐야한다는 소리다
				if(map[src.y][x] == 1) return INF;	//만약 중간에 다른 섬이 있다면 -> 현재 이 지점과 이지점을 놓을 수 없다
			}
			//여기까지 왔다는 것은 중간에 다른 섬이 없어서 다리를 놓을 수 있다는 뜻이다
			return right - left - 1;	//두 섬 간의 다리 길이를 리턴하자
			
		} 
		else if(src.x == dest.x) {
			//x축 선상에 있으면
			int absDy = Math.abs(src.y - dest.y);
			if(absDy == 2) return INF;	//섬과 섬간의 다리 길이가 1이라서 놓을 수 없다
			
			int top = Math.min(src.y, dest.y);
			int bottom = Math.max(src.y, dest.y);	//위쪽과 아래쪽의 y좌표를 구한후
			
			for(int y = top+1; y < bottom; y++) {
				//left +1부터 -> 바다부터 봐야한다는 소리다
				if(map[y][src.x] == 1) return INF;	//만약 중간에 다른 섬이 있다면 -> 현재 이 지점과 이지점을 놓을 수 없다
			}
			//여기까지 왔다는 것은 중간에 다른 섬이 없어서 다리를 놓을 수 있다는 뜻이다
			return bottom - top - 1;	//두 섬 간의 다리 길이를 리턴하자
		}
		
		return INF; //두개가 동일 선상에 없을 경우이다
	}
	
	//최소 신장트리를 구성하자
	static int MST() {
		int islandsSize = islands.size();
		PriorityQueue<Edge> edges = new PriorityQueue<>();
		
		boolean[] isConnected = new boolean[islandsSize];
		isConnected[0] = true;	//0번에서 시작할 것이기 때문에 0번은 이미 된거다
		int connectCount = 1;	//몇개나 연결되었는지 세어주자(0번은 이미 연결된거므로 1로 시작한다)
		
		for(int i = 0; i < islandsSize; i++) {
			if(distances[0][i] != INF) {
				//연결이 되어 있으면
				edges.add(new Edge(0, i, distances[0][i]));	//해당 간선을 넣어주자
			}
		}
		
		int resultDistance = 0;		//결과 길이
		Edge curr = null;	//pq에서 빼낸거 받기용
		while(connectCount < islandsSize && !edges.isEmpty()) {
			//연결할게 남아있을때 까지
			curr = edges.poll();
			if(isConnected[curr.dest]) continue;	//이미 여기가 연결되어 있으면 패스
			
			isConnected[curr.dest] = true;			//여기는 연결되었다고 하자
			connectCount++;							//연결된 섬 개수 늘리기
			resultDistance += curr.distance;		//다리의 길이 만큼 더해주기
			for(int i = 0; i < islandsSize; i++) {
				if(distances[curr.dest][i] != INF) {
					//새로 연결된 지점에서 다시 뻗어나갈 수 있으면
					edges.add(new Edge(curr.dest, i, distances[curr.dest][i]));
				}
			}
		}
		
		//모두 제대로 연결 되었을 경우 MST의 길이를
		// 연결이 제대로 안 되었을 경우 -1을 반환
		return connectCount == islandsSize ? resultDistance : -1;
	}
	
	static class Edge implements Comparable<Edge>{
		int src;
		int dest;
		int distance;
		
		public Edge(int src, int dest, int distance) {
			this.src = src;
			this.dest = dest;
			this.distance = distance;
		}
		
		public int compareTo(Edge other) {
			return this.distance - other.distance;
		}
	}
	
	//좌표를 나타내기 위한 데이터
	static class Coord{
		int y;
		int x;
		
		public Coord(int y, int x) {
			this.y = y;
			this.x = x;
		}

		@Override
		public String toString() {
			return String.format("[%d, %d]", this.y, this.x);
		}
	}
	
}
