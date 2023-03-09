import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class B15685 {

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		int numDragon = Integer.parseInt(in.readLine());	//N, 드래곤 커브의 개수
		
		List<Coord> curves = new LinkedList<>();
		
		int y, x;	//좌표
		int dir;	//방향, (0: +x, 1: -y, 2: -x, 3: +y)
		int gen;	//세대, [0 ~ 10]
		
		int dy, dx;
		Coord first;
		for(int i = 0; i < numDragon; i++) {
			dy = dx = 0;	//변위 초기화
			st = new StringTokenizer(in.readLine());
			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());
			dir = Integer.parseInt(st.nextToken());
			gen = Integer.parseInt(st.nextToken());
			
//			if(dir == 0) dx = 1;
//			else if(dir == 1) dy = -1;
//			else if(dir == 2) dx = -1;
//			else if(dir == 3) dy = 1;
			
			first = new Coord(y, x, dir);
			curves.addAll(GetDragon(first, gen));	//gen세대 드래곤 커브를 list로 받아온다
		}

		boolean[][] map = new boolean[200+3][200+3];
		//101이 원점이다
		
		for(Coord curve: curves) {
			dy = dx = 0;
			if(curve.dir == 0) dx = 1;
			else if(curve.dir == 1) dy = -1;
			else if(curve.dir == 2) dx = -1;
			else if(curve.dir == 3) dy = 1;
			
			map[101+curve.y][101+curve.x] = true;		//여기에서부터
			map[101+curve.y+dy][101+curve.x+dx] = true;	//여기까지 선분이다
		}
		
		int result = 0;
		for(y = 0; y < 202; y++) {
			for(x = 0; x < 202; x++) {
				if(map[y][x] && map[y+1][x] && map[y][x+1] && map[y+1][x+1]) {
					//정사각형에 들어가면
					result++;
				}
			}
		}
		
		System.out.println(result);
	}
	
	//드래곤 커브를 얻어온다
	public static List<Coord> GetDragon(Coord first, int gen){
		//gen == 0이면 그대로 주면 된다
		List<Coord> result = new LinkedList<>();
		result.add(first);	//처음거 넣고
		
		int size;
		
		Coord last;	//마지막 선분을 기준으로 회전한다
		Coord current;
		
		int nY, nX;	//다음 좌표 계산
		int nDir;	//다음 방향
		
		int centerY, centerX;	//회전축 가져오기
		int dy, dx;
		int diffY, diffX;
		
		for(int g = 0; g < gen; g++) {
			//세대에 변화에 따라 넣기
			dy = dx = 0;
			size = result.size();
			last = result.get(size-1);
			if(last.dir == 0) dx = 1;
			else if(last.dir == 1) dy = -1;
			else if(last.dir == 2) dx = -1;
			else if(last.dir == 3) dy = 1;
			
			centerY = last.y + dy;
			centerX = last.x + dx;	//중심축 계산
			
			for(int i = size-1; i >= 0; i--) {
				//마지막 선분부터 넣어줘야한다
				dy = dx = 0;
				current = result.get(i);	//커브해줘야되는 선분 가져오기
				
				//(0: +x, 1: -y, 2: -x, 3: +y)
				nDir = (current.dir + 1) % 4;
				diffY = current.y - centerY;
				diffX = current.x - centerX;
				
				if(nDir == 0) dx = 1;
				else if(nDir == 1) dy = -1;
				else if(nDir == 2) dx = -1;
				else if(nDir == 3) dy = 1;
				
				nY = centerY + diffX - dy;
				nX = centerX - diffY - dx;
				result.add(new Coord(nY, nX, nDir));
			}
		}
		
		return result;	//결과 반환
	}
	
	static class Coord{
		int y;
		int x;	//좌표
		int dir;
		public Coord(int y, int x, int dir) {
			super();
			this.y = y;
			this.x = x;
			this.dir = dir;
		}
		@Override
		public String toString() {
			return String.format("[%d, %d] - %d", y, x, dir);
		}
	}
}
