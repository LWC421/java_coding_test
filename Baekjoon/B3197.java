import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class B3197 {

	//4방위 탐색용
	static final int[] dy = {1, 0, -1, 0};
	static final int[] dx = {0, 1, 0, -1};

	static final char BIRD = 'L';
	static final char WATER = '.';
	static final char ICE = 'X';
	
	static int yLength, xLength;

	static int connected[];		//연결 요소 저장
	static char[][] map;
	
	static boolean[][] visited;
	
	static Set<Coord> tmp;
	static Set<Coord> removes;

	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());

		yLength = Integer.parseInt(st.nextToken());
		xLength = Integer.parseInt(st.nextToken());

		Coord bird1 = null;
		Coord bird2 = null;

		map = new char[yLength][xLength];
		visited = new boolean[yLength][xLength];
		
		for(int y = 0; y < yLength; y++) {
			char[] row = in.readLine().toCharArray();
			for(int x = 0; x < xLength; x++) {
				char c = row[x];
				if(c == BIRD) {
					if(bird1 == null) {
						//아직 1번째 백조가 초기화가 안되어있으면
						bird1 = new Coord(y, x);
					} else {
						//2번째 백조 초기화 차례
						bird2 = new Coord(y, x);
					}
					c = WATER;	//백조 자리는 다시 물로 바꾸기
				}
				map[y][x] = c;
			}
		}
		//입력 종료

		connected = new int[yLength*xLength];	//연결 요소 저장
		for(int i = 0, limit=yLength*xLength; i < limit; i++) {
			connected[i] = i;	//자기자신을 가리키도록 하기
		}

		tmp = new HashSet<>();
		removes = new HashSet<>();
		
		int nY, nX;
		for(int y = 0; y < yLength; y++) {
			for(int x = 0; x < xLength; x++) {
				if(map[y][x] == WATER) {
					//물일 경우
					if(visited[y][x]) continue;	//이미 작업한 곳이면 넘기기
					unionMap(y, x);	//BFS를 통해 연결요소 합치는 과정
				}
			}
		}
	

		int time = 0;	//시간 측정
		int bird1_coord = bird1.y * xLength + bird1.x;
		int bird2_coord = bird2.y * xLength + bird2.x;
		
		int fromCoord, toCoord;
		while(find(bird1_coord) != find(bird2_coord)) {
			//같은 집합이 아닐때만 계속 반복
			time++;
			removes.addAll(tmp);	//tmp에서 removes로 옮기고
			tmp.clear();			//tmp는 정리해놓고
			
			for(Coord c: removes) {
				//각각의 가장자리 얼음에 대해 작업
				
				fromCoord = c.y * xLength + c.x;
				
				for(int d = 0; d < 4; d++) {
					nY = c.y + dy[d];
					nX = c.x + dx[d];
					
					if(nY < 0 || nY >= yLength) continue;
					if(nX < 0 || nX >= xLength) continue;
					
					if(map[nY][nX] == ICE) {
						//만약 주변에 얼음이 있으면서
						//set들에 들어가 있지 않으면
						tmp.add(new Coord(nY, nX));		//임시 set에 넣기
					}
				}
			}
			//다음 녹을 얼음에 대해 처리했으면
			
			for(Coord c: removes) {
				map[c.y][c.x] = WATER;	//이제 녹은 것들은 물로 변경
			}
			for(Coord c: removes) {
				//녹이고 난 후 union을 하자
				fromCoord = c.y * xLength + c.x;
				for(int d = 0; d < 4; d++) {
					nY = c.y + dy[d];
					nX = c.x + dx[d];
					
					if(nY < 0 || nY >= yLength) continue;
					if(nX < 0 || nX >= xLength) continue;
					
					if(map[nY][nX] == WATER) {
						toCoord = nY * xLength + nX;
						union(fromCoord, toCoord);
					}
				}
			}
			removes.clear();
		}
		
		System.out.println(time);
	}
	
	static void printMap() {
		for(int y = 0; y < yLength; y++) {
			for(int x = 0; x < xLength; x++) {
				System.out.print(map[y][x]);
			}
			System.out.println();
		}
		
		System.out.println("-----------------------");
	}

	static void unionMap(int y, int x) {
		Queue<Coord> q = new ArrayDeque<>();
		q.add(new Coord(y, x));
		
		int nY, nX;
		Coord curr;
		
		int fromCoord;
		int toCoord;
		while( !q.isEmpty() ) {
			curr = q.poll();
			fromCoord = y * xLength + x;
			visited[curr.y][curr.x] = true;
			
			for(int d = 0; d < 4; d++) {
				nY = curr.y + dy[d];
				nX = curr.x + dx[d];
				
				if(nY < 0 || nY >= yLength) continue;
				if(nX < 0 || nX >= xLength) continue;
				
				if(map[nY][nX] == ICE) {
					//만약 얼음이면 -> 물과 맞닿은 얼음이라는 뜻이다
					tmp.add(new Coord(nY, nX));		//삭제 예정 set에 넣자
				}
				if(map[nY][nX] != WATER) continue;	//물일 경우에만 합치자
				if(visited[nY][nX]) continue;
				
				toCoord = nY * xLength + nX;
				visited[nY][nX] = true;
				union(fromCoord, toCoord);	//합치기
				q.add(new Coord(nY, nX));
			}
		}
	}

	static int find(int a) {
		if(connected[a] == a) {
			return a;
		}

		return connected[a] = find(connected[a]);
	}

	static boolean union(int a, int b) {
		a = find(a);
		b = find(b);

		if(a == b) {
			return false;
		}

		if(a < b) {
			connected[b] = a;
		} else {
			connected[a] = b;
		}
		return true;
	}

	static class Coord{
		int y;
		int x;

		public Coord(int y, int x) {
			this.y = y;
			this.x = x;
		}

		@Override
		public int hashCode() {
			return y * 1501 + x;
		}

		@Override
		public boolean equals(Object obj) {
			return this.hashCode() == obj.hashCode();
		}

		@Override
		public String toString() {
			return String.format("[%d, %d]", this.y, this.x);
		}
	}

}
