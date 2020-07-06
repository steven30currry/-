
public class Map {
	private int length;
	private int width;
	private int[][] cell;
	private int[][] next;
	private int generation;
	
	public Map(int length,int width) {
		this.length=length;
		this.width=width;
		cell=new int[length+2][width+2];
		next=new int[length+2][width+2];
		for(int i=0;i<=length+1;i++)
			for(int j=0;j<=width+1;j++) {
				cell[i][j]=0;
				next[i][j]=0;
			}
		generation=0;
	}
	
	//随机初始化第一代细胞图
	public void randomCell() {
		for(int i=1;i<=length;i++)
			for(int j=1;j<=width;j++)
				cell[i][j]=Math.random()>0.7?1:0;
		generation=1;
	}
	
	
	//自行设置细胞
	public void setCell(int i,int j) {
		if(cell[i][j]==0)
			cell[i][j]=1;
		else
			cell[i][j]=0;
	}
	
	//设置代数
	public void setGeneration(int i) {
		generation=i;
	}
	
	
	//繁衍下一代细胞
	public void multiply() {
		for(int i=1;i<=length;i++)
			for(int j=1;j<=width;j++) {
				switch(getNeighborCount(i,j)) {
					case 2:next[i][j]=cell[i][j];break;
					case 3:next[i][j]=1;break;
					default:next[i][j]=0;
				}
			}
		for(int i=1;i<=length;i++)
			for(int j=1;j<=width;j++)
				cell[i][j]=next[i][j];
		generation=generation+1;
	}
	//求邻居数量
	private int getNeighborCount(int i,int j) {
		int n=0;
		for(int x=i-1;x<=i+1;x++)
			for(int y=j-1;y<=j+1;y++)
				n=n+cell[x][y];
		n=n-cell[i][j];
		return n;
	}
	
	//清零
	public void clearCell() {
		for(int i=0;i<=length+1;i++)
			for(int j=0;j<=width+1;j++) {
				cell[i][j]=0;
				next[i][j]=0;
			}
		generation=0;
	}
	//外界获取细胞代数信息
	public int getGeneration() {
		return generation;
	}
	//外界获取细胞图信息
	public int[][] getCell() {
		return cell;
	}
}
