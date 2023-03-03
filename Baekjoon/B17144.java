import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class B17144 {
	
	public static void main(String[] args) throws Exception{
		//R*C의 격자판
		//공기청정기는 항상 1번 열 -> 2행을 차지
		
		/*
		 1. 미세먼지 확산 -> 각 미세먼지는 인접 네 방향으로 확산
		 	공기청정기가 있거나 칸이 없으면 확산X
		 	확산양은 미세먼지크기 / 5이다 -> 소수점 절삭
		 	기존 미세먼지는 확산된양만큼 줄어든다
		 2. 공기청정기 작동 -> 바람 나온다
		 	공기청정기의 위쪽 바람은 반시계
		 	공기청정기의 아래쪽 바람은 시계방향
		 	바람 불면 미세먼지가 바람의 방향대로 한칸씩 이동
		 	공기청정기로 들어가면 미세먼지는 모두 정화된다
		*/
		
		// T초가 지난 후 남아있는 미세먼지의 양은
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		int yLength, xLength;//R, C, [6 ~ 50]
		int timeLimit;		//T, 몇 초 동안 동작할건지, [1 ~ 1000]
		yLength = Integer.parseInt(st.nextToken());
		xLength = Integer.parseInt(st.nextToken());
		timeLimit = Integer.parseInt(st.nextToken());
		
		//맵 입력받기
		int[][] map = new int[yLength+2][xLength+2];
		int[][] copyMap = new int[yLength+2][xLength+2];	//임시 저장용
		int number;
		
		//공기청정기의 위, 아래 좌표 저장(x값만 저장하면 된다)
		int top = -999;
		int bottom = -999;
		
		for(int y = 1; y <= yLength; y++) {
			st = new StringTokenizer(in.readLine());
			for(int x = 1; x <= xLength; x++) {
				number = Integer.parseInt(st.nextToken());
				map[y][x] = number;
				if(number == -1) {
					//공기청정기이면 -> 어차피 공기청정기의 밑부분이 나중에 나온다
					top = y-1;
					bottom = y;
				}
			}
		}
		//맵 입력 종료
		
		//위아래 벽치기
		for(int y = 0; y <= yLength+1; y++) {
			map[y][0] 		  = -1;
			map[y][xLength+1] = -1;
		}
		for(int x = 0; x <= xLength+1; x++) {
			map[0][x] 		  = -1;
			map[yLength+1][x] = -1;
		}
		//전처리 종료
		
		
		int[] dy = {0, 1, 0, -1};
		int[] dx = {1, 0, -1, 0};
		
		int nY, nX;	//다음 위치
		int hwaksan;
		
		for(int time = 0; time < timeLimit; time++) {
			//시간마다 처리
			
			//1. 미세먼지 확산
			for(int y = 1; y <= yLength; y++) {
				for(int x = 1; x <= xLength; x++) {
					if(map[y][x] > 0) {
						//미세먼지가 있을 경우
						hwaksan = 0;	//확산이 얼마나 되는지
						for(int d = 0; d < 4; d++) {
							//4방위로
							nY = y + dy[d];
							nX = x + dx[d];
							if(map[nY][nX] == -1) continue;	//벽이나 공기청정기
							copyMap[nY][nX] += map[y][x] / 5;	//확산
							hwaksan += map[y][x] / 5;	//확산량 늘리기
						}
						copyMap[y][x] += map[y][x] - hwaksan;	//확산된 만큼 줄이고 넣기
					}
				}
			}
			//미세먼지 확산 종료

			
			// 2. 공기청정기 가동
			
			//공기청정기 위에 부분 배열 회전
			for(int x = 3; x <= xLength; x++) {
				//밑에줄 오른쪽으로 한칸씩 당기기
				map[top][x] = copyMap[top][x-1];
			}
			for(int y = top-1; y >= 1; y--) {
				//오른쪽줄 위쪽으로 한칸씩 당기기
				map[y][xLength] = copyMap[y+1][xLength];
			}
			for(int x = xLength-1; x >= 1; x--) {
				//위쪽줄 왼쪽으로 한칸씩 당기기
				map[1][x] = copyMap[1][x+1];
			}
			for(int y = 2; y <= top; y++) {
				//왼쪽줄 아래쪽으로 한칸씩 당기기
				map[y][1] = copyMap[y-1][1];
			}
			map[top][1] = -1;	//여기는 미세먼지가 빨려들어가서 없어진다
			map[top][2] = 0;	//여기는 공기청정기 앞이다
			
			//공기청정기 밑에 부분 배열 회전
			for(int x = 3; x <= xLength; x++) {
				//위에줄
				map[bottom][x] = copyMap[bottom][x-1];
			}
			for(int y = bottom+1; y <= yLength; y++) {
				//오른쪽줄
				map[y][xLength] = copyMap[y-1][xLength];
			}
			for(int x = xLength-1; x >= 1; x--) {
				//밑줄
				map[yLength][x] = copyMap[yLength][x+1];
			}
			for(int y = yLength-1; y > bottom; y--) {
				//왼쪽줄
				map[y][1] = copyMap[y+1][1];
			}
			//공기청정기 처리 완료
			map[bottom][1] = -1;	//빨려들어가서 사라진다
			map[bottom][2] = 0;	//여기는 공기청정기 앞이다
			
			
			//공기청정기의 영향영역이 아닌 부분들은 그냥 복사해주기
			for(int y = 2; y < top; y++) {
				for(int x = 2; x < xLength; x++) {
					map[y][x] = copyMap[y][x];
				}
			}
			for(int y = bottom+1; y < yLength; y++) {
				for(int x = 2; x < xLength; x++) {
					map[y][x] = copyMap[y][x];
				}
			}
			//모든 처리 완료
			
//			// 종료 -> 임시맵 초기화
			for(int y = 1; y <= yLength; y++) {
				for(int x = 1; x <= xLength; x++) {
					copyMap[y][x] = 0;	//카피맵 초기화
				}
			}
		}
		
		int result = 0;
		for(int y = 1; y <= yLength; y++) {
			for(int x = 1; x <= xLength; x++) {
				result += map[y][x];
			}
		}
		
		System.out.println(result+2);
	}
	
	public static void print(int[][] map) {
		for(int y = 1; y < map.length-1; y++) {
			for(int x = 1; x < map[0].length-1; x++) {
				System.out.printf("%3d", map[y][x]);
			}
			System.out.println();
		}
		System.out.println();
	}
}
