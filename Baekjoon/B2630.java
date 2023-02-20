import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class B2630 {

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		
		int N;	//N, 한변의 길이
		int numZero = 0;
		int numOne = 0;	//0, 1의 개수
		N = Integer.parseInt(in.readLine());
		int[][] map = new int[N][N];

		//맵 받기
		StringTokenizer st = null;
		for(int y = 0; y < N; y++) {
			st = new StringTokenizer(in.readLine());
			for(int x = 0; x < N; x++) {
				map[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		//스택 사용해서 하기, [y, x, length]를 의미한다
		Stack<int[]> stack = new Stack<>();
		stack.push(new int[] {0, 0, N});

		int[] tmp;
		int y, x, length;
		int sum;	//영역의 합
		while( !stack.isEmpty() ) {	//비어있지 않은 동안
			tmp = stack.pop();
			y 		= tmp[0];
			x 		= tmp[1];
			length  = tmp[2];
			sum = 0;

			//영역합 구하기
			for(int curY = y; curY < y+length; curY++) {
				for(int curX = x; curX < x+length; curX++) {
					sum += map[curY][curX];
				}
			}

			//1로 가득찬 경우다
			if(sum == length * length) {
				numOne++;
			}
			else if(sum == 0) {
				numZero++;
			}
			else {	//4분할 하기
				int half = length / 2;
				stack.push(new int[] {y, 		x, 		half});
				stack.push(new int[] {y+half, 	x, 		half});
				stack.push(new int[] {y, 		x+half, half});
				stack.push(new int[] {y+half, 	x+half, half});
			}
		}
		
		sb.append(numZero).append("\n");
		sb.append(numOne);
		System.out.println(sb.toString());
	}

}
