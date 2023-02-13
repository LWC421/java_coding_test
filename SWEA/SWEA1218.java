import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

public class SWEA1218 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));

		int T = 10;
		StringBuilder sb = new StringBuilder("");
		for(int test_case = 1; test_case <= T; test_case++) {
			in.readLine();
			String str = in.readLine();

			Stack<String> stack = new Stack<>();
			int strLength = str.length();
			boolean flag = true;

			//���� �ϳ��ϳ� �˻��غ���
			for(int i = 0; i < strLength; i++) {
				char target = str.charAt(i);

				//�ݴ� ��ȣ�� ���� �˻�
				if(target == '}') {
					if(stack.isEmpty()) {
						flag = false;
						break;
					}
					if( !stack.pop().equals("{") ) {
						flag=  false;
						break;
					}
				}
				else if(target == ']') {
					if(stack.isEmpty()) {
						flag = false;
						break;
					}
					if( !stack.pop().equals("[") ) {
						flag=  false;
						break;
					}
				}
				else if(target == ')') {
					if(stack.isEmpty()) {
						flag = false;
						break;
					}
					if( !stack.pop().equals("(") ) {
						flag=  false;
						break;
					}
				}
				else if(target == '>') {
					if(stack.isEmpty()) {
						flag = false;
						break;
					}
					if( !stack.pop().equals("<") ) {
						flag=  false;
						break;
					}
				}
				else {
					stack.push(String.valueOf(target));
				}
			}

			//			������ ������ ����� -> �ùٸ� ��ȣ��
			if(flag && stack.isEmpty()) {
				sb.append("#").append(test_case).append(" ").append(1).append("\n");
			}
			else {
				sb.append("#").append(test_case).append(" ").append(0).append("\n");
			}
		}

		System.out.println(sb.toString());
	}
}
