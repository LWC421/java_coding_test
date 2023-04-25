import java.io.*;
import java.util.*;

public class B1102 {

	static int numBal;			//�������� ����
	static int[][] weights;		//�� �����Ҹ� ��ġ�µ� �ʿ��� ���� ����
	static int targetActive;	//��ǥ�ϴ� ���� ������
	static int[] dp;
	
	static final int LIMIT = 16 * 36 + 1;
	
	static final int MSB = 15;	//�������� ������ �ִ� 16���̹Ƿ�
	
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		int activeCount = 0;
		
		numBal = Integer.parseInt(in.readLine());
		weights = new int[numBal][numBal];
		
		int all = (1 << numBal) + 1;
		dp = new int[all];
		
		for(int from = 0; from < numBal; from++) {
			//�����ҵ� ���� ��������
			st = new StringTokenizer(in.readLine());
			
			for(int to = 0; to < numBal; to++) {
				weights[from][to] = Integer.parseInt(st.nextToken());
			}
		}
		char[] line = in.readLine().toCharArray();
		
		int initActive = 0;
		for(int i = 0; i < numBal; i++) {
			//������ on/off �ޱ�
			if(line[i] == 'Y') {
				initActive = (initActive | (1 << i));
				activeCount++;
			}
		}
		
		targetActive = Integer.parseInt(in.readLine());
		//�Է� ����
		
		if(activeCount >= targetActive) {
			//�̹� ������ �����ϸ�
			System.out.println('0');
			return;
		}
		
		if(activeCount == 0) {
			System.out.println(-1);
			return;
		}
		
		for(int i = 0; i < all; i++) {
			dp[i] = LIMIT;
		}
		
		//���� �����ҿ� ������
		int weight = dp(initActive);
		
		System.out.println(weight);
	}
	
	public static int dp(int actived) {
		int count = 0;
		
		//���� ���� �������� ���� üũ
		for(int i = 0; i < numBal; i++) {
			if( (actived & (1 << i)) != 0 ) {
				count++;
			}
		}
		//��ǥ ������ ��ŭ �� ������
		if(count == targetActive) {
			return 0; //�� �̻��� ����� ����
		}
		
		//����� ���� ������
		if(dp[actived] != LIMIT) {
			return dp[actived];
		}
		
		int nActived;
		for(int i = 0; i < numBal; i++) {
			//������ �����ҿ� ����
			if( (actived & (1 << i)) == 0) {
				//���� ���� �� �� ���̸�
				int minWeight = 36;
				nActived = actived | (1 << i);
				for(int j = 0; j < numBal; j++) {
					if( (actived & (1 << j)) != 0) {
						//���� �����ҿ��� nActived�� ���� �ּ��� ����� ����غ���
						minWeight = Math.min(minWeight, weights[j][i]);
					}
				}
				
				dp[actived] = Math.min(dp[actived], dp(nActived) + minWeight) ;
			}
		}
		
		return dp[actived];
	}
}
