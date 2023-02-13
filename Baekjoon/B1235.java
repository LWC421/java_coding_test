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
		
		//7자리인 학생의 번호가 들어온다
		void add(String str) {
			//역순으로 순회
			Map<String, Map> current = root;
			String t;
			int length = str.length();
			for(int i = length-1; i >= 0; i--) {
				t = String.valueOf(str.charAt(i));
				if(current.containsKey(t)) {
					//가지고 있으면 -> 현재 자리수까지는 중복이라는 뜻이다
					max = Math.max(max, (length+1) - i);	//max값 갱신
				}
				else {
					//안 가지고 있으면 -> 중복되지 않는다는 뜻이다
					//새로 만들어주자
					current.put(t, new HashMap<String, Map>());
				}
				current = current.get(t);
			}
		}
	}
}
