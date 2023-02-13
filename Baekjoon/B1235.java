import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class B1235 {

	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		Trie t = new Trie();
		int N = Integer.parseInt(in.readLine());
		for(int i = 0; i < N; i++) {
			t.add(in.readLine());
		}
		
		System.out.println(t.max);
	}
	
	static class Trie{
		int max = 1;
		Map<String, Map> root = new HashMap<>();
		
		//7�ڸ��� �л��� ��ȣ�� ���´�
		void add(String str) {
			//�������� ��ȸ
			Map<String, Map> current = root;
			String t;
			int length = str.length();
			for(int i = length-1; i >= 0; i--) {
				t = String.valueOf(str.charAt(i));
				if(current.containsKey(t)) {
					//������ ������ -> ���� �ڸ��������� �ߺ��̶�� ���̴�
					max = Math.max(max, (length+1) - i);	//max�� ����
				}
				else {
					//�� ������ ������ -> �ߺ����� �ʴ´ٴ� ���̴�
					//���� ���������
					current.put(t, new HashMap<String, Map>());
				}
				current = current.get(t);
			}
		}
	}
}
