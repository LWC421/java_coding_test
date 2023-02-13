import java.io.BufferedReader;
import java.io.InputStreamReader;
public class B2164 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int target = Integer.parseInt(in.readLine());
		int off = 0;
		for(int i = 19; i >= 0; i--) {
			if( ( target & (1 << i) ) > 0) {	//���� ���� ��Ʈ ã��
				off = target ^ (1 << i);	//���� ���� ��Ʈ ����
				if(off == 0) {	//���� ���� ��Ʈ ������ 0�̸� �Է� �� ���
					System.out.print(target);	
					return;
				}	//2 * ���� ���� bit����
				System.out.print(2*off);
				return;
			}
		}
	}
}