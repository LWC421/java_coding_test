
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

//���� ����
public class B1935 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		int count = Integer.parseInt(in.readLine());
		//�ѱ��ھ� �ڸ���
		String[] str = in.readLine().split("");

		//�ϴ� ���� �Է� �ޱ�
		double[] numbers = new double[count];
		for(int i = 0; i < count; i++) {
			numbers[i] = Double.parseDouble(in.readLine());
		}
		
		//�Է� ���� �� ��ȸ�ϱ�
		Stack<Double> stack = new Stack<>();	//������� ������ �ƴ� ���� ���� �� �ִ�
		String target = null;
		int strLength = str.length;
		for(int i = 0; i < strLength; i++) {
			target = str[i];
			//������ ���� Ȯ��
			if(target.equals("*")){
				stack.push(stack.pop() * stack.pop());
			}
			else if(target.equals("/")){
				double right = stack.pop();
				double left = stack.pop();
				stack.push(left / right);
			}
			else if(target.equals("+")){
				stack.push(stack.pop() + stack.pop());
			}
			else if(target.equals("-")){
				double right = stack.pop();
				double left = stack.pop();
				stack.push(left - right);
			}
			else {	//������ ����̴� -> ���ڷ� �־��ֱ�
				stack.push(numbers[target.charAt(0) - 'A']);
			}
		}
		
		System.out.printf("%.2f", stack.pop());
	}
}
