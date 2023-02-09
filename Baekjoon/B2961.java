import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B2961 {
	static int N;
	static int[][] zeryo;
	static long ssun = 1;	//쓴맛
	static long sin = 0;	//신맛
	static long min = Integer.MAX_VALUE;	//현재 차이값
	
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(in.readLine());
		zeryo = new int[N][2];	//[신맛, 쓴맛]을 가지는 배열
		
		StringTokenizer st = null;
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(in.readLine());
			zeryo[i][0] = Integer.parseInt(st.nextToken());
			zeryo[i][1] = Integer.parseInt(st.nextToken());
		}
		
		subset(0);
		
		System.out.println(min);
	}
	
	public static void subset(int count) {
		if(count == N) {	//모든 재료에 대해 확인 했을때
			if(sin != 0) {	//아무것도 안 넣은 경우를 제외하고
				min = Math.min(min, Math.abs(ssun - sin));
			}
			
			return;
		}
		
		//재료를 넣는 경우
		ssun *= zeryo[count][0];
		sin += zeryo[count][1];
		
		subset(count+1);
		
		//재료를 안 넣는 경우
		ssun /= zeryo[count][0];
		sin -= zeryo[count][1];
		subset(count+1);
	}
}
