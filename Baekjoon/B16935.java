import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B16935 {
	static int yLength;		//문제의 N과 동일하다
	static int xLength;		//문제의 M과 동일하다
	static int numCommand;	//문제의 R과 동일하다
	
	static int maxLength; 	//y, x중 긴 것

	static int[][] map;		//숫자가 담긴 맵이다
	static int[][] copy;	//복제된 배열이다

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;

		st = new StringTokenizer(in.readLine(), " ");
		yLength = Integer.parseInt(st.nextToken());
		xLength = Integer.parseInt(st.nextToken());
		numCommand = Integer.parseInt(st.nextToken());
		maxLength = Math.max(yLength, xLength);

		//맵 초기화 시작
		map = new int[maxLength][maxLength];
		copy = new int[maxLength][maxLength];

		for(int y = 0; y < yLength; y++) {
			st = new StringTokenizer(in.readLine());
			for(int x = 0; x < xLength; x++) {
				map[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		//명령어 시작
		st = new StringTokenizer(in.readLine());
		for(int i = 0; i < numCommand; i++) {
			switch(Integer.parseInt(st.nextToken())) {
			case 1:	//배열 상하 반전
				upDown();
				break;
			case 2:	//배열 좌우 반전
				leftRight();
				break;
			case 3:	//시계방향 90도 회전
				rotateMinus();
				break;
			case 4:	//반시계방향 90도 회전
				rotatePlus();
				break;
			case 5:	//4분할 후 시계방향 90도 회전
				quadRotatePlus();
				break;
			case 6:	//4분할 후 반시계방향 90도 회전
				quadRotateMinus();
				break;
			}
		}		
		for(int y = 0; y < yLength; y++) {
			for(int x = 0; x < xLength; x++) {
				sb.append(map[y][x]).append(" ");
			}
			sb.append("\n");
		}

		System.out.println(sb.toString());
	}

	//a의 [y, x]와  b의 [y, x]를 스왑한다
	public static void swap(int ay, int ax, int by, int bx) {
		int tmp = map[ay][ax];
		map[ay][ax] = map[by][bx];
		map[by][bx] = tmp;
	}
	
	//yLength와 xLength를 교환
	public static void swapYX() {
		int tmp = yLength;
		yLength = xLength;
		xLength = tmp;
	}

	//copy배열에 map을 복사
	public static void copy() {
		//원본 배열 복사해놓기
		for(int y = 0; y < yLength; y++) {
			for(int x = 0; x < xLength; x++) {
				copy[y][x] = map[y][x];
			}
		}
	}
	
	//map을 0으로 초기화
	public static void init() {
		for(int y = 0; y < maxLength; y++) {
			for(int x = 0; x < maxLength; x++) {
				map[y][x] = 0;
			}
		}
	}

	//1번 커맨드 -> 배열 상하 반전
	public static void upDown() {
		int halfY = yLength / 2;
		for(int y = 0; y < halfY; y++) {
			for(int x = 0; x < xLength; x++) {
				swap(y, x, yLength-1-y, x);
			}
		}
	}

	//2번 커맨드
	public static void leftRight() {
		int halfX = xLength / 2;
		for(int y = 0; y < yLength; y++) {
			for(int x = 0; x < halfX; x++) {
				swap(y, x, y, xLength - x - 1);
			}
		}
	}

	//3번 커맨드
	public static void rotatePlus() {
		copy();
		init();
		
		swapYX();

		//돌리기
		for(int y = 0; y < yLength; y++) {
			for(int x = 0; x < xLength; x++) {
				map[y][x] = copy[x][yLength-1-y];
			}
		}
		
		
	}

	//4번 커맨드
	public static void rotateMinus() {
		copy();
		init();
		init();
		
		swapYX();

		//돌리기
		for(int y = 0; y < yLength; y++) {
			for(int x = 0; x < xLength; x++) {
				map[y][x] = copy[xLength-1-x][y];
			}
		}
	}

	//5번 커맨드 -> 4분할해서 시계방향 90도 회전
	public static void quadRotatePlus() {
		copy();
		
		int halfX = xLength / 2;
		int halfY = yLength / 2;
		
		//4등분한 공간만큼 돌면서
		for(int y = 0; y < halfY; y++) {
			for(int x = 0; x < halfX; x++) {
				map[y][x] 				= copy[y+halfY][x];			//좌상단 처리하기
				map[y][x+halfX] 		= copy[y][x];				//우상단 처리하기
				map[y+halfY][x] 		= copy[y+halfY][x+halfX];	//좌하단 처리하기
				map[y+halfY][x+halfX] 	= copy[y][x+halfX];			//우하단 처리하기
			}
		}
	}

	//6번 커맨드
	public static void quadRotateMinus() {
		copy();
		int halfX = xLength / 2;
		int halfY = yLength / 2;
		
		
		//4등분한 공간만큼 돌면서
		for(int y = 0; y < halfY; y++) {
			for(int x = 0; x < halfX; x++) {
				map[y][x] 				= copy[y][x+halfX];			//좌상단 처리하기
				map[y][x+halfX] 		= copy[y+halfY][x+halfX];	//우상단 처리하기
				map[y+halfY][x] 		= copy[y][x];				//좌하단 처리하기
				map[y+halfY][x+halfX] 	= copy[y+halfY][x];			//우하단 처리하기
			}
		}
	}

}
