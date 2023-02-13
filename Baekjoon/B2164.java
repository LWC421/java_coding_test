import java.io.BufferedReader;
import java.io.InputStreamReader;
public class B2164 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int target = Integer.parseInt(in.readLine());
		int off = 0;
		for(int i = 19; i >= 0; i--) {
			if( ( target & (1 << i) ) > 0) {	//가장 높은 비트 찾기
				off = target ^ (1 << i);	//가장 높은 비트 끄기
				if(off == 0) {	//가장 높은 비트 껐을때 0이면 입력 값 출력
					System.out.print(target);	
					return;
				}	//2 * 가장 높은 bit끈값
				System.out.print(2*off);
				return;
			}
		}
	}
}