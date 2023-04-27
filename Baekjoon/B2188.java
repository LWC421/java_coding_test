import java.io.*;
import java.util.*;


public class B2188{

	static int numCow;
	static int numHouse;
	
	static LinkedList<Integer>[] favorites;
	
	static int[] cowToHouse;
	static int[] houseToCow;
	static boolean[] visited;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception{
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");
		
		numCow = Integer.parseInt(st.nextToken());
		numHouse = Integer.parseInt(st.nextToken());
		
		favorites = new LinkedList[numCow+1];
		
		cowToHouse = new int[numCow+1];
		houseToCow = new int[numHouse+1];
		visited = new boolean[numCow+1];
		
		for(int i = 1; i <= numCow; i++) {
			favorites[i] = new LinkedList<Integer>();
			
			st = new StringTokenizer(in.readLine(), " ");
			int numFavorite = Integer.parseInt(st.nextToken());
			
			for(int j = 0; j < numFavorite; j++) {
				favorites[i].add(Integer.parseInt(st.nextToken()));
			}
		}
		
		//�Է� ����
		
		int result = 0;
		for(int i = 1; i <= numCow; i++) {
			if(cowToHouse[i] == 0) {
				//������ �Ұ� ������ ��簡 ������
				for(int j = 1; j <= numCow; j++) {
					//�ϴ� ������ �ҵ��� ����� �� ���ٰ� ǥ��
					visited[j] = false;
				}
				if(dfs(i)) {
					//��Ī�غ��� ��Ī�� �Ǿ����� ������� �߰�����
					result++;
				}
			}
		}
		
		System.out.println(result);
	}
	
	public static boolean dfs(int x) {
		List<Integer> houses = favorites[x];
		visited[x] = true;	//�ش� �Ҵ� �������̴�
		
		for(int h: houses) {
			//h : x�Ұ� ���� �;��ϴ� ��縦 �ǹ��Ѵ�
			if(houseToCow[h] == 0 || !visited[houseToCow[h]] && dfs(houseToCow[h])) {
				//���� �ƹ��� ����ϰ� ���� �ʴٸ�
				// �Ǵ� 
				cowToHouse[x] = h;	//x�Ҵ� h�� �ְ�
				houseToCow[h] = x;	//h��翡�� x�Ұ� �ִٰ� ǥ��
				
				return true;
			}
		}
		
		
		//��Ī ����
		return false;
	}
}