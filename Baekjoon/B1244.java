import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B1244 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int numSwitch = Integer.parseInt(in.readLine());
		boolean[] switches = new boolean[numSwitch+1];	//0번째 빼고 생각하기
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		String s = null;
		//스위치 꺼진상태 켜진상태 받아오기
		for(int i = 1; i < numSwitch+1; i++) {
			s = st.nextToken();
			if(s.equals("0")) {
				switches[i] = false;
			}
			else {
				switches[i] = true;
			}
		}
		
		int numStudent = Integer.parseInt(in.readLine());
		boolean[] isMan = new boolean[numStudent];
		int[] nums = new int[numStudent];
		for(int i = 0; i < numStudent; i++) {
			st = new StringTokenizer(in.readLine(), " ");
			s = st.nextToken();
			if(s.equals("1")) {
				isMan[i] = true;		
			}
			else {
				isMan[i] = false;
			}
			
			nums[i] = Integer.parseInt(st.nextToken());
		}
		//학생수만큼 돌기
		for(int i = 0; i < numStudent; i++) {
			int num = nums[i];
			
			if(isMan[i]) {	//남학생이면
				for(int j = num; j <= numSwitch; j += num) {
					switches[j] = !switches[j];	//반대로 하기
				}
			}
			else {	//여학생이면
				int length = 0;
				while(num-length >= 1 &&	//0번은 가짜다
						num+length <= numSwitch && 
						switches[num-length] == switches[num+length]) {
					length++;
				}
				length--;
				for(int j = num-length; j <= num+length; j++) {
					switches[j] = !switches[j];	//반대로 하기
				}
			}
		}
		
		StringBuilder sb = new StringBuilder("");
		for(int i = 1; i <= numSwitch; i++) {
			if(switches[i]) {
				sb.append(1 + " ");
			}
			else {
				sb.append(0 + " ");
			}
			if(i >= 20 && i % 20 == 0) {
				sb.append("\n");
			}
		}
		System.out.println(sb.toString());
	}
}
