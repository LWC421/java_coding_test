import java.io.*;
import java.util.*;


public class B10165 {

	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder("");

		int numStop = Integer.parseInt(in.readLine());	//���� �������� ����
		int numLine = Integer.parseInt(in.readLine());	//�뼱�� ����

		List<FromTo> lines = new LinkedList<>();

		//�������� 0������ ����
		int from, to;
		for(int i = 1; i <= numLine; i++) {
			st = new StringTokenizer(in.readLine());

			from = Integer.parseInt(st.nextToken());
			to = Integer.parseInt(st.nextToken());

			if(from > to) {
				if(to != 0) {
					//0�� �����Ҹ� ����ġ�� ���̸� -> 
					int minusFrom = from - numStop;
					lines.add(new FromTo(i, minusFrom, to));
					
					int plusTo = numStop + to;
					lines.add(new FromTo(i, from, plusTo));
				}
				else {
					//0�� �����ҿ��� ������ �� �̸�
					int plusTo = numStop;	//0��� ������ ���������� ǥ��
					lines.add(new FromTo(i, from, plusTo));
				}
			}else {
				lines.add(new FromTo(i, from, to));
			}
		}
		//�Է� ����
		
		//from�� to�� �̿��ؼ� ���� ����
		lines.sort((a, b) -> {
			if(a.from == b.from) {
				return b.to - a.to;
			}
			return a.from - b.from;
		});
		
//		for(FromTo line: lines) {
//			System.out.println(line);
//		}
		
		Stack<FromTo> stack = new Stack<FromTo>();
		
		FromTo top = null;
		for(FromTo line: lines) {
			//�ϳ��ϳ��� �뼱�� ����
			if(stack.empty()) {
				//������ �������� ������ �־߾� �Ѵ�
				stack.add(line);
				continue;
			}
			
			top = stack.peek();	//������ ���� ����
			
			if(line.to <= top.to) {
				//���� ���� �뼱�� �������ΰ� ��ġ��
				continue;
			}
			
			stack.add(line);	//��ġ�� �����Ƿ� �߰�����
		}
		
		TreeSet<Integer> remains = new TreeSet<>();
		
		while( !stack.isEmpty() ) {
			top = stack.pop();
			remains.add(top.id);	//���� �뼱���� id�� ����
		}
		
		for(int id: remains) {
			sb.append(id).append(' ');
		}
		
		System.out.println(sb.toString());
	}
	
	static class FromTo{
		int id;
		int from;
		int to;
		
		public FromTo(int id, int from, int to) {
			this.id = id;
			this.from = from;
			this.to = to;
		}
		
		@Override
		public String toString() {
			return String.format("#%d : %d -> %d", id, from, to);
		}
	}
}

