import java.io.*;
import java.util.*;

public class B14725{
	
	static StringBuilder sb;
	
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		sb = new StringBuilder("");
		
		int numAnt = Integer.parseInt(in.readLine());	//���̵��� ����
		List<String>[] ants = new LinkedList[numAnt];
		
		int numDepth;
		String target = null;
		for(int i = 0; i < numAnt; i++) {
			ants[i] = new LinkedList<>();
			st = new StringTokenizer(in.readLine(), " ");	//���� ���� �ޱ�
			numDepth = Integer.parseInt(st.nextToken());	//���̰� ������ ���� ����
			for(int j = 0; j < numDepth; j++) {
				//���̰� ������ �� ��ŭ ó��������
				ants[i].add(st.nextToken());
			}
		}
		//��� �Է� ����
		Trie root = new Trie();	//���ο� Ʈ���̸� ����
		Trie curr = null;	//���� ���� �ִ� ����Ʈ����
		String food, lastFood;
		for(int ant = 0; ant < numAnt; ant++) {
			curr = root;	//�ϴ� ��Ʈ���� �����ؼ�
			int limit = limit=ants[ant].size()-1;
			for(int i = 0; i < limit; i++) {
				//������ ���� �� ������
				food = ants[ant].get(i);	//�ش� ������ �����ͼ�
				if( !curr.child.containsKey(food) ) {
					//������ ���� ������
					curr.child.put(food, new Trie());	//���� Ʈ���� �ֱ�
				}
				curr = curr.child.get(food);	//����Ʈ���� ��������
			}
			//������ ���� ���� ó��
			lastFood = ants[ant].get(limit);	//������ ���� ��������
			//������ ���� ������
			curr.child.put(lastFood, new Trie());	//���� Ʈ���� �ֱ�
			curr = curr.child.get(lastFood);	//������ �������� ��������
			curr.isEnd = true;	//�������̶�� ǥ���ϱ�
		}
		//Ʈ���� ó�� �Ϸ�
		
		search(root, 0);	//root���� depth0���� �־ �˻�����
		System.out.println(sb.toString());
	}
	
	static void search(Trie trie, int depth) {
		Set<Map.Entry<String, Trie>> sets = trie.child.entrySet();
		for(Map.Entry<String, Trie> sub: sets) {
			StringBuilder builder = new StringBuilder("");
			for(int i = 0; i < depth; i++) {
				builder.append("--");	//depth��ŭ �־��ֱ�
			}
			builder.append(sub.getKey()).append('\n');	//key���� �־��ְ�
			sb.append(builder.toString());
			search(sub.getValue(), depth+1);	//����Ʈ���� ����
		}
	}
	
	static class Trie{
		boolean isEnd;
		TreeMap<String, Trie> child;	//���� ������ ���� TreeMap���� ����
		
		public Trie() {
			this.isEnd = false;
			this.child = new TreeMap<>();
		}
	}
}