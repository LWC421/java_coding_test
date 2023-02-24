import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;


public class B10026 {
	
	static int length;			//맵의 크기
	static char[][] greenMap;	//적록 색약의 맵
	static char[][] map;		//아닌 사람이 봤을때 맵

	static int count;			//개수 세기
	static int greenCount;		//적록 색약의 개수 세기
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		length = Integer.parseInt(in.readLine());
		
		greenMap = new char[length+2][length+2];
		map = new char[length+2][length+2];
	
		String s;
		char ch;
		for(int y = 1; y <= length; y++) {
			s = in.readLine();
			for(int x = 1; x <= length; x++) {
				ch = s.charAt(x-1);
				if(ch == 'G') {
					//적록 색약에게 빨강색으로 넣는다
					greenMap[y][x] = 'R';
				}
				else {
					//R아니면 B다
					greenMap[y][x] = ch;
				}
				map[y][x] = ch;
			}
		}
		//입력 종료

		//순회하기
		Queue<int[]> q = new ArrayDeque<>();
		int[] tmp;
		int curY, curX;
		for(int y = 1; y <= length; y++) {
			for(int x = 1; x <= length; x++) {
				ch = map[y][x];
				if(ch == 'R' || ch == 'G' || ch == 'B') {
					//색상을 봤으면
					bfs(y, x);
				}
				
				ch = greenMap[y][x];
				if(ch == 'R' || ch == 'B') {
					greenBfs(y, x);
				}
			}
		}
		
		System.out.printf("%d %d", count, greenCount);
	}
	
	public static void bfs(int startY, int startX) {
		//순회하기
		Queue<int[]> q = new ArrayDeque<>();
		int[] tmp;
		int curY, curX;
		
		char target = map[startY][startX];	//찾는 색상 받아오기
		map[startY][startX] = '\u0000';	//아무것도 아니라고 표시
		
		q.add(new int[] { startY, startX });
		
		while( !q.isEmpty() ) {
			tmp = q.poll();
			curY = tmp[0];
			curX = tmp[1];
			
			//하
			if(map[curY+1][curX] == target) {
				q.add(new int[] {curY+1, curX});
				map[curY+1][curX] = '\u0000';
			}
			//우
			if(map[curY][curX+1] == target) {
				q.add(new int[] {curY, curX+1});
				map[curY][curX+1] = '\u0000';
			}
			//좌
			if(map[curY][curX-1] == target) {
				q.add(new int[] {curY, curX-1});
				map[curY][curX-1] = '\u0000';
			}
			//상
			if(map[curY-1][curX] == target) {
				q.add(new int[] {curY-1, curX});
				map[curY-1][curX] = '\u0000';
			}
		}
		count++;	//구역 늘리기
	}
	
	public static void greenBfs(int startY, int startX) {
		//순회하기
		Queue<int[]> q = new ArrayDeque<>();
		int[] tmp;
		int curY, curX;
		
		char target = greenMap[startY][startX];	//찾는 색상 받아오기
		greenMap[startY][startX] = '\u0000';	//아무것도 아니라고 표시
		
		q.add(new int[] { startY, startX });
		
		while( !q.isEmpty() ) {
			tmp = q.poll();
			curY = tmp[0];
			curX = tmp[1];
			
			//하
			if(greenMap[curY+1][curX] == target) {
				q.add(new int[] {curY+1, curX});
				greenMap[curY+1][curX] = '\u0000';
			}
			//우
			if(greenMap[curY][curX+1] == target) {
				q.add(new int[] {curY, curX+1});
				greenMap[curY][curX+1] = '\u0000';
			}
			//좌
			if(greenMap[curY][curX-1] == target) {
				q.add(new int[] {curY, curX-1});
				greenMap[curY][curX-1] = '\u0000';
			}
			//상
			if(greenMap[curY-1][curX] == target) {
				q.add(new int[] {curY-1, curX});
				greenMap[curY-1][curX] = '\u0000';
			}
		}
		greenCount++;	//구역 늘리기
	}
}
