import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.*;

public class Gui extends JFrame {
	private int length,width;
	JButton random,start,pause,next,clear;
	JLabel generation;
	JButton[][] cell;
	int running;
	Map map;
	Thread thread;
	
	public Gui(int length,int width) {
		this.length=length;
		this.width=width;
		map=new Map(length,width);
		
		//添加按钮和标签
		JPanel backPanel, centerPanel, bottomPanel;
        backPanel = new JPanel(new BorderLayout());
        centerPanel = new JPanel(new GridLayout(length,width));
        bottomPanel = new JPanel();
        this.setContentPane(backPanel);
        backPanel.add(centerPanel, "Center");
        backPanel.add(bottomPanel, "South");

        cell=new JButton[this.length][this.width];
        for(int i=0;i<length;i++) {
        	for(int j=0;j<width;j++) {
        		cell[i][j]=new JButton("");
        		cell[i][j].setBackground(Color.WHITE);
        		centerPanel.add(cell[i][j]);
        	}
        }
        for(int i=0;i<length;i++) {
        	for(int j=0;j<width;j++) {
        		cell[i][j].addActionListener(new a());
        	}
        }
		
		random=new JButton("初始化");
		start=new JButton("开始繁衍");
		pause=new JButton("暂停繁衍");
		next=new JButton("下一代");
		clear=new JButton("清空");
		bottomPanel.add(random);
        bottomPanel.add(start);
        bottomPanel.add(pause);
        bottomPanel.add(next);
        bottomPanel.add(clear);

		generation=new JLabel("0");
		bottomPanel.add(generation);
		
		running=0;
		//注册监听器
		random.addActionListener(new a1());
		next.addActionListener(new a2());
		clear.addActionListener(new a3());
		start.addActionListener(new a4());
		pause.addActionListener(new a5());
		//设置窗体
		setSize(1000,650);
		this.setResizable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
//展示细胞图当前状态
	public void showCell() {
		int[][] t=map.getCell();
		for(int i=0;i<length;i++) {
			for(int j=0;j<width;j++) {
				if(t[i+1][j+1]==1)
					cell[i][j].setBackground(Color.BLACK);
				else
					cell[i][j].setBackground(Color.WHITE);
			}
		}
	}
	
	
//手动设置细胞
	class a implements ActionListener{
		public void actionPerformed(ActionEvent e0) {
			if(running==0) {
				for(int i=0;i<length;i++) {
					for(int j=0;j<width;j++) {
						if(e0.getSource()==cell[i][j]) {
							map.setCell(i+1,j+1);
							showCell();
							if(map.getGeneration()==0) {
								map.setGeneration(1);
								generation.setText(""+map.getGeneration());
							}
							return;
						}
					}
				}
			}
		}
	}
	
//随机初始化细胞图	
	class a1 implements ActionListener{
		public void actionPerformed(ActionEvent e1) {
			map.randomCell();
			showCell();
			generation.setText(""+map.getGeneration());
			running=0;
		}
	}
	
//繁衍下一代（手动操作，每点击一次繁衍一代）
	class a2 implements ActionListener{
		public void actionPerformed(ActionEvent e2) {
			map.multiply();
			showCell();
			generation.setText(""+map.getGeneration());
		}
	}
	
//清空细胞图
	class a3 implements ActionListener{
		public void actionPerformed(ActionEvent e3) {
			map.clearCell();
			showCell();
			generation.setText(""+map.getGeneration());
			running=0;
		}
	}
	
//自动繁衍
	class a4 implements ActionListener{
		public void actionPerformed(ActionEvent e4) {
			if(running==0) {
				running=1;
				if(map.getGeneration()>=1) {
					thread=new Thread(new Runnable() {
						public void run() {
							while(running==1) {
								map.multiply();
								showCell();
								generation.setText(""+map.getGeneration());
								try {
										Thread.sleep(250);
	                        		} catch (InterruptedException e) {
	                        			e.printStackTrace();
	                        		}
					
							}
						}
					});
					thread.start();
				}
			}
		}
	}
//暂停自动繁衍
	class a5 implements ActionListener{
		public void actionPerformed(ActionEvent e5) {
			running=0;
		}
	}
}


	