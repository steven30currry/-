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
		
		//��Ӱ�ť�ͱ�ǩ
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
		
		random=new JButton("��ʼ��");
		start=new JButton("��ʼ����");
		pause=new JButton("��ͣ����");
		next=new JButton("��һ��");
		clear=new JButton("���");
		bottomPanel.add(random);
        bottomPanel.add(start);
        bottomPanel.add(pause);
        bottomPanel.add(next);
        bottomPanel.add(clear);

		generation=new JLabel("0");
		bottomPanel.add(generation);
		
		running=0;
		//ע�������
		random.addActionListener(new a1());
		next.addActionListener(new a2());
		clear.addActionListener(new a3());
		start.addActionListener(new a4());
		pause.addActionListener(new a5());
		//���ô���
		setSize(1000,650);
		this.setResizable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
//չʾϸ��ͼ��ǰ״̬
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
	
	
//�ֶ�����ϸ��
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
	
//�����ʼ��ϸ��ͼ	
	class a1 implements ActionListener{
		public void actionPerformed(ActionEvent e1) {
			map.randomCell();
			showCell();
			generation.setText(""+map.getGeneration());
			running=0;
		}
	}
	
//������һ�����ֶ�������ÿ���һ�η���һ����
	class a2 implements ActionListener{
		public void actionPerformed(ActionEvent e2) {
			map.multiply();
			showCell();
			generation.setText(""+map.getGeneration());
		}
	}
	
//���ϸ��ͼ
	class a3 implements ActionListener{
		public void actionPerformed(ActionEvent e3) {
			map.clearCell();
			showCell();
			generation.setText(""+map.getGeneration());
			running=0;
		}
	}
	
//�Զ�����
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
//��ͣ�Զ�����
	class a5 implements ActionListener{
		public void actionPerformed(ActionEvent e5) {
			running=0;
		}
	}
}


	