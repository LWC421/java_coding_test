import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B17136 {

	static int[][] map;
	final static int LENGTH = 10;
	
	static int remains[];			//색종이 남은 개수들 저장
	static boolean solved;			//문제가 해결되었는지 저장
	static int minCount;			//색종이를 적게 사용하는 횟수 저장
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		map = new int[LENGTH][LENGTH];		//10 * 10 맵 생성
		
		int num;
		int remainOne = 0;	//1의 남은 개수 저장용
		for(int y = 0; y < LENGTH; y++) {
			st = new StringTokenizer(in.readLine());
			for(int x = 0; x < LENGTH; x++) {
				num = Integer.parseInt(st.nextToken());
				map[y][x] = num;
				remainOne += num;
			}
		}
		//맵 입력 종료
		
		remains = new int[6];	//0번은 안쓸거다
		for(int i = 1; i < 6; i++) {
			remains[i] = 5;		//처음에 5장씩 있다고 해놓자
		}
		
		minCount = 5 * 5 +1;		//적당한 수로 초기화
		
		solved = false;				//아직 안 풀렸다
		dfs(0, 0, 0, remainOne);	//아직 0장 붙였고, [0, 0]에서 시작, 아직 remainOne만큼 남았다
		System.out.println(minCount == 5 * 5 + 1 ? -1 : minCount);
	}
	
	/**
	 * @param y	좌상단의 y
	 * @param x	좌상단의 x
	 * @param scale 놓을 색종이의 크기
	 * @param remainOne 남은 1의 개수
	 */
	static void dfs(int count, int y, int x, int remainOne) {
		if(remainOne == 0) {
			//붙여야 할 곳이 없으면 -> 풀린거다
			minCount = Math.min(minCount, count);
			return;
		}
		
		if(y > 9 || x > 9) {
			//마지막칸을 넘어갔으면
			return;
		}
		
		int nextCoord = y * 10 + x;
		int nY, nX;
		nextCoord++;
		nY = nextCoord / 10;
		nX = nextCoord % 10;	//줄바뀜까지 생각했을때 이렇게 계산해주자
		
		if(map[y][x] == 0) {
			//이미 0이면 -> 다음 칸으로 넘어간다
			dfs(count, nY, nX, remainOne);
		}
		else {
			//해당칸이 0이 아니면 -> 고려해보자
			for(int scale = 5; scale > 0; scale--) {
				//해당 좌표에 대해 5크기 ~ 1크기까지의 색종이에 대해 고려해본다
				if(check(y, x, scale)) {
					//해당 종이를 붙일 수 있다면
					attach(y, x, scale);	//색종이를 붙이고
					
					dfs(count+1, nY, nX, remainOne - (scale*scale));	//다음칸으로 넘어가자
					
					//dfs로부터 되돌아왔으면
					dettach(y, x, scale);	//색종이를 다시 회수하자
				}
			}
		}
	}
	
	
	
	/**
	 * @param y	좌상단의 y
	 * @param x	좌상단의 x
	 * @param scale	놓을 색종이의 크기
	 * @return
	 */
	static boolean check(int y, int x, int scale) {
		if(remains[scale] == 0) {
			//만약 색종이를 다 썼으면 -> 붙일 수 없다
			return false;
		}
		
		if(y + scale > 10 || x + scale > 10) {
			//해당 종이를 붙이는 것이 벗어난다면 false를 반환
			return false;
		}
		int nY, nX;
		
		for(int dy = 0; dy < scale; dy++) {
			nY = y + dy;
			for(int dx = 0; dx < scale; dx++) {
				nX = x + dx;
				if(map[nY][nX] == 0) {
					//만약 붙일 수 없으면
					return false;
				}
			}
		}
		
		return true;	//붙일수 있다는 뜻이다
	}

	/**
	 * 해당 공간에 실제로 색종이를 붙인다
	 * @param y	좌상단의 y
	 * @param x	좌상단의 x
	 * @param scale	놓을 색종이의 크기
	 */
	static void attach(int y, int x, int scale) {
		int nY, nX;
		
		for(int dy = 0; dy < scale; dy++) {
			nY = y + dy;
			for(int dx = 0; dx < scale; dx++) {
				nX = x + dx;
				map[nY][nX] = 0;	//색종이 붙이기
			}
		}
		remains[scale]--;
	}

	/**
	 * 해당 공간에 실제로 색종이를 붙인다
	 * @param y	좌상단의 y
	 * @param x	좌상단의 x
	 * @param scale	놓을 색종이의 크기
	 */
	static void dettach(int y, int x, int scale) {
		int nY, nX;
		
		for(int dy = 0; dy < scale; dy++) {
			nY = y + dy;
			for(int dx = 0; dx < scale; dx++) {
				nX = x + dx;
				map[nY][nX] = 1;	//색종이 떼기
			}
		}
		remains[scale]++;
	}
}
