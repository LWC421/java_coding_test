import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class CT_battle_ground {
	
	static MapData[][] map;
	static Player[] players;	//사람 관리
	
	//상 우 하 좌 순을 의미한다
	final static int[] dy = {-1, 0, 1, 0};
	final static int[] dx = {0, 1, 0, -1};
	
	public static void main(String[] args) throws Exception{
		// n*n맵에서 진행
		// 각 칸에는 무기 있을 수 있다
		// 초기 플레이어들은 무기 없는 칸에 위치한다
		// 플레이어마다 모두 다른 능력치를 가진다
		
		// 각 칸마다 플레이어는 능력치, 무기는 공격력을 의미
		
		// 1. 플레이어마다 본인이 향하는 방향으로 한 칸 이동
		//		벗어나는 경우 정반대 방향으로 방향을 바꾸어 1칸 이동
		
		// 2-1. 이동한 방향에 플에이가 없다면 총이 있는지 확인
		// 		있을 경우 총 획득 -> 더쎈 총이면 획득한 후 가지고 있던 총들은 격자에 둔다
		// 2-2. 플레이어가 있을 경우 싸운다
		//		초기능력치 + 소유한 총의 공격력의 합을 비교 
		//			더 큰 플레이어가 이긴다
		//			같을 경우 초기 능력치가 큰 플레이어가 이긴다
		//			이긴 사람은 (초기 능력치 + 총의 공격력의 합)의 차이 만큼을 포인트로 획득
		// 2-2-2. 진 플레이어는 총을 해당 격자에 내려놓고
		//			원래 가고 있던 방향대로 한 칸 이동
		//			이동 하려는 칸에 다른 플에이어 or 범위 밖인 경우 오른쪽으로 90도씩 회전하며 빈 칸이 보이면 그곳으로 이동
		// 2-2-3. 이긴 플레이어는 승리한 칸에서 가장 쎈 총 하나를 가지고 나머지는 격자에 내려 놓는다
		
		// 1 ~ n번 플레이어가 순차적으로 한번씩 진행하며 라운드를 진행
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		int mapLength = Integer.parseInt(st.nextToken());		//n, 맵 크기
		int numPlayer = Integer.parseInt(st.nextToken());		//m, 플에이어의 수
		int numRound = Integer.parseInt(st.nextToken());		//k, 라운드의 수
		
		map = new MapData[mapLength+2][mapLength+2];			//+2로 초기화
		players = new Player[numPlayer];
		
		int num;
		for(int y = 1; y <= mapLength; y++) {
			st = new StringTokenizer(in.readLine());
			for(int x = 1; x <= mapLength; x++) {
				map[y][x] = new MapData();
				num = Integer.parseInt(st.nextToken());
				if(num != 0) map[y][x].addWeapon(num);		//빈칸이 아니면 -> 총의 공격력이므로 해당 칸에 넣자
			}
		}
		//맵 입력 종료
		
		int y, x, dir, init;
		for(int i = 0; i < numPlayer; i++) {
			st = new StringTokenizer(in.readLine());
			y = Integer.parseInt(st.nextToken());
			x = Integer.parseInt(st.nextToken());
			dir = Integer.parseInt(st.nextToken());
			init = Integer.parseInt(st.nextToken());
			Player player = new Player(i, y, x, dir, init);
			map[y][x].player = player;	//해당 칸에 사람을 넣어주자
			players[i] = player;		//사람 관리에도 넣어주자
		}
		//모든 입력 종료
		
		for(int round = 0; round < numRound; round++) {
			//라운드 만큼
			for(int i = 0; i < numPlayer; i++) {
				players[i].go();	//행위를 해라
			}
		}
		StringBuilder sb = new StringBuilder("");
		for(int i = 0; i < numPlayer; i++) {
			sb.append(players[i].point).append(" ");
		}
		System.out.println(sb.toString());
	}
	
	static class Player{
		int id;
		int y;
		int x;
		int dir;			//가고 있는 방향(0 -> 위, 1 -> 우, 2 -> 하, 3 -> 좌)
		int initPower;		//초기 능력치
		int weaponPower;	//무기의 공격력
		int point;		//포인트
		
		public Player(int id, int y, int x, int dir, int initPower) {
			this.id = id;
			this.y = y;
			this.x = x;
			this.dir = dir;
			this.initPower = initPower;
			this.weaponPower = 0;		//처음에는 무기를 들고 있지 않다
			this.point = 0;				//처음에는 0점이다
		}
		
		//전진하자
		public void go() {
			int nY, nX;		//다음 예상 칸
			nY = this.y + dy[this.dir];
			nX = this.x + dx[this.dir];
			
			if(map[nY][nX] == null) {
				//벽일 경우
				this.dir = (this.dir + 2) % 4;		//반대 방향으로 돌기
				nY = this.y + dy[this.dir];
				nX = this.x + dx[this.dir];	//재계산
			}
			
			if(map[nY][nX].player != null) {
				//해당 칸에 누군가가 있으면
				map[this.y][this.x].player = null;		//원래 있던 곳은 없던거 처리
				this.y = nY;
				this.x = nX;
				this.fight(map[this.y][this.x].player);	//그사람이랑 싸우자
			}
			else {
				//누군가가 없으면
				map[this.y][this.x].player = null;	//현재 있는 곳은 없다고 표시
				this.y = nY;
				this.x = nX;
				map[this.y][this.x].player = this;	//내가 있는 곳 표시
				if( !map[this.y][this.x].weapons.isEmpty() ) {
					//해당 칸에 총이 있으면
					map[this.y][this.x].weapons.add(this.weaponPower);		//내꺼 넣고
					this.weaponPower = map[this.y][this.x].weapons.poll();	//가장 쎈 거 빼자
				}
			}
		}
		
		public void fight(Player other) {
			int myPower = this.initPower + this.weaponPower;
			int otherPower = other.initPower + other.weaponPower;
			int diff = myPower - otherPower;
			if(diff > 0) {
				//내가 이기는 경우
//				System.out.printf("%d is win(sum) from %d : %d vs %d\n", this.id, other.id, myPower, otherPower);
				other.lose();
				this.win();
				this.point += diff;
			}
			else if(diff < 0) {
				this.lose();
				other.win();
				other.point -= diff;
				//내가 지는 경우
			} else { //합한 공격력이 같은 경우 초기능력치로 비교
				if(this.initPower > other.initPower) {
					//초기 능력치로 비교해서 이길 경우
					other.lose();
					this.win();
					this.point += diff;
				} 
				else {
					//초기 능력치로 비교해서 질 경우
					this.lose();
					other.win();
					other.point -= diff;
				}
			}
		}
		
		public void win() {
			//이긴 사람은 해당칸에 있으면 된다
			if( !map[this.y][this.x].weapons.isEmpty() ) {
				//총이 있으면 -> 가장 높은 총 가져오기
				map[this.y][this.x].weapons.add(this.weaponPower);
				this.weaponPower = map[this.y][this.x].weapons.poll();
			}
			map[this.y][this.x].player = this;	//나는 여기 있다고 표시
		}
		
		public void lose() {
			map[this.y][this.x].addWeapon(this.weaponPower);	//내가 가진거는 내려놓고
			this.weaponPower = 0;
			
			int nY, nX;
			nY = this.y + dy[this.dir];
			nX = this.x + dx[this.dir];
			while(map[nY][nX] == null || map[nY][nX].player != null) {
				//벽인 경우나 플레이어가 있는 경우
				this.dir = (this.dir + 1) % 4;
				nY = this.y + dy[this.dir];
				nX = this.x + dx[this.dir];
			}
			
			//그 칸으로 이동
			map[this.y][this.x].player = null;
			this.y = nY;
			this.x = nX;
			map[this.y][this.x].player = this;
			if( !map[this.y][this.x].weapons.isEmpty() ) {
				//무기가 있으면 -> 꺼내쓰자
				this.weaponPower = map[this.y][this.x].weapons.poll();
			}
		}
	}
	
	static class MapData{
		PriorityQueue<Integer> weapons;	//해당 칸에 존재하는 총들
		Player player;					//해당 칸에 존재하는 player
		
		public MapData() {
			this.weapons = new PriorityQueue<>((a, b) ->  {
				return b - a;
			});
			this.player = null;
		}
		
		//해당 칸에 총을 집어넣는다
		public void addWeapon(int weaponPower) {
			this.weapons.add(weaponPower);
		}
	}
}
