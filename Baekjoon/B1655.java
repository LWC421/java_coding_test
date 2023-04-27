import java.io.*;
import java.util.*;

public class B1655 {
	
	public static void main(String[] args) throws Exception{

//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");

		PriorityQueue<Integer> smalls = new PriorityQueue<>((a, b) -> {
			return b-a;
		}) ;
		
		PriorityQueue<Integer> bigs = new PriorityQueue<>((a, b) -> {
			return a-b;
		}) ;
		
		int numNumber = Integer.parseInt(in.readLine());
		int[] inputs = new int[numNumber];
		
		for(int i = 0; i < numNumber; i++) {
			inputs[i] = Integer.parseInt(in.readLine());
		}
		//�Է� ����
		if(numNumber == 1) {
			System.out.println(inputs[0]);
			return;
		}
		
		smalls.add(inputs[0]);	//ó������ �����ſ� �ְ� ����
		sb.append(inputs[0]).append('\n');
		
		
		if(inputs[0] <= inputs[1]) {
			bigs.add(inputs[1]);
			sb.append(inputs[0]).append('\n');
		} else {
			bigs.add(smalls.poll());
			smalls.add(inputs[1]);
			sb.append(inputs[1]).append('\n');
		}
		
		for(int i = 2; i < numNumber; i++) {
			if(bigs.peek() >= inputs[i]) {
				//ū ������ ���� ���� ������, ���� ���� ������ 
				smalls.add(inputs[i]);	//�ϴ� �����ʿ� �ְ�
			} else {
				bigs.add(inputs[i]);
			}
			
			if(smalls.size() - bigs.size() > 1) {
				//������ ������ �� ������
				bigs.add(smalls.poll());
			}
			if(bigs.size() - smalls.size() > 0) {
				//ū�� ������ �� ������
				smalls.add(bigs.poll());
			}
			
			sb.append(smalls.peek()).append('\n');
		}
		System.out.println(sb.toString());
	}
}
