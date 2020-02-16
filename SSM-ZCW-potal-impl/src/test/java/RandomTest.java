import org.junit.Test;

import java.util.Random;

/**
 * 标题：
 * 作者：何处是归程
 * 时间：2020/2/8 - 17:37
 */
public class RandomTest {
	@Test
	public void test (){
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <=4 ; i++) {
			int num = random.nextInt(10);
			sb.append(num);
		}
		System.out.println(sb);
	}
}
