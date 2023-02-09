import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B11723 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		int M = Integer.parseInt(in.readLine());	//�����ؾ��ϴ� Ƚ��

		int result = 0;	//��� ���� 

		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;

		String command = null;
		int number = 0;	//�Է¹޴� ��

		for(int i = 0; i < M ; i++) {
			st = new StringTokenizer(in.readLine(), " ");
			
			command = st.nextToken();
			
			switch (command.charAt(0)) {	//ù ��° ���ڰ�
			case 'r':{ //remove
				number = Integer.parseInt(st.nextToken()) - 1;
				result = result & (Integer.MAX_VALUE ^ 1 << number);
				break;
			}	
			case 'c':{//check
				number = Integer.parseInt(st.nextToken()) - 1;
				sb.append( ((result & (1 << number)) != 0) ? 1 : 0).append("\n");
				break;
			}	
			case 't':{	//toggle
				number = Integer.parseInt(st.nextToken()) - 1;
				result = result ^ (1 << number);
				break;
			}
			case 'e':{	//empty
				result = 0;
				break;
			}
			case 'a':{	//add �Ǵ� all�� ����̴�
				if(command.charAt(1) == 'd') {	//add��ɾ��̴�
					number = Integer.parseInt(st.nextToken()) - 1;
					result = result | (1 << number);
				}
				else {	//all��ɾ��̴�
					result = Integer.MAX_VALUE;
				}
				break;
			}
			}
		}
		
		System.out.println(sb.toString());
	}
}
