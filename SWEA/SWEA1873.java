import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA1873 {

	static int yLength, xLength;	//맵의 크기
	static char[][] map;			//맵
	static int numCommand;			//명령어 개수
	static char[] commands;			//커맨드들
	static int curY, curX;			//현재 전차의 위치
	static int curDy, curDx;		//현재 보고있는 방향
	
	public static void main(String[] args) throws Exception{
		//	문자	의미
		//	.	평지(전차가 들어갈 수 있다.)
		//	*	벽돌로 만들어진 벽
		//	#	강철로 만들어진 벽
		//	-	물(전차는 들어갈 수 없다.)
		//	^	위쪽을 바라보는 전차(아래는 평지이다.)
		//	v	아래쪽을 바라보는 전차(아래는 평지이다.)
		//	<	왼쪽을 바라보는 전차(아래는 평지이다.)
		//	>	오른쪽을 바라보는 전차(아래는 평지이다.)
		BufferedReader in =new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader in =new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));	
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;
		
		int T = Integer.parseInt(in.readLine());
		
		
		for(int test_case = 1; test_case <= T; test_case++) {
			st = new StringTokenizer(in.readLine());
			yLength = Integer.parseInt(st.nextToken());
			xLength = Integer.parseInt(st.nextToken());
			
			//맵 초기화 및 입력 받기
			map = new char[yLength+2][xLength+2];
			
			String s;
			char c;
			for(int y = 1; y <= yLength; y++) {
				s = in.readLine();
				for(int x = 1; x <= xLength; x++) {
					c = s.charAt(x-1);
					map[y][x] = c;
					if(c == '<') {
						curY = y;
						curX = x;
						curDy = 0;
						curDx = -1;
						map[y][x] = '.';	//평지로 바꾸기
					}
					else if(c == '>') {
						curY = y;
						curX = x;
						curDy = 0;
						curDx = 1;
						map[y][x] = '.';	//평지로 바꾸기
					}
					else if(c == '^') {
						curY = y;
						curX = x;
						curDy = -1;
						curDx = 0;
						map[y][x] = '.';	//평지로 바꾸기
					}
					else if(c == 'v') {
						curY = y;
						curX = x;
						curDy = 1;
						curDx = 0;
						map[y][x] = '.';	//평지로 바꾸기
					}
				}
			}
			
			//맵 밖은 x로 두르기
			for(int y = 0; y <= yLength+1; y++) {
				map[y][0] 		  = 'x';
				map[y][xLength+1] = 'x';
			}
			for(int x = 0; x <= xLength+1; x++) {
				map[0][x] 		  = 'x';
				map[yLength+1][x] = 'x';
			}
			
			//명령어 입력 받기
			numCommand = Integer.parseInt(in.readLine());
			commands = new char[numCommand];
			s = in.readLine();
			for(int i = 0; i < numCommand; i++) {
				commands[i] = s.charAt(i);
			}
			//입력 종료
			
			//커맨드 시작
			for(char command: commands) {
				switch(command) {
				case 'U':{	//위
					curDy = -1;
					curDx = 0;
					move();
					break;
				}
				case 'D':{	//아래
					curDy = 1;
					curDx = 0;
					move();
					break;
				}
				case 'L':{	//왼쪽
					curDy = 0;
					curDx = -1;
					move();
					break;
				}
				case 'R':{	//오른쪽
					curDy = 0;
					curDx = 1;
					move();
					break;
				}
				case 'S':{	//쏘기
					shoot();
					break;
				}
				}
			}
			
			//커맨드 종료
			if(curDy == -1) {
				//위 보고 있는 경우
				map[curY][curX] = '^';
			}
			else if(curDy == 1) {
				//밑을 보고 있는 경우
				map[curY][curX] = 'v';
			}
			else if(curDx == -1) {
				//왼쪽을 보고 있는 경우
				map[curY][curX] = '<';
			}
			else if(curDx == 1) {
				//오른쪽을 보고 있는 경우
				map[curY][curX] = '>';
			}
			
			sb.append("#").append(test_case).append(" ");
			for(int y = 1; y <= yLength; y++) {
				for(int x = 1; x <= xLength; x++) {
					sb.append(map[y][x]);
				}
				sb.append("\n");
			}
		}
		
		System.out.println(sb.toString());
		
	}
	
	public static void move() {	
		int nextY, nextX;	//예상 다음 위치
		nextY = curY + curDy;
		nextX = curX + curDx;
		if(map[nextY][nextX] == '.') {	//다음 위치를 갈 수 있으면
			curY = nextY;
			curX = nextX;
		}
	}
	
	public static void shoot() {
		int y, x;	//포탄의 위치
		y = curY;
		x = curX;
		while(map[y][x] != 'x' && 	//맵 밖이면
				map[y][x] != '*' && //벽돌이면
				map[y][x] != '#') { //강철이면
			y = y + curDy;
			x = x + curDx;
		}
		
		if(map[y][x] == '*') {
			//부술 수 있는 벽돌이면
			map[y][x] = '.';	//평지로 바꾸기
		}
	}
}
