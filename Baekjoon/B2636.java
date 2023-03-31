import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class B2636 {
    
    //4방위 탐색용
    final static int[] dy = {0, 1, 0, -1};
    final static int[] dx = {1, 0, -1, 0};
    
    public static void main(String[] args) throws Exception{
        // 가장자리에는 치즈가 없다
        // 치즈에는 하나 이상의 구멍이 있다
        // 공기 중에 노출 되면 접촉된 칸은 없어진다
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        
        int yLength = Integer.parseInt(st.nextToken());
        int xLength = Integer.parseInt(st.nextToken());
        
        
        int[][] map = new int[yLength][xLength];
        List<Coord> cheeses = new LinkedList<>();            //치즈 위치 저장용
        
        for(int y = 0; y < yLength; y++) {
            st = new StringTokenizer(in.readLine());
            for(int x = 0; x < xLength; x++) {
                map[y][x] = Integer.parseInt(st.nextToken());
            }
        }
        //입력 종료
        
        //바깥쪽 치즈 가져오기
        Queue<Coord> q = new ArrayDeque<>();
        q.add(new Coord(0, 0));	//얘는 어쨌든 바깥쪽이다
        boolean[][] visited = new boolean[yLength][xLength];	//방문 체크용
        visited[0][0] = true;
        int nY, nX;				//다음 위치 저장용
        Coord curr = null;
        while( !q.isEmpty() ) {
        	curr = q.poll();
        	for(int d = 0; d < 4; d++) {
        		//4방위 탐색
        		nY = curr.y + dy[d];
        		nX = curr.x + dx[d];
        		if(nY < 0 || nY >= yLength || nX < 0 || nX >= xLength) {
        			continue;	//맵 밖으로 나갈 경우
        		}
        		if(visited[nY][nX]) continue;	//이미 방문 했을경우
        		visited[nY][nX] = true;
        		if(map[nY][nX] == 1) {
        			//치즈이면
        			cheeses.add(new Coord(nY, nX));
        		}
        		else {
        			//빈 곳이면
        			q.add(new Coord(nY, nX));
        		}
        	}
        }
        
        int time = 0;
        int remainCheese = -1;
        while( !cheeses.isEmpty() ) {
        	time++;
        	remainCheese = cheeses.size();
        	for(Coord cheese: cheeses) {
        		map[cheese.y][cheese.x] = 0;	//공기로 바꾸기
        	}
        	cheeses.clear();	//다 사용했으면 되돌리자
        	
        	q.add(new Coord(0, 0));	//얘는 어쨌든 바깥쪽이다
        	//방문체크 되돌리기
        	for(int y = 0; y < yLength; y++) {
        		for(int x = 0; x < xLength; x++) {
        			visited[y][x] = false;
        		}
        	}
            visited[0][0] = true;
            curr = null;
            while( !q.isEmpty() ) {
            	curr = q.poll();
            	for(int d = 0; d < 4; d++) {
            		//4방위 탐색
            		nY = curr.y + dy[d];
            		nX = curr.x + dx[d];
            		if(nY < 0 || nY >= yLength || nX < 0 || nX >= xLength) {
            			continue;	//맵 밖으로 나갈 경우
            		}
            		if(visited[nY][nX]) continue;	//이미 방문 했을경우
            		visited[nY][nX] = true;
            		if(map[nY][nX] == 1) {
            			//치즈이면
            			cheeses.add(new Coord(nY, nX));
            		}
            		else {
            			//빈 곳이면
            			q.add(new Coord(nY, nX));
            		}
            	}
            }
        }
        
        
        StringBuilder sb = new StringBuilder("");
        sb.append(time).append("\n");
        sb.append(remainCheese);
        
        System.out.println(sb.toString());
    }
    
    static class Coord{
        int y;
        int x;
        
        public Coord(int y, int x) {
            this.y = y;
            this.x = x;
        }

        @Override
        public boolean equals(Object obj) {
            Coord other = (Coord)obj;
            return this.y == other.y && this.x == other.x;        //좌표 기준으로 동일여부 체크
        }

		@Override
		public String toString() {
			return String.format("[%d, %d]", this.y, this.x);
		}
    }
}