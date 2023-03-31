import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class B1194 {
	
	//4방위 탐색용이다
	public final static int[] dy = {0, 1, 0, -1};
	public final static int[] dx = {1, 0, -1, 0};
	
	public final static int MAX_VALUE = 50*50*2 + 1;	//50*50의 칸을 갔다가 다시 돌아왔을 경우도 있으므로 상한을 높게잡자
	
	public static void main(String[] args) throws Exception{
		// . : 빈칸
		// # : 벽, 절대 이동 불가
		// a, b, c, d, e, f : 이동가능, 처음 들어가면 열쇠를 집는다
		// A, B, C, D, E, F : 문, 열쇠가 있어야 이동 가능
		// O : 빈곳이며 민식이가 있는 곳
		// 1 : 탈출구
		
		// 맵은 50 * 50이 최대크기
		// 4방위 탐색
		// 이동의 최소 횟수
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		int yLength = Integer.parseInt(st.nextToken()); //N, 세로 크기
		int xLength = Integer.parseInt(st.nextToken()); //M, 가로 크기
		
		int startY = -1;
		int startX = -1;	//처음 시작 위치 받기
		
		char[][] map = new char[yLength+2][xLength+2];	//맵 정보
		for(int y = 1; y <= yLength; y++) {
			char[] line = in.readLine().toCharArray();
			for(int x = 1; x <= xLength; x++) {
				map[y][x] = line[x-1];
				if(line[x-1] == '0') {
					startY = y;
					startX = x;			//시작지점 잡아주기
					map[y][x] = '.'	;	//그리고나서 빈칸으로 바꿔주자
				}
			}
		}
		//맵 입력 종료
		
		//벽치기
		int yLimit = yLength+1;
		int xLimit = xLength+1;
		for(int y = 0; y <= yLimit; y++) {
			map[y][0] = '#';
			map[y][xLimit] = '#';
		}
		for(int x = 0; x <= xLimit; x++) {
			map[0][x] = '#';
			map[yLimit][x] = '#';
		}
		//벽치기 종료

//		for(char[] row: map) {
//			System.out.println(Arrays.toString(row));
//		}
		
		// 이동 가능한 조건
		// 동일 키를 가지고 있으나 이동 횟수가 적은 경우 -> BFS에서는 고려안해도 된다
		// 그전에 간 것과 비교해서 다른 키를 가지고 있을 경우
		
		MapData[][] visitedMap = new MapData[yLength+2][xLength+2];	//각 위치에서 어떤 열쇠를 가지고 어떤 문 정보를 가지고 왔는지 확인
		
		for(int y = 1; y <= yLength; y++) {
			for(int x = 1; x <= xLength; x++) {
				visitedMap[y][x] = new MapData();
			}
		}
		
		Queue<MoveData> q = new ArrayDeque<>();
		MoveData startData = new MoveData(startY, startX, 0, 0, 0);	//처음 위치, 키 없음, 열었는 문 없음, 이동횟수 0로 설정
		q.add(startData);	
		visitedMap[startY][startX].addData(startData);
		
		MoveData cur = null;	//큐에서 빼낸거용
		int nY, nX;
		char nextChar;
		while( !q.isEmpty() ) {
			cur = q.poll();		//큐에서 빼내서
			
			for(int d = 0; d < 4; d++) {
				nY = cur.y + dy[d];
				nX = cur.x + dx[d];
				nextChar = map[nY][nX];			//다음 칸의 정보를 받아오자
				
				if(nextChar == '#') continue;	//벽이면 패스
				if(nextChar == '1') {
					//만약 다음 가는 곳이 탈출구 이면
					System.out.println(cur.moveCount + 1);
					return;	//종료
				}
				
				//일단 다음 위치를 위해 저장할 데이터를 생성
				MoveData nextData = new MoveData(nY, nX, cur.key, cur.door, cur.moveCount + 1);
				
				if( (nextChar - 'A') >= 0 && (nextChar - 'A') < 6 ) {
					//다음 위치가 문일 경우
					if( !cur.hasKey(nextChar) ) {
						//해당 열쇠를 가지고 있지 않으면 -> 갈 수 없다
						continue;
					}
					nextData.setDoor(nextChar);		//해당 문을 열었다고 표시
				}
				
				
				if( (nextChar - 'a') >= 0 && (nextChar - 'a') < 6) {
					//열쇠이면 -> 열쇠를 정보에 넣어주자
					nextData.setKey(nextChar);
				}

				if(visitedMap[nY][nX].duplicated(nextData)) {
					//만약 중복된다면 -> 갈 필요 없다
					continue;
				}

				visitedMap[nY][nX].addData(nextData);	//해당 정보에 대해 방문 배열에 넣어주기
				
				//해당 위치 정보를 큐에 넣자
				q.add(nextData);
			}
		}

		//큐에서 종료가 안 되었다 -> 탈출구를 못 찾았다는 뜻이다
		System.out.println(-1);
	}
	
	public static class KeyDoor{
		int key;		//키 정보
		int door;		//문 정보
		
		public KeyDoor(int key, int door) {
			this.key = key;
			this.door = door;
		}

		@Override
		public int hashCode() {
			return (this.key << 6) + this.door;	//key정보와 문 정보로 hashCode를 설정
		}

		@Override
		public boolean equals(Object obj) {
			KeyDoor other = (KeyDoor)obj;
			
			return this.hashCode() == other.hashCode();	//그냥 hashCode기준으로 비교하기
		}
	}
	
	public static class MapData{
		Set<KeyDoor> keyDoors;		//현재 위치에서 왔을 때 가지고 있던 키 정보들
		
		
		public MapData() {
			this.keyDoors = new HashSet<>();
		}
		
		public void addData(MoveData data) {
			this.keyDoors.add(new KeyDoor(data.key, data.door));				//해당 칸에 해당 키 정보는 왔었다고 표시
		}
		
		public boolean duplicated(MoveData data) {
			KeyDoor keyDoor = new KeyDoor(data.key, data.door);
			return this.keyDoors.contains(keyDoor);	//여기서 contains이라는 뜻은 해당정보가 이미 왔다는 것이다 -> 진행하면 안된다
		}
	}
	
	public static class MoveData{
		int y;
		int x;
		int key;		//'a'키를 가지고 있을 경우 000 001이 비트 마스킹 된다
		int door;		//'A'문을 땄을 경우 000 0001이 비트 마스킹 된다
		int moveCount;	//이동 횟수
		
		public MoveData(int y, int x, int key, int door, int moveCount) {
			this.y = y;
			this.x = x;
			this.moveCount = moveCount;
			this.door = door;
			this.key = key;
		}
		
		//해당 열쇠를 가지고 있는지 반환
		public boolean hasKey(char target) {
			int index = target - 'a';	//0 ~ 5으로 바꾸기 위해 'a'만큼 빼주자
			
			return (this.key & (1 << index)) != 0;	//해당 키를 가지고 있는지 확인
		}
		
		//해당 열쇠를 넣어주기
		public void setKey(char target) {
			int index = target - 'a';	//0 ~ 5으로 바꾸기 위해 'a'만큼 빼주자
			
			this.key = this.key | (1 << index);	//해당 키를 넣어주자
		}
		
		//해당 문은 열었다고 표시
		public void setDoor(char target) {
			int index = target - 'A';	//0 ~ 5로 바꾸기 위해 'A'만큼 빼준다
			
			this.door = this.door | (1 << index);
		}
	}
}
