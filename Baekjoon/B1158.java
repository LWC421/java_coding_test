import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B1158 {
	public static void main(String[] args) throws Exception{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken()) - 1;
		
		Circular c = new Circular();
		for(int i = 1; i <= N; i++) {
			c.add(i);
		}
		
		StringBuilder sb = new StringBuilder();
		int[] result = new int[N];
		for(int i = 0; i < N; i++) {
			result[i] = c.get(K);
		}

		sb.append("<");
		
		for(int i = 0; i < N-1; i++) {
			sb.append(result[i]).append(", ");
		}
		sb.append(result[N-1]);
		sb.append(">");
		
		System.out.println(sb.toString());
	}
}

class Circular{
	Node current = null;
	Node tail = null;

	//항상 마지막에 넣기
	public void add(int data) {
		Node tmp = new Node(data, current, tail);
		//비어있을 경우
		if(isEmpty()) {
			current = tmp;
			current.next = current;
			current.prev = current;
			tail = current;
		}
		
		tail.next = tmp;
		current.prev = tmp;
		
		//마지막 위치 변경
		tail = tmp;
	}

	//가져오기
	public int get(int next) {
		for(int i = 0; i < next; i++) {
			current = current.next;
		}

		//연결관계 변경시켜주기
		current.prev.next = current.next;
		current.next.prev = current.prev;
		
		int data = current.data;
		Node tmp = current;
		
		if(current.next == current) {
			current = null;
		}
		else {
			current = current.next;
		}		
		
		return data;
	}
	
	public boolean isEmpty() {
		return current == null;
	}
}

class Node{
	int data;
	Node next;
	Node prev;

	public Node(int data, Node next, Node prev) {
		this.data = data;
		this.next = next;
		this.prev = prev;
	}

	public Node(int data) {
		this.data = data;
	}
}