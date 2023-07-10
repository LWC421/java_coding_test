import java.io.*;
import java.util.*;

public class Main{

	static char[][] map;
	final static int yLength = 12;
	final static int xLength = 6;

	final static char EMPTY = '.';

	final static int[] dy = {1, -1, 0, 0};
	final static int[] dx = {0, 0, 1, -1};

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		map = new char[yLength][xLength];

		for (int y = 0; y < yLength; y++) {
			char[] row = in.readLine().toCharArray();
			for (int x = 0; x < xLength; x++) {
				map[y][x] = row[x];
			}
		}
		//입력 종료

		int result = 0;
		boolean find = true;
		List<Coord> bombList = null;

		while(find){

			//터뜨릴게 있을 동안
			bombList = bombs();
			if(bombList.isEmpty()) find = false;

			if(find){
				//터뜨릴게 있으면
				delete(bombList);
				result++;
			}
		}

		System.out.print(result);
	}

	public static List<Coord> bombs(){
		//맵에서 터뜨릴 게 있는지 체크
		LinkedList<Coord> coords = new LinkedList<>();
		boolean[][] visited = new boolean[yLength][xLength];

		char target;
		for (int y = 0; y < yLength; y++) {
			for (int x = 0; x < xLength; x++) {
				target = map[y][x];

				if(target != EMPTY && !visited[y][x]){
					//빈 곳이 아니면서 방문을 안 했으면
					Coord curr = new Coord(y, x);
					check(curr, visited, coords);	//현재 위치를 넣을 수 있는지 확인해보기
				}
			}
		}

		return coords;
	}

	public static void check(Coord start, boolean[][] visited, LinkedList<Coord> coords){
		visited[start.y][start.x] = true;

		LinkedList<Coord> tmpCoords = new LinkedList<>();
		tmpCoords.add(start);	//일단 현재꺼 할 수 있다고 넣고
		char first = map[start.y][start.x];
		int count = 1;		//현재 1개가 연결된 상태라는 뜻

		Queue<Coord> q = new ArrayDeque<>();
		q.add(start);

		Coord currCoord;
		char nChr;
		int nY, nX;
		while( !q.isEmpty()){
			currCoord = q.poll();

			for(int d = 0; d < 4; d++){
				nY = currCoord.y + dy[d];
				nX = currCoord.x + dx[d];

				if(nY < 0 || nY >= yLength) continue;
				if(nX < 0 || nX >= xLength) continue;

				if( visited[nY][nX] ) continue;

				nChr = map[nY][nX];
				if(nChr == first){
					//동일한 문자이면
					count++;		//연결된 개수 늘리고
					visited[nY][nX] = true;
					q.add(new Coord(nY, nX));
					tmpCoords.add(new Coord(nY, nX));
				}
			}
		}

		if(count >= 4){
			coords.addAll(tmpCoords);
		}
	}

	public static void delete(List<Coord> coords){
		for(Coord c: coords){
			map[c.y][c.x] = EMPTY;	//빈곳으로 표시
		}

		Stack<Character> column = new Stack<>();
		for(int x = 0; x < xLength; x++){
			column.clear();	//일단 세로줄 하나 비우고
			for(int y = 0; y < yLength; y++){
				if(map[y][x] != EMPTY){
					//터뜨리고 나서 빈 곳이 아니면
					column.add(map[y][x]);	//현재꺼 넣고
				}
				map[y][x] = EMPTY;	//비우고
			}

			int currY = 11;
			while( !column.isEmpty() ){
				char c = column.pop();
				//빈곳이 아닌 세로줄의 묶음을 가져와서
				map[currY--][x] = c;
			}
		}
	}

	static class Coord{
		int y, x;

		public Coord(int y, int x){
			this.y = y;
			this.x = x;
		}

		@Override
		public String toString(){
			return String.format("[%d, %d]", this.y, this.x);
		}
	}
}