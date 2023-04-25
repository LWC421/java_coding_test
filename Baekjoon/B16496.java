import java.io.*;
import java.util.*;

public class B16496 {

	public static void main(String[] args) throws Exception {
//		System.setIn(new FileInputStream("input.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		int numNumber = Integer.parseInt(in.readLine());
		StringTokenizer st = new StringTokenizer(in.readLine(), " ");

		String[] inputs = new String[numNumber];

		for(int i = 0; i < numNumber; i++) {
			inputs[i] = st.nextToken();
		}
		//�Է� ����

		Comparator<String> myComparator = new StringComparator();

		Arrays.sort(inputs, myComparator);

		StringBuilder sb = new StringBuilder("");

		if(inputs[0].equals("0")) {
			//���� �����ߴµ� �� ���� 0�̸� -> ��� 0�̶�� �Ҹ���
			System.out.println("0");
			return;
		}

		for(int i = 0; i < numNumber; i++) {
			sb.append(inputs[i]);
		}

		System.out.println(sb.toString());
	}

	static class StringComparator implements Comparator<String>{
		@Override
		public int compare(String a, String b) {
			String left = a + b;
			String right = b + a;

			int length = left.length();

			for(int i = 0; i < length; i++) {
				if(left.charAt(i) < right.charAt(i)) {
					return 1;
				}
				else if(left.charAt(i) > right.charAt(i)) {
					return -1;
				}
			}

			return 0;
		}
	}
}
