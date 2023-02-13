import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

public class SWEA5432 {
	public static void main(String[] args) throws Exception {
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
		StringBuilder sb = new StringBuilder("");
		
		int T = Integer.parseInt(in.readLine());
		
		String str = null;
		for(int test_case = 1; test_case <= T; test_case++) {
			str = in.readLine();
			
			char c = '\u0000';
			int strLength = str.length();
			
			//stack�� ���
			//���� �־������� ��Ÿ����
			Stack<Integer> stack = new Stack<>();
			
			int result = 0;
			
			//��ȣ�� ���� ó���ϱ�
			for(int i = 0; i < strLength; i++) {
				c = str.charAt(i);
				if(c == '(') {	//�־�� �ȴ�
					stack.push(i);
				}
				else {	//�ݴ� ��ȣ�̴�
					int left = stack.pop();
					if( (i - left) == 1) {	//1�� �����̸� �������̴�
						result += stack.size();
					}
					else {	//�踷���̴�
						result += 1;
					}
				}
			}
			
			sb.append("#").append(test_case).append(" ").append(result).append("\n");
		}
		
		System.out.println(sb.toString());
	}
}
