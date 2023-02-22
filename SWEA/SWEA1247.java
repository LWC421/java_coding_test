import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA1247 {
	//회사 -> 고객N명 방문 -> 자신의 집
	
	static int numCustommer;			//고객의 수
	static int[] start = new int[2]; 	//회사
	static int[] end = new int[2];		//집
	static int[][] coords;				//고객들의 좌표
	static boolean[] visited;			//고객들의 집을 방문했는지
	static int curY, curX;				//현재 나의 위치 
	static int count;					//몇 개 방문 했는지
	static int distance;				//현재까지 거리
	static int min;						//최소거리 저장
	
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");
		
		int y, x;	//좌표 받기
		int T = Integer.parseInt(in.readLine());
		for(int test_case = 1; test_case <= T; test_case++) {
			numCustommer = Integer.parseInt(in.readLine());
			coords = new int[numCustommer][2];		//[y, x]형태로 받기
			visited = new boolean[numCustommer];	//방문 배열 초기화
			count = 0;								//방문 한거 없다고 표시
			distance = 0;							//거리 초기화
			min = Integer.MAX_VALUE;				//최소거리 초기화
			
			st = new StringTokenizer(in.readLine());
			
			//회사 좌표 받기
			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());
			start[0] = y;
			start[1] = x;
			
			//집 좌표 받기
			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());
			end[0] = y;
			end[1] = x;
			
			//고객 좌표 받기
			for(int i = 0; i < numCustommer; i++) {
				x = Integer.parseInt(st.nextToken());
				y = Integer.parseInt(st.nextToken());
				coords[i][0] = y;
				coords[i][1] = x;
			}
			
			//입력 종료
			
			//현재 위치 설정
			curY = start[0];
			curX = start[1];
			
			dfs();
			
			sb.append("#").append(test_case).append(" ").append(min).append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	//현재 어디 있는지
	public static void dfs() {
		if(count == numCustommer) {	//모두 방문 했으면 -> 집가자
			//집까지의 거리 구하기
			int curDistance = Math.abs(curY - end[0]) + Math.abs(curX - end[1]);
			distance += curDistance;
			min = Math.min(distance, min);
			distance -= curDistance;
			return;
		}
		
		for(int i = 0; i < numCustommer; i++) {
			if( !visited[i] ) {
				//방문하지 않았을 때만
				int curDistance = Math.abs(curY - coords[i][0]) + Math.abs(curX - coords[i][1]);
				int prevY = curY;
				int prevX = curX;
				
				visited[i] = true;			//방문 했다
				count++;
				distance += curDistance;	//거리 더해주기
				curY = coords[i][0];		//좌표 바꿔주기
				curX = coords[i][1];
				
				dfs();						//다음 거로 간다
				
				visited[i] = false;			//방문 안했다
				count--;
				distance -= curDistance;	//거리 빼주기
				curY = prevY;				//좌표 되돌리기
				curX = prevX;
			}
		}
	}
}