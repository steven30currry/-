import org.junit.*;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Test;

public class MapTest {
	private static Map map=new Map(60,120);
	
	@After
	public void tearDown() throws Exception{}

//测试初始化细胞图功能
	@Test(timeout=1000)
	public void testRandomCell() {
		int s=0,k=0;
		map.randomCell();
	//遍历细胞图，计算状态为1的细胞的数量
		int[][] cell=map.getCell();
		for(int i=1;i<=60;i++) {
			for(int j=1;j<=120;j++) {
				s=s+cell[i][j];
			}
		}
	//若状态为1的细胞总数为0，则k赋值0，邹泽k赋值1
		if(s==0)
			k=0;
		else
			k=1;
	//完成细胞初始化之后，状态为1的细胞总数应该是不为0的，k应该为1
		assertEquals(1,k);
	}

//测试改变某个细胞状态的功能
	@Test
	public void testSetCell() {
	  //获取细胞[6][6]的状态，赋值给i
		int[][]cell=map.getCell();
		int i=cell[6][6];
	  //改变细胞[6][6]的细胞状态（即变为与原来相反的状态）
		map.setCell(6, 6);
	  //再次获取当前的细胞状态，测试当前细胞[6][6]的状态是否符合预期
		cell=map.getCell();
	  //如果上一次细胞[6][6]的状态为0，则细胞[6][6]当前的状态应该为1，反之为0
		if(i==0) {
			assertEquals(1,cell[6][6]);
		}
		else {
			assertEquals(0,cell[6][6]);
		}
		
	}
//测试改变细胞代数的功能是否正常
	@Test
	public void testSetGeneration() {
	  //将细胞代数设置为6，检测
		map.setGeneration(6);
		assertEquals(6,map.getGeneration());
	}

//测试细胞繁衍功能是否正常
	@Test
	public void testMultiply() {
		int[][] cell=map.getCell(); //取当前细胞状态
	  //根据细胞[6][6]周围的细胞状态判断细胞[6][6]下一代的状态
		int s=0;
		for(int i=5;i<=7;i++) {
			for(int j=5;j<=7;j++) {
				s=s+cell[i][j];
			}
		}
		s=s-cell[6][6];
		int next;
		switch(s) {
			case 2:next=cell[6][6];break;
			case 3:next=1;break;
			default:next=0;
		}
	  //细胞繁衍一代，再次获取细胞状态
		map.multiply();
		cell=map.getCell();
	  //对比刚刚推测的细胞[6][6]的状态与繁衍后真实获得的细胞[6][6]的状态是否相同
		assertEquals(next,cell[6][6]);
	}

//测试细胞清空状态是否正常
	@Test
	public void testClearCell() {
		map.clearCell();
		int cell[][]=map.getCell();
		int s=0;
		for(int i=1;i<=60;i++) {
			for(int j=1;j<=120;j++) {
				s=s+cell[i][j];
			}
		}
		if(s!=0)
			s=1;
		assertEquals(0,s);
	}
}
