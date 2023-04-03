import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class B9205 {
	
	static final int MAX_DISTANCE = 50*20;	//50���͸��� �ϳ� * �ִ� 20�� ����
	
	static boolean isHappy;			//�ູ ����
	static boolean[] visited;		//�湮 ����
	static boolean[][] isConnected;	//�Ÿ�
	static int length;				//������ + 2

	public static void main(String[] args) throws Exception{
		// ����� ������ ���
		// ���� �� �ڽ�(20�� �������)
		// 50���͸��� �� ���� ���Ŵ�
		// �� �ڽ����� �ִ� 20�������� ��� �� �� �ִ�
		// ���������� ���ָ� ������ �� �ִ�
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder("");
		StringTokenizer st = null;
		
		int T = Integer.parseInt(in.readLine());
		for(int test_case = 1; test_case <= T; test_case++) {
			//�׽�Ʈ ���̽� ����
			int numConv = Integer.parseInt(in.readLine());
			length = numConv+2;
			isHappy = false;	//�� �ູ�ϴ� �Ф�
			

			//0 -> ������, 1 ~ numConv -> ������, numConv+1 -> �佺Ƽ��
			Coord[] points = new Coord[length];
			//������� ��(������) �ޱ�
			st = new StringTokenizer(in.readLine());
			points[0] = new Coord(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			
			//������ �ޱ�
			for(int i = 1; i <= numConv; i++) {
				st = new StringTokenizer(in.readLine());
				points[i] =new Coord(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			}
			
			//�佺Ƽ�� ��ǥ �ޱ�
			st = new StringTokenizer(in.readLine());
			points[numConv+1] = new Coord(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			
			isConnected = new boolean[length][length];
			Coord src, dest;
			int yDistance, xDistance;	//�Ÿ� �����
			for(int y = 0; y < length; y++) {
				src = points[y];
				for(int x = 0; x < length; x++) {
					if(y == x) {
						isConnected[y][x] = false;	//�ڱ��ڽ����δ� ���� ����
						continue;
					}
					dest = points[x];
					yDistance = src.y - dest.y;
					xDistance = src.x - dest.x;
					if(yDistance < 0) yDistance = -yDistance;
					if(xDistance < 0) xDistance = -xDistance;
					if(yDistance + xDistance <= MAX_DISTANCE) {
						//�� �� ������
						isConnected[y][x] = true;	//����Ǿ��ִٰ� ����
					}
				}
			}
			
			for(int mid = 0; mid < length; mid++) {
				for(int start = 0; start < length; start++) {
					for(int end = 0; end < length; end++) {
						if(start == end) continue;
						if(isConnected[start][mid] && isConnected[mid][end]) {
							//�����ؼ� �� �� ������
							isConnected[start][end] = true;
						}
					}
				}
			}
			
			sb.append(isConnected[0][length-1] ? "happy" : "sad").append("\n");
		}
		System.out.println(sb.toString());
	}
	
	static class Coord{
		int y;
		int x;
		public Coord(int y, int x) {
			this.y = y;
			this.x = x;
		}
	}
}
