import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

class Solution {
	
	static int numGoosle;	// [1 ~ 4] 
	static int yLength;		// [2 ~ 15]
	static int xLength;		// [2 ~ 12]
	static int minRemain;	// 최소 남은개수
	
	static byte[][] original;	//맵 입력
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;
		
		int T = Integer.parseInt(in.readLine());
		
		
		for(int test_case = 1; test_case <= T; test_case++) {
			//테스트 케이스 시작
			
			st = new StringTokenizer(in.readLine());
			numGoosle = Integer.parseInt(st.nextToken());
			xLength = Integer.parseInt(st.nextToken());
			yLength = Integer.parseInt(st.nextToken());
			
			minRemain = 0;
			
			original = new byte[yLength+2][xLength+2];
			
			//2번째 줄 입력 시작
			byte num;
			for(int y = 1; y <= yLength; y++) {
				st = new StringTokenizer(in.readLine());
				for(int x = 1; x <= xLength; x++) {
					num = Byte.parseByte(st.nextToken());
					original[y][x] = num;
					if(num != 0) {	//벽돌이면
						minRemain++;
					}
				}
			}
			//입력 종료
			
			//맵에 벽치기
			for(int y = 0; y <= yLength+1; y++) {
				original[y][0] 		   = -1;
				original[y][xLength+1] = -1;
			}
			
			for(int x = 0; x <= xLength+1; x++) {
				original[0][x] 		   = -1;
				original[yLength+1][x] = -1;
			}
			//벽치기 종료
			
			bomb(0, original);
			
			sb.append(String.format("#%d %d\n", test_case, minRemain));
		}
		
		System.out.println(sb.toString());
	}
	
	public static void bomb(int goosleCount, byte[][] map) {
		if(goosleCount == numGoosle) {
			//중복 순열을 다했으면 종료
			int count = 0;
			for(int y = 1; y <= yLength; y++) {
				for(int x = 1; x <= xLength; x++) {
					if(map[y][x] > 0) {
						count++;
					}
				}
			}
			
			minRemain = Math.min(count, minRemain);
			return;
		}
		
		byte[][] copied;

		for(int x = 1; x <= xLength; x++) {
			copied = copy(map);
			bfs(copied,x);
			bomb(goosleCount+1, copied);
		}
		
	}
	
	public static void bfs(byte[][] map, int cursor) {
		int top = -1;
		
		for(int y = 1; y <= yLength; y++) {
			if(map[y][cursor] != 0) {
				top = y;
				//해당열에 터뜨릴 벽돌이 있는지 확인
				break;
			}
		}
		
		if(top == -1) {
			//벽돌이 없었다면
			return;
		}
		
		//폭발체크
		
		boolean[][] bombed = new boolean[yLength+2][xLength+2];
		
		
		// [y, x, length]로 넣기
		Queue<int[]> q = new ArrayDeque<>();
		int[] tmp;
		int y, x;
		int length;	//큐에서 빼낸 정보 사용하기
		
		q.add(new int[] {top, cursor});	//처음거 넣고
		bombed[top][cursor] = true;
		
		while( !q.isEmpty() ) {
			//BFS를 돈다
			tmp = q.poll();
			y = tmp[0];
			x = tmp[1];
			length = map[y][x];	//벽돌의 숫자 가져오기
			
			//일단 해당 폭탄에 대해 터뜨리기
			for(int dy = 1; dy < length; dy++) {
				//위로 터지는거 확인
				if(map[y-dy][x] == -1) {	//-1이면 맵 밖으로 나간다는 뜻이다
					break;	//맵밖을 벗어나면
				}
				
				if(bombed[y-dy][x] || map[y-dy][x] == 0) {
					//이미 터진곳이면 큐에 넣지 않는다
					continue;
				}
				
				bombed[y-dy][x] = true;
				q.add(new int[] {y-dy, x});
			}
			
			for(int dy = 1; dy < length; dy++) {
				//아래로 터지는거 확인
				if(map[y+dy][x] == -1) {	//-1이면 맵 밖으로 나간다는 뜻이다
					break;	//맵밖을 벗어나면
				}
				if(bombed[y+dy][x]|| map[y+dy][x] == 0) {
					//이미 터진곳이면 큐에 넣지 않는다
					continue;
				}
				bombed[y+dy][x] = true;
				q.add(new int[] {y+dy, x});
			}
			
			for(int dx = 1; dx < length; dx++) {
				//오른쪽으로 터지는거 확인
				if(map[y][x+dx] == -1) {	//-1이면 맵 밖으로 나간다는 뜻이다
					break;	//맵밖을 벗어나면
				}
				if(bombed[y][x+dx] || map[y][x+dx] == 0) {
					//이미 터진곳이면 큐에 넣지 않는다
					continue;
				}
				bombed[y][x+dx] = true;
				q.add(new int[] {y, x+dx});
			}
			
			for(int dx = 1; dx < length; dx++) {
				//오른쪽으로 터지는거 확인
				if(map[y][x-dx] == -1) {	//-1이면 맵 밖으로 나간다는 뜻이다
					break;	//맵밖을 벗어나면
				}
				if(bombed[y][x-dx] || map[y][x-dx] == 0) {
					//이미 터진곳이면 큐에 넣지 않는다
					continue;
				}
				bombed[y][x-dx] = true;
				q.add(new int[] {y, x-dx});
			}
		}
		
		//폭발했으면 밑으로 내리기
		for(x = 1; x <= xLength; x++) {
			//왼쪽부터 보면서
			for(y = 1; y <= yLength; y++) {
				if(bombed[y][x]) {
					map[y][x] = 0;	//터뜨리기
					for(int cy = y; cy > 1; cy--) {
						//내리기
						map[cy][x] = map[cy-1][x];
					}
					map[1][x] = 0;
				}
			}
		}
	}
	
	public static byte[][] copy(byte[][] map){
		byte[][] copied = new byte[yLength+2][xLength+2];
		
		for(int y = 0; y <= yLength+1; y++) {
			for(int x = 0; x <= xLength+1; x++) {
				copied[y][x] = map[y][x];
			}
		}
		
		return copied;
	}
}