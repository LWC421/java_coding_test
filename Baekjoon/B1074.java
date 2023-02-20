import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B1074 {

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		int N;		// 맵의 크기는 2^N이 된다
		int r, c;	//몇 번째에 방문하는지 체크
		
		N = Integer.parseInt(st.nextToken());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		
		int mapSize = (int)Math.pow(2, N);	//맵 크기 구하기

		int count = 0;
		int curY = 0;
		int curX = 0;
		int length = mapSize;
		int half;
		int size = 0;
		while( length != 1 ) {
			half = length /2;
			size = half*half;
			if(r < curY + half && c < curX + half) {	//좌상단인 경우
				length = half;
			}
			else if(r < curY + half && curX + half <= c) {	//우상단인 경우
				count += size;	//좌상단은 아니므로 그만큼 더해주기
				curX = curX + half;
			}
			else if(curY + half <= r && c < curX + half) {	//좌하단인 경우
				count += size*2;
				curY = curY + half;
			}
			else if(curY + half <= r && curX + half <= c) {	//우하단인 경우
				count += size*3;
				curY = curY + half;
				curX = curX + half;
			}
		}
		System.out.println(count);
	}
}
