import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class SWEA7699 {
	
	static int yLength, xLength;
	static char[][] map;
	static int maxCount;
	
	//4방위 탐색용
	final static int[] dy = {0, 1, 0, -1};
	final static int[] dx = {1, 0, -1, 0};

	public static void main(String[] args) throws Exception{
		// [1, 1] -> [R, C] 까지 있다
		// [1, 1]에서 시작
		// 각칸에는 알파벳 있다, 같은 알파벳이면 같은 것이다
		// 각 알파벳을 처음 만나면 비용이 없다
		// 4방위로 이동한다
		// 같은 알파벳을 두번이상 만나지 않으며 가장 많은 알파벳을 만나는 방법
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		int T = Integer.parseInt(in.readLine());	//테스트 케이스 개수
		StringTokenizer st = null;
		for(int test_case = 1; test_case <= T; test_case++) {
			//테스트 케이스 시작
			st = new StringTokenizer(in.readLine());
			
			yLength = Integer.parseInt(st.nextToken());	//R, y크기
			xLength = Integer.parseInt(st.nextToken()); //C, x크기
			maxCount = 0;								//최대 알파벳 본 갯수 초기화
			
			map = new char[yLength + 2][xLength + 2];
			
			for(int y = 1; y <= yLength; y++) {
				char[] row = in.readLine().toCharArray();
				for(int x = 1; x <= xLength; x++) {
					map[y][x] = row[x-1];
				}
			}
			//입력 종료
			
			for(int y = 0, limit=yLength+2; y < limit; y++) {
				map[y][0] = '0';
				map[y][xLength+1] = '0';
			}
			for(int x = 0, limit=xLength+2; x < limit; x++) {
				map[0][x] = '0';
				map[yLength+1][x] = '0';
			}
			//벽치기
			
			Data init = new Data(1, 1, 1, 0);
			init.setAlpha(map[1][1]);		//처음지점 보고 시작
			
			Queue<Data> q = new ArrayDeque<>();
			q.add(init);
			
			Data curr = null;
			int nY, nX;
			Data next;
			while( !q.isEmpty() ) {
				curr = q.poll();
				
				if(curr.count > maxCount){
					maxCount = curr.count;
				}
				for(int d = 0; d < 4; d++) {
					nY = curr.y + dy[d];
					nX = curr.x + dx[d];
					if(map[nY][nX] == '0') continue;			//맵 밖이면
					if(curr.hasAlpha(map[nY][nX])) continue;	//이미 봤던 거면
					next = new Data(nY, nX, curr.count+1, curr.alpha);
					next.setAlpha(map[nY][nX]);	//이거 볼 예정으로 넣기
					q.add(next);				//큐에다가 넣기
				}
			}
			
			sb.append(String.format("#%d %d\n", test_case, maxCount));
		}
		
		System.out.println(sb.toString());
	}
	
	static class Data{
		int y;
		int x;
		int count;
		int alpha;
		public Data(int y, int x, int count, int alpha) {
			this.y = y;
			this.x = x;
			this.count = count;
			this.alpha = alpha;
		}
		
		public boolean hasAlpha(char c) {
			int index = c - 'A';
			return (this.alpha & (1 << index)) != 0;
		}
		
		public void setAlpha(char c) {
			int index = c - 'A';
			this.alpha = (this.alpha | (1 << index));
		}
	}
}
