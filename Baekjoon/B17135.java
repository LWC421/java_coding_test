import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;


public class B17135 {

	static int[][] combis;					//궁수 배치들의 조합
	static int[] currentCombi = new int[3];	//궁수는 항상 3명이다
	static int currentCombiIndex;			//현재 어느 combi에 넣을건지

	static int yLength, xLength; //격자판 행, 열의 수   [3 ~ 15]

	public static void main(String[] args) throws Exception {
		//진행되는 맵은 N*M
		//각 칸에는 최대 적이 하나
		//N번행바로 아래 모든칸에는 성이 있다

		//성이 있는 칸에 각각 총 궁수 3명을 배치한다
		//궁수는 거리 D이하인 적 중 가장 가까운 적을 공격
		//만약 거리가 동일하면 왼쪽을 노린다
		//같은 적을 여러 궁수가 공격할 수 있다
		//공격받은 적은 게임에서 제외된다
		//궁수의 공격 끝난 후 적이 이동

		//적은 아래로 한 칸 이동
		//적이 성이 있는 칸으로 적이 이동할 경우 게임에서 제외된다

		//모든 적이 격자판에서 제외되면 게임이 끝난다
		// -> 궁수 배치가 제일 중요하다
		// -> 궁수의 공격으로 제거할 수 있는 적의 최대 수를 계산해보자.

		//거리는 |y1 - y2| + |x1 - x2|이다

		//-----------------------------------------------
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		int range;	//궁수의 공격범위  [1 ~ 10]
		int maxAttack = 0;	//공격한 최대 횟수 저장

		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		yLength = Integer.parseInt(st.nextToken());
		xLength = Integer.parseInt(st.nextToken());
		range = Integer.parseInt(st.nextToken());

		//조합 개수 계산 및 조합 초기화
		int numCombi = xLength * (xLength-1) * (xLength-2) / 6;
		combis = new int[numCombi][3];
		currentCombiIndex = 0;

		//맵 정보 입력받기
		List<Enemy> enemies = new LinkedList<>();
		List<Enemy> original = new LinkedList<>();
		for(int y = 0; y < yLength; y++) {
			st = new StringTokenizer(in.readLine());
			for(int x = 0; x < xLength; x++) {
				if(st.nextToken().charAt(0) == '1') {
					//적일 경우
					enemies.add(new Enemy(y, x));
					original.add(new Enemy(y, x));	//매 조합마다 적 정보를 원상복구해야한다
				}
			}
		}

		//yLength에 궁수들이 존재한다
		combi(0, 0);	//궁수 조합하기
		//		System.out.println(Arrays.deepToString(combis));

		for(int[] comb: combis) {
			//조합을 한개씩 가져오면서 시뮬레이션하기

			int currentAttack = 0;	//현재 공격한 횟수
			GoongSoo[] goongsoos = new GoongSoo[3];	//3명의 궁수
			Enemy[] targetEnemy = new Enemy[3];		//한턴당 최대 3명의 적을 공격한다

			for(int i = 0; i < 3; i++) {
				//3명의 궁수들 배치하기
				goongsoos[i] = new GoongSoo(yLength, comb[i], range);
			}

//			for(int turn = 0; turn <= yLength; turn++) {
			while(enemies.size() > 0) {
				//한턴씩 지난다

				//궁수의 공격
				for(int i = 0; i < 3; i++) {
					int minDistance = Integer.MAX_VALUE;	//공격 가능한 가장 가까운 거리 저장
					Enemy minEnemy = null;					//가장 가까운 적 저장

					for(Enemy e: enemies) {
						int distance = goongsoos[i].AttackAvilable(e);	//공격 가능 여부 확인
						if(distance != -1) {
							//해당 적을 공격가능하면
							if(minDistance > distance) {
								//더 짧은 적일 경우
								minDistance = distance;	
								minEnemy = e;
							}
							else if(minDistance == distance) {
								//동일 거리의 적일 경우
								if(e.x < minEnemy.x) {
									//현재 적이 더 왼쪽에 있을 경우 겨냥하는 적을 바꾼다
									minEnemy = e;
								}
							}
						}
					}

					targetEnemy[i] = minEnemy;	//가장가까운 적을 저장 -> 없을 경우 null이 들어온다
				}

				//모든 궁수들이 겨냥하면 -> 적을 공격해서 없앤다
				for(Enemy e: targetEnemy) {
					if( e != null && enemies.contains(e)) {
						//겨냥하고 있는 적이있으면서 해당 적이 사라졌지 않았으면
						enemies.remove(e);	//해당 적을 없애기
						currentAttack++;	//공격횟수 늘리기
					}
				}
				targetEnemy[0] = null;
				targetEnemy[1] = null;
				targetEnemy[2] = null;

				//적들의 전진
				Enemy currentEnemy;
				for(int i = 0; i < enemies.size(); i++) {
					currentEnemy = enemies.get(i);
					currentEnemy.y++;	//적의 전진
					currentEnemy = enemies.get(i);
					if(currentEnemy.y == yLength) {
						//성에 도달한 경우
						enemies.remove(i--);	//삭제하면서 인덱스 줄이기
					}
					
				}
			}

			//모든 턴이 종료 -> 원상복구하자
			enemies.clear();			//남아있는 적 다 지우고
			for(Enemy e: original) {
				//원 상태에서 복사
				enemies.add(new Enemy(e.y, e.x));
			}

			maxAttack = Math.max(currentAttack, maxAttack);	//최대 공격 횟수 갱신해주기
		}

		System.out.println(maxAttack);
	}

	//궁수
	static class GoongSoo{
		int y, x;	//궁수가 위치한 곳
		int range;	//궁수의 사거리

		public GoongSoo(int y, int x, int range) {
			this.y = y;
			this.x = x;
			this.range = range;
		}

		//사정거리안에 들어오면 distance반환
		//사정거리안에 들어오지 않으면 -1반환
		public int AttackAvilable(Enemy e) {
			int distance = Math.abs(this.y - e.y) + Math.abs(this.x - e.x);
			if(range >= distance) {
				//사정거리안에 들어올 경우
				return distance;
			}

			return -1;	//사정거리 안에 들어오지 않을 경우
		}

		@Override
		public String toString() {
			return String.format("[%d, %d] - %d", y, x, range);
		}
	}

	//적
	static class Enemy{
		int y, x;

		public Enemy(int y, int x) {
			this.y = y;
			this.x = x;
		}

		@Override
		public boolean equals(Object o) {
			Enemy e = (Enemy)o;
			if(this.y == e.y && this.x == e.x) {
				return true;	//좌표가 같으면 같은 적이다
			}

			return false;
		}

		@Override
		public String toString() {
			return String.format("[%d, %d]", y, x);
		}
	}

	//궁수들 조합하기
	static void combi(int count, int start) {
		if(count == 3) {
			//궁수 3명을 모두 조합했으면
			for(int i = 0; i < 3; i++) {
				//현재 조합을 복사해서 조합에 넣기
				combis[currentCombiIndex][i] = currentCombi[i];
			}
			currentCombiIndex++;	//다음 조합의 인덱스로 가기
			return;
		}

		for(int i = start; i < xLength; i++) {
			//조합해보기
			currentCombi[count] = i;
			combi(count+1, i+1);	//다음 조합
		}
	}
}
