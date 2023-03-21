import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;

public class B2447 {

	public static void main(String[] args) throws Exception{
		int N = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
		char[][] map = new char[N][N];
		
		//일단 별로 다 찍자
		for(int y = 0; y < N; y++) {
			for(int x = 0; x < N; x++) {
				map[y][x] = '*';
			}
		}
		
		Stack<int[]> stack = new Stack<>();
		stack.push(new int[] {N/3, N/3, N/3});
		int y, x, length, empty;
		int[] tmp;	//[y, x, length]
		while( !stack.isEmpty() ) {
			tmp = stack.pop();
			y 		= tmp[0];
			x 		= tmp[1];
			length 	= tmp[2];
			
			
			for(int dy = 0; dy < length; dy++) {
				for(int dx = 0; dx < length; dx++) {
					map[y+dy][x+dx] = ' ';	//여기는 빈곳으로 표시
				}
			}
			
			
			if(length != 1) {
				//length가 1보다 클대만 쪼개자
				int nLength = length / 3;	//다음 빈곳의 길이
				stack.push(new int[] {y-nLength*2		, x-nLength*2			, nLength});	//좌상단
				stack.push(new int[] {y-nLength*2		, x+nLength				, nLength});	//중상단
				stack.push(new int[] {y-nLength*2		, x+length+nLength  	, nLength});	//우상단
				
				stack.push(new int[] {y+nLength	 		, x-nLength*2			, nLength});	//좌
				stack.push(new int[] {y+nLength  		, x+length+nLength  	, nLength});	//우
				
				stack.push(new int[] {y+length+nLength  , x-nLength*2			, nLength});	//좌하단
				stack.push(new int[] {y+length+nLength  , x+nLength				, nLength});	//주하단
				stack.push(new int[] {y+length+nLength  , x+length+nLength  	, nLength});	//우하단
			}
		}
		StringBuilder sb = new StringBuilder("");
		for(char[] row: map) {
			for(char c: row) {
				sb.append(c);
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}
}
