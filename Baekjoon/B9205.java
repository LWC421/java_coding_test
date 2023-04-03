import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B9205 {
	
	static final int MAX_DISTANCE = 50*20;	//50미터마다 하나 * 최대 20개 소지
	
	static boolean isHappy;			//행복 여부
	static boolean[] visited;		//방문 여부
	static boolean[][] isConnected;	//거리
	static int length;				//편의점 + 2

	public static void main(String[] args) throws Exception{
		// 상근이 집에서 출발
		// 맥주 한 박스(20개 들어있음)
		// 50미터마다 한 병씩 마신다
		// 한 박스에는 최대 20병까지만 들고 갈 수 있다
		// 편의점에서 맥주를 구매할 수 있다
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;
		
		int T = Integer.parseInt(in.readLine());
		for(int test_case = 1; test_case <= T; test_case++) {
			//테스트 케이스 시작
			int numConv = Integer.parseInt(in.readLine());
			length = numConv+2;
			isHappy = false;	//안 행복하다 ㅠㅠ
			

			//0 -> 시작점, 1 ~ numConv -> 편의점, numConv+1 -> 페스티벌
			Coord[] points = new Coord[length];
			//상근이의 집(시작점) 받기
			st = new StringTokenizer(in.readLine());
			points[0] = new Coord(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			
			//편의접 받기
			for(int i = 1; i <= numConv; i++) {
				st = new StringTokenizer(in.readLine());
				points[i] =new Coord(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			}
			
			//페스티벌 좌표 받기
			st = new StringTokenizer(in.readLine());
			points[numConv+1] = new Coord(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			
			isConnected = new boolean[length][length];
			Coord src, dest;
			int yDistance, xDistance;	//거리 저장용
			for(int y = 0; y < length; y++) {
				src = points[y];
				for(int x = 0; x < length; x++) {
					if(y == x) {
						isConnected[y][x] = false;	//자기자신으로는 가지 말자
						continue;
					}
					dest = points[x];
					yDistance = src.y - dest.y;
					xDistance = src.x - dest.x;
					if(yDistance < 0) yDistance = -yDistance;
					if(xDistance < 0) xDistance = -xDistance;
					if(yDistance + xDistance <= MAX_DISTANCE) {
						//갈 수 있으면
						isConnected[y][x] = true;	//연결되어있다고 하자
					}
				}
			}
			
			for(int mid = 0; mid < length; mid++) {
				for(int start = 0; start < length; start++) {
					for(int end = 0; end < length; end++) {
						if(start == end) continue;
						if(isConnected[start][mid] && isConnected[mid][end]) {
							//경유해서 갈 수 있으면
							isConnected[start][end] = true;
						}
					}
				}
			}
			
			sb.append(isConnected[0][length-1] ? "happy" : "sad").append("\n");
		}
		System.out.println(sb.toString());
	}
	
	static class Coord{
		int y;
		int x;
		public Coord(int y, int x) {
			this.y = y;
			this.x = x;
		}
	}
}
