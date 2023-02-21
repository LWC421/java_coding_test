import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B3109 {
	
	static char[][] map;
	static int yLength, xLength;
	static boolean[] done;
	static int count = 0;
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		yLength = Integer.parseInt(st.nextToken());
		xLength = Integer.parseInt(st.nextToken());
	
		//위 아래는 건물로 막기
		map = new char[yLength+2][xLength];
		done = new boolean[yLength+2];
		
		//맵 형성하기 -> X는 막힌곳
		String s;
		for(int y = 1; y <= yLength; y++) {
			s = in.readLine();
			for(int x = 0; x < xLength; x++) {
				map[y][x] = s.charAt(x);
			}
		}
		
		//위 아래 가장자리 막기
		for(int x = 0; x < xLength; x++) {
			map[0][x] = 'X';
			map[yLength+1][x] = 'X';
		}
		
		for(int y = 1; y <= yLength; y++) {
			dfs(y, y, 0);	//y -> (y, 0)에서 시작
		}
		
		System.out.println(count);
	}
	
	public static void dfs(int start, int curY, int curX) {
		if(curX == xLength-1 && !done[start] ) {
			//마지막에 도착했으면
			done[start] = true;	//start에서 출발한 애는 도착했다고 표시
			count++;
			return;
		}
		
		if( done[start] ) {
			return;	//이미 완성된 경로이면 안봄
		}
		
		for(int dy = -1; dy <= 1; dy++) {
			//위 -> 중간 -> 아래 순으로 간다
			if(map[curY+dy][curX+1] == '.') {
				//갈 수 있으면
				if( !done[start] ) {
					map[curY+dy][curX+1] = 'x';	//갈수 없다고 표시
					dfs(start, curY+dy, curX+1);
				}
			}
		}
	}
}
