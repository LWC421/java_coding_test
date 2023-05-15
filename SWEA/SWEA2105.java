import java.io.*;
import java.util.*;


public class SWEA2105 {
	// N*N�� [4 ~ 20]
	// �� ���ڴ� ����Ʈ�� ����
	
	// �밢�� �������� ������ �� �ִ�
	// �簢���� �׷� ó������ ���ƿ;� �Ѵ�
	
	// ���� ���ڸ� ������ �ȵȴ�
	// �ϳ��� ���ڸ� ������ �͵� �ȵȴ�
	// ����ߴ� �Ŵ� �� ��� �ȵȴ�
	
	// �������� �ƹ����̳� �ȴ�
	
	//4�밢 �̵���
	final static int[] dy = {1, 1, -1, -1};
	final static int[] dx = {1, -1, -1, 1};

	static int mapLength;		//���� ũ��
	static int[][] map;			//��
	static Set<Integer> used;	//���� ���� �����
	
	public static void main(String[] args) throws Exception {
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;
		used = new HashSet<>();
		int max = -1;
		
		int TC = Integer.parseInt(in.readLine());
		
		for(int test_case = 1; test_case <= TC; test_case++) {
			//�׽�Ʈ ���̽� ����
			mapLength = Integer.parseInt(in.readLine());	//�� ũ��
			map = new int[mapLength][mapLength];		//�� �ʱ�ȭ
			max = -1;
//			used.clear();								//����� ���ڵ� �ʱ�ȭ
			
			for(int y = 0; y < mapLength; y++) {
				st = new StringTokenizer(in.readLine(), " ");	//�ٸ��� �Է�
				for(int x = 0; x < mapLength; x++) {
					map[y][x] = Integer.parseInt(st.nextToken());
				}
			}
			//�Է� ����
			
			//�� ĭ�� ���ؼ� �˻縦 �غ���
			for(int y = 0; y < mapLength; y++) {
				for(int x = 0; x < mapLength; x++) {
					//���簢���� �� ���� �ٲٸ鼭 �̵��Ѵ�
					for(int rb = 1; rb < mapLength; rb++) {
						for(int lb = 1; lb < mapLength; lb++) {
							if(goRect(y, x, rb, lb)) {
								//���������� �簢���� �׷�����
//								System.out.printf("[%d, %d], %d, %d\n", y, x, rb, lb);

								if(max < used.size()) {
									//�� ū ���̿����� �������ֱ�
									max = used.size();
								}
							}
							//��·�� �������Ƿ� used�� �ʱ�ȭ
							used.clear();
						}
					}
				}
			}
			
			
			sb.append(String.format("#%d %d\n", test_case, max));
		}
		
		System.out.println(sb.toString());
	}

	/**
	 * 
	 * @param initY ù ���� Y
	 * @param initX ù ���� X
	 * @param rb ������ ������ ���� ���� ����, (1 <= )
	 * @param lb ���� �����η� ���� ���� ����, (1 <= )
	 * @return	������ ��� true, ���� �� ��� false
	 */
	public static boolean goRect(int initY, int initX, int rb, int lb) {
		int currY = initY;
		int currX = initX;	//ù ��ġ�� ����ְ�
		
		//������ ������ �̵�
		for(int i = 0; i < rb; i++) {
			currY++;
			currX++;
			if(currY >= mapLength || currX >= mapLength || currY < 0 || currX < 0) {
				return false;
			}
			
			if( !check(currY, currX)) {
				//�ش� ĭ���� üũ�ϰ� �������� ���ڸ� �־�����
				return false;	//�������� false�� ��ȯ
			}
		}
		
		//���� ��
		for(int i = 0; i < lb; i++) {
			currY++;
			currX--;
			if(currY >= mapLength || currX >= mapLength || currY < 0 || currX < 0) {
				return false;
			}
			
			if( !check(currY, currX)) {
				//�ش� ĭ���� üũ�ϰ� �������� ���ڸ� �־�����
				return false;	//�������� false�� ��ȯ
			}
		}
		
		//���� ��
		for(int i = 0; i < rb; i++) {
			currY--;
			currX--;
			if(currY >= mapLength || currX >= mapLength || currY < 0 || currX < 0) {
				return false;
			}
			
			if( !check(currY, currX)) {
				//�ش� ĭ���� üũ�ϰ� �������� ���ڸ� �־�����
				return false;	//�������� false�� ��ȯ
			}
		}
		
		//������ ��
		for(int i = 0; i < lb; i++) {
			currY--;
			currX++;
			if(currY >= mapLength || currX >= mapLength || currY < 0 || currX < 0) {
				return false;
			}
			
			if( !check(currY, currX)) {
				//�ش� ĭ���� üũ�ϰ� �������� ���ڸ� �־�����
				return false;	//�������� false�� ��ȯ
			}
		}
		
		return true;
	}
	
	public static boolean check(int y, int x) {
		if(used.contains(map[y][x])) {
			//���� ��ġ��
			return false;
		}
		
		used.add(map[y][x]);	//ó�� ���� ���ڸ� �־��ֱ�
		return true;
	}
}
