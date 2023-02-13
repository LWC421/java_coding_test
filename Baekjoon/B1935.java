
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

//후위 계산기
public class B1935 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		int count = Integer.parseInt(in.readLine());
		//한글자씩 자르기
		String[] str = in.readLine().split("");

		//일단 숫자 입력 받기
		double[] numbers = new double[count];
		for(int i = 0; i < count; i++) {
			numbers[i] = Double.parseDouble(in.readLine());
		}
		
		//입력 받은 식 순회하기
		Stack<Double> stack = new Stack<>();	//결과값이 정수가 아닌 값이 나올 수 있다
		String target = null;
		int strLength = str.length;
		for(int i = 0; i < strLength; i++) {
			target = str[i];
			//연산자 인지 확인
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
			else {	//숫자인 경우이다 -> 숫자로 넣어주기
				stack.push(numbers[target.charAt(0) - 'A']);
			}
		}
		
		System.out.printf("%.2f", stack.pop());
	}
}
