import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B1244 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int numSwitch = Integer.parseInt(in.readLine());
		boolean[] switches = new boolean[numSwitch+1];	//0��° ���� �����ϱ�
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		String s = null;
		//����ġ �������� �������� �޾ƿ���
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
		//�л�����ŭ ����
		for(int i = 0; i < numStudent; i++) {
			int num = nums[i];
			
			if(isMan[i]) {	//���л��̸�
				for(int j = num; j <= numSwitch; j += num) {
					switches[j] = !switches[j];	//�ݴ�� �ϱ�
				}
			}
			else {	//���л��̸�
				int length = 0;
				while(num-length >= 1 &&	//0���� ��¥��
						num+length <= numSwitch && 
						switches[num-length] == switches[num+length]) {
					length++;
				}
				length--;
				for(int j = num-length; j <= num+length; j++) {
					switches[j] = !switches[j];	//�ݴ�� �ϱ�
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
