import java.io.*;
import java.util.*;


public class SWEA2105 {
	// N*N맵 [4 ~ 20]
	// 각 숫자는 디저트의 종류
	
	// 대각선 방향으로 움직일 수 있다
	// 사각형을 그려 처음으로 돌아와야 한다
	
	// 같은 숫자를 만나면 안된다
	// 하나의 숫자만 만나는 것도 안된다
	// 사용했던 거는 또 사용 안된다
	
	// 시작점은 아무곳이나 된다
	
	//4대각 이동용
	final static int[] dy = {1, 1, -1, -1};
	final static int[] dx = {1, -1, -1, 1};

	static int mapLength;		//맵의 크기
	static int[][] map;			//맵
	static Set<Integer> used;	//사용된 숫자 저장용
	
	public static void main(String[] args) throws Exception {
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;
		used = new HashSet<>();
		int max = -1;
		
		int TC = Integer.parseInt(in.readLine());
		
		for(int test_case = 1; test_case <= TC; test_case++) {
			//테스트 케이스 시작
			mapLength = Integer.parseInt(in.readLine());	//맵 크기
			map = new int[mapLength][mapLength];		//맵 초기화
			max = -1;
//			used.clear();								//사용한 숫자들 초기화
			
			for(int y = 0; y < mapLength; y++) {
				st = new StringTokenizer(in.readLine(), " ");	//줄마다 입력
				for(int x = 0; x < mapLength; x++) {
					map[y][x] = Integer.parseInt(st.nextToken());
				}
			}
			//입력 종료
			
			//각 칸에 대해서 검사를 해보자
			for(int y = 0; y < mapLength; y++) {
				for(int x = 0; x < mapLength; x++) {
					//직사각형의 두 변을 바꾸면서 이동한다
					for(int rb = 1; rb < mapLength; rb++) {
						for(int lb = 1; lb < mapLength; lb++) {
							if(goRect(y, x, rb, lb)) {
								//성공적으로 사각형을 그렸으면
//								System.out.printf("[%d, %d], %d, %d\n", y, x, rb, lb);

								if(max < used.size()) {
									//더 큰 값이였으면 변경해주기
									max = used.size();
								}
							}
							//어쨌든 끝났으므로 used를 초기화
							used.clear();
						}
					}
				}
			}
			
			
			sb.append(String.format("#%d %d\n", test_case, max));
		}
		
		System.out.println(sb.toString());
	}

	/**
	 * 
	 * @param initY 첫 시작 Y
	 * @param initX 첫 시작 X
	 * @param rb 오른쪽 밑으로 가는 변의 길이, (1 <= )
	 * @param lb 왼쪽 밑으로로 가는 변의 길이, (1 <= )
	 * @return	성공할 경우 true, 실패 할 경우 false
	 */
	public static boolean goRect(int initY, int initX, int rb, int lb) {
		int currY = initY;
		int currX = initX;	//첫 위치들 잡아주고
		
		//오른쪽 밑으로 이동
		for(int i = 0; i < rb; i++) {
			currY++;
			currX++;
			if(currY >= mapLength || currX >= mapLength || currY < 0 || currX < 0) {
				return false;
			}
			
			if( !check(currY, currX)) {
				//해당 칸에서 체크하고 없었으면 숫자를 넣어주자
				return false;	//겹쳤으면 false를 반환
			}
		}
		
		//왼쪽 밑
		for(int i = 0; i < lb; i++) {
			currY++;
			currX--;
			if(currY >= mapLength || currX >= mapLength || currY < 0 || currX < 0) {
				return false;
			}
			
			if( !check(currY, currX)) {
				//해당 칸에서 체크하고 없었으면 숫자를 넣어주자
				return false;	//겹쳤으면 false를 반환
			}
		}
		
		//왼쪽 위
		for(int i = 0; i < rb; i++) {
			currY--;
			currX--;
			if(currY >= mapLength || currX >= mapLength || currY < 0 || currX < 0) {
				return false;
			}
			
			if( !check(currY, currX)) {
				//해당 칸에서 체크하고 없었으면 숫자를 넣어주자
				return false;	//겹쳤으면 false를 반환
			}
		}
		
		//오른쪽 위
		for(int i = 0; i < lb; i++) {
			currY--;
			currX++;
			if(currY >= mapLength || currX >= mapLength || currY < 0 || currX < 0) {
				return false;
			}
			
			if( !check(currY, currX)) {
				//해당 칸에서 체크하고 없었으면 숫자를 넣어주자
				return false;	//겹쳤으면 false를 반환
			}
		}
		
		return true;
	}
	
	public static boolean check(int y, int x) {
		if(used.contains(map[y][x])) {
			//만약 겹치면
			return false;
		}
		
		used.add(map[y][x]);	//처음 보는 숫자면 넣어주기
		return true;
	}
}
