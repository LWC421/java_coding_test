import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class B11054 {

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int length = Integer.parseInt(in.readLine());
		
		int[] inputs = new int[length];	//입력 받는 숫자 저장
		StringTokenizer st = new StringTokenizer(in.readLine());
		for(int i = 0; i < length; i++) {
			inputs[i] = Integer.parseInt(st.nextToken());
		}
		//입력 종료
		
		int[] asc = new int[length];	//오름차순 길이
		for(int i = 0; i < length; i++) {
			//얘는 앞에서부터 본다
			asc[i] = 1;	//최소한 1은 보장된다
			for(int j = 0; j < i; j++) {
				//현재 보고있는거의 앞부분을 본다
				if(inputs[j] < inputs[i]) {
					asc[i] = Math.max(asc[i], asc[j]+1);
				}
			}
		}
		
		int[] desc = new int[length];	//내림차순 길이
		for(int i = length-1; i >= 0; i--) {
			//얘는 뒤에서부터 보는데
			desc[i] = 0;
			for(int j = i+1; j < length; j++) {
				//현재 보고있는거의 뒷부분을 보자
				if(inputs[i] > inputs[j]) {
					//작아지는 경우면
					desc[i] = Math.max(desc[i], desc[j] + 1);
				}
			}
		}
		
//		System.out.println(Arrays.toString(asc));
//		System.out.println(Arrays.toString(desc));
		
		int result = 0;	//일단 결과값 저장
		
		for(int i = 0; i < length; i++) {
			//오르락이랑 내리랑 합친게 더 크면 -> 바꿔주자
			result = Math.max(result, asc[i] + desc[i]);
		}
		System.out.println(result);
	}
}
