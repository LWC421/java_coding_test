import java.io.*;
import java.util.*;

public class B10775 {

	static int[] parents;
	static boolean[] used;
	
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		int numGate = Integer.parseInt(in.readLine());		//����Ʈ�� ����
		int numAirplane = Integer.parseInt(in.readLine());	//������� ����
		
		int num;
		int[] gateWants = new int[numAirplane];	//�������� ���ϴ� ����
		for(int i = 0; i < numAirplane; i++) {
			num = Integer.parseInt(in.readLine());	//1 ~ num�߿� �־�� �ȴ�
			gateWants[i] = num;
		}
		//�Է� ����
		
		parents = new int[numGate + 1];		//���� ���� �Ǵ��� ǥ��
		used = new boolean[numGate + 1];	//���������
		
		for(int i = 0; i <= numGate; i++) {
			parents[i] = i;	//�ϴ� �ڱ��ڽ��� root��
		}
		
		int count = 0;
		for(int gate: gateWants) {
			//�ϳ��ϳ��� �����鿡 ����
			int curr = gate;
			while(used[curr]) {
				//������̸�
				curr = find(curr);
			}
			if(curr == 0) {
				//������ �͹�������
				break;
			}
			used[curr] = true;	//������̶�� ǥ��
			union(gate, curr-1);
			count++;
		}
		System.out.println(count);
	}
	
	public static int find(int x) {
		if(parents[x] == x) {
			return x;
		}
		
		return parents[x] = find(parents[x]);
	}
	
	public static void union(int x, int y) {
		x = find(x);
		y = find(y);
		
		parents[x] = y;
	}
}
