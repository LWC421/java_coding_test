import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;

public class B2667 {
	
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		
		int length = Integer.parseInt(in.readLine()); //맵 크기
		int[][] map = new int[length+2][length+2];	  //맵
		boolean[][] visited = new boolean[length+2][length+2];	//방문체크
		PriorityQueue<Integer> counts = new PriorityQueue<>();
		int count;
		
		Queue<Coord> startQ = new ArrayDeque<>();
		
		//맵 입력받기
		String row;
		int num;
		for(int y = 1; y <= length; y++) {
			row = in.readLine();
			for(int x = 1; x <= length; x++) {
				num = Integer.parseInt(String.valueOf(row.charAt(x-1)));
				map[y][x] = num;
				
				if(num == 1) {	//갈 수 있는 곳이면
					startQ.add(new Coord(y, x));
				}
			}
		}
		//입력 종료
		
		Queue<Coord> curQ = new ArrayDeque<>();
		
		Coord cur;
		while( !startQ.isEmpty() ) {
			//봐야하는 시작지점이 있으면
			cur = startQ.poll();
			if(visited[cur.y][cur.x]) {
				continue;	//이미 방문한 곳이면
			}
			
			curQ.add(cur);	//지점 넣기
			visited[cur.y][cur.x] = true; 
			count = 0;		//단지내 집의 개수 0으로 초기화
			while( !curQ.isEmpty() ) {
				//탐색하기
				cur = curQ.poll();
				
				count++;
				
				//위
				if( !visited[cur.y-1][cur.x] && map[cur.y-1][cur.x] == 1) {
					curQ.add(new Coord(cur.y-1, cur.x));
					visited[cur.y-1][cur.x] = true;
				}
				//아래
				if( !visited[cur.y+1][cur.x] && map[cur.y+1][cur.x] == 1) {
					curQ.add(new Coord(cur.y+1, cur.x));
					visited[cur.y+1][cur.x] = true;
				}
				//왼쪽
				if( !visited[cur.y][cur.x-1] && map[cur.y][cur.x-1] == 1) {
					curQ.add(new Coord(cur.y, cur.x-1));
					visited[cur.y][cur.x-1] = true;
				}
				//오른쪽
				if( !visited[cur.y][cur.x+1] && map[cur.y][cur.x+1] == 1) {
					curQ.add(new Coord(cur.y, cur.x+1));
					visited[cur.y][cur.x+1] = true;
				}
			}
			counts.add(count);	//하나의 단지의 집의 개수 추가
		}
		
		sb.append(String.format("%d\n", counts.size()));
		while( !counts.isEmpty() ) {
			sb.append(String.format("%d\n", counts.poll()));
		}
		
		System.out.println(sb.toString());
	}
	
	static class Coord{
		int y, x;
		Coord(int y, int x){
			this.y = y;
			this.x = x;
		}
	}
}
