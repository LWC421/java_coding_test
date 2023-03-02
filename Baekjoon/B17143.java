import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.StringTokenizer;

public class B17143 {
	public static void main(String[] args) throws Exception{
		// R * C격자판 있다 -> 1부터 시작한다
		// 칸마다 상어가 1이거나 0이거나
		// 각 상어는 크기, 속도를 가진다
		// 처음에는 [0, 0]에서 시작 -> 가장 오른쪽으로 이동하면 이동 멈춘다
		// 1. 오른쪽으로 한칸 이동
		// 2. 이동한 열에서 땅과 제일 가까운 상어를 잡는다 -> 잡은 상어는 사라진다
		// 3. 상어가 이동한다
		// 1초마다 주어진 단위칸만큼 이동 -> 경계를 넘는 경우에는 방향을 바꿔서 이동한다
		// 상어가 이동을 마치면 한 칸에 상어가 두 마리 있을 수 있다 -> 크기가 큰 상어가 나머지 상어들을 잡아먹는다
		// 최종적으로 잡은 상어의 크기의 합을 구하자
		
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int yLength, xLength;	//R, C, 맵 크기 [2 ~ 100]
		int numShark;			//M, 상어 개수 [0 ~ R*C]
		
		//1번재 줄 입력
		StringTokenizer st = new StringTokenizer(in.readLine());
		yLength = Integer.parseInt(st.nextToken());
		xLength = Integer.parseInt(st.nextToken());
		numShark = Integer.parseInt(st.nextToken());
		
		Shark[][] map = new Shark[yLength+1][xLength+1];					//상어 위치 저장용
		PriorityQueue<Shark>[][] copyMap = new PriorityQueue[yLength+1][xLength+1];		//상어 임시 저장용
		for(int y = 1; y <= yLength; y++) {
			for(int x = 1; x <= xLength; x++) {
				copyMap[y][x] = new PriorityQueue<>();	//임시맵의 PQ들 초기화
			}
		}
		
		
		
		//2번째 줄부터 입력
		int y, x;		//r, c, 상어의 위치
		int speed;		//s, 상어의 속도
		int direction;	//d, 이동 방향
		int scale;		//z, 크기
		
		int dy = 0;
		int dx = 0;		//이동 방향에 따른 변위값
		
		Set<Coord> sharkPositions = new HashSet<>();	//상어들 위치 저장용
		Set<Coord> copyPositions = new HashSet<>();		//상어들 위치 임시 저장용
		
		for(int i = 0; i < numShark; i++) {
			dy = 0;
			dx = 0;
			
			st = new StringTokenizer(in.readLine());
			y = Integer.parseInt(st.nextToken());
			x = Integer.parseInt(st.nextToken());
			speed = Integer.parseInt(st.nextToken());
			direction = Integer.parseInt(st.nextToken());
			scale = Integer.parseInt(st.nextToken());
			
			
			
			if(direction == 1) {
				dy = -1;//위
				
				speed = speed % ( (yLength-1) * 2);	//일단 위아래 한번씩 박고도 다시 돌아오는지 확인
				if(speed >= yLength) {
					speed = (yLength-1) * 2 - speed;
					dy = 1;
				}
			}
			else if(direction == 2) {
				dy = 1;	//아래
				
				speed = speed % ((yLength-1) *2);
				if(speed >= yLength) {
					speed = (yLength-1) * 2 - speed;
					dy = -1;
				}
			}
			else if(direction == 3) {
				dx = 1;	//오른쪽
				
				speed = speed % ((xLength-1) * 2);
				if(speed >= xLength) {
					speed = (xLength-1) * 2 - speed;
					dx = -1;
				}
			}
			else if(direction == 4) {
				dx = -1;//왼쪽
				speed = speed % ((xLength-1) *2);
				if(speed >= xLength) {
					speed = (xLength-1) * 2 - speed;
					dx = 1;
				}
			}
			
			
			map[y][x] = new Shark(dy * speed, dx * speed, scale);	//해당 칸에 상어넣기
			sharkPositions.add(new Coord(y, x));	//상어 위치 넣기
		}
		
		//입력 종료
		
		int currentPosition = 0;	//낚시왕의 위치
		int catchCount = 0;			//낚시왕이 잡은 상어 크기의 합
		Shark currentShark = null;	//맵에서 빼낸 상어 저장용
		int nextY, nextX;			//상어의 다음 위치
		
		while( currentPosition < xLength) {
			currentPosition++;	//이동
			
			//땅과 제일 가까운 상어 잡기 수행
			for(int curY = 1; curY <= yLength; curY++) {
				if( map[curY][currentPosition] != null) {
					//해당칸에 상어가 있으면 -> 위에서 부터 본다는 것은 땅에서 가까운 애가 제일 먼저 잡힌다는 뜻
					catchCount += map[curY][currentPosition].scale;	//해당 상어의 크기만큼 증가
					map[curY][currentPosition] = null;	//해당 상어는 이제 없다
					sharkPositions.remove(new Coord(curY, currentPosition));	//해당 위치에는 상어 없다고 표시
					break;	//잡았으므로 종료
				}
			}
			
			//상어의 이동차례
			for(Coord coord : sharkPositions) {
				//상어가 있는 곳만 보기위해 Set에서 위치들을 가져와서
				currentShark = map[coord.y][coord.x];	//맵에서 상어 정보를 가져와서
				
				//다음 위치를 구해서
				nextY = coord.y + currentShark.dy;
				nextX = coord.x + currentShark.dx;
				if(nextY <= 0) {
					//위로 팅겨져 나가는 경우 -> 아래로 꺾어야 한다
					nextY = Math.abs((coord.y-1) + currentShark.dy) + 1;
					currentShark.changeDirection();	//방향 바꾸기
				}
				else if(nextY > yLength) {
					//아래로 팅겨져 나가는 경우 -> 위로 꺾어야 한다
					nextY = 2*yLength - coord.y - currentShark.dy;
					currentShark.changeDirection();
				}
				if(nextX <= 0) {
					//왼쪽으로 팅겨져 나가는 경우 -> 오른쪽으로 꺾어야 한다
					nextX = Math.abs((coord.x-1) + currentShark.dx) + 1;
					currentShark.changeDirection();
				}
				else if(nextX > xLength) {
					//오른쪽으로 팅겨져 나가는 경우 -> 좌로 꺾어야 한다
					nextX = 2*xLength - coord.x - currentShark.dx;
					currentShark.changeDirection();
				}
				
				
				//상어 이동한거 넣어주기 -> 일단 임시맵에다가 넣기
				copyMap[nextY][nextX].add(currentShark);
				copyPositions.add(new Coord(nextY, nextX));	//다음 위치 넣기
			}
			
			//상어의 임시 이동이 모두 끝나면 -> 임시 저장한거 원래 맵으로 가져오기
			for(int curY = 1; curY <= yLength; curY++) {
				for(int curX = 1; curX <= xLength; curX++) {
					if(copyMap[curY][curX].size() > 0) {
						//해당 위치에 상어가 있으면
						map[curY][curX] = copyMap[curY][curX].poll();	//뽑은거 가져오기 -> 크기가 가장 큰 상어만 뽑혀져 나온다
					}
					else {
						//해당 위치에 상어가 없으면
						map[curY][curX] = null;		//상어가 없다고 표시
					}
					copyMap[curY][curX].clear();	//해당 위치의 임시맵 사용 다했으므로 비우기
				}
			}
			
			sharkPositions.clear();		//set을 한번 초기화 하고
			for(Coord coord: copyPositions) {
				sharkPositions.add(coord);	//원래 position맵에 넣기
			}
			copyPositions.clear();	//임시 위치Set 초기화
		}
		
		System.out.println(catchCount);
	}
	
	//좌표 저장용
	static class Coord{
		int y, x;
		
		public Coord(int y, int x) {
			this.y = y;
			this.x = x;
		}

		@Override
		public int hashCode() {
			return y*1000 + x;
		}

		@Override
		public boolean equals(Object obj) {
			Coord o = (Coord) obj;
			
			if(this.y == o.y && this.x == o.x) {
				return true;	//좌표 기준으로 같다 체크
			}
			return false;
		}

		@Override
		public String toString() {
			return String.format("[%d, %d]", y, x);
		}
	}
	
	//상어 정보
	static class Shark implements Comparable<Shark>{
		int dy, dx;	//초당 이동하는 칸
		int scale;	//크기
		
		//생성자
		public Shark(int dy, int dx, int scale) {
			this.dy = dy;
			this.dx = dx;
			this.scale = scale;
		}
		
		//방향바꾸기
		public void changeDirection() {
			this.dy = -dy;
			this.dx = -dx;
		}
		
		//비교자 -> 큰 값이 먼저 오도록
		public int compareTo(Shark o) {
			return -Integer.compare(this.scale, o.scale);
		}

		@Override
		public String toString() {
			return String.format("[%d, %d]", dy, dx);
		}
	}
}
