package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameFrame extends JFrame implements ActionListener
{
    //按钮
    private JPanel backPanel,centerPanel,bottomPanel;
    private JButton[][] btnCell;
    private JButton btnRestart,btnAutoProduce,btnStop;
    private GameService GameS;
    private GameLogic GameL;
    private Thread thread;
    public static void main(String[] args)
    {
        new GameFrame();
    }
    public GameFrame()
    {
        super("生命游戏");
        initFrame();
    }
    // 初始化界面
    public void initFrame()
    {
        GameS = new GameService();
        GameL = new GameLogic(GameS.getRow(),GameS.getCol());
        //边框
        backPanel = new JPanel(new BorderLayout());
        this.setContentPane(backPanel);
        // 网格
        centerPanel = new JPanel(new GridLayout(GameS.getRow(), GameS.getCol()));
        backPanel.add(centerPanel,"Center");
        // 按钮
        bottomPanel = new JPanel();
        backPanel.add(bottomPanel,"South");
        // 创建细胞按钮
        btnCell = new JButton[GameS.getRow()][GameS.getCol()];
        // 初始化网格按钮数组以表示细胞
        for (int i = 0; i < GameS.getRow(); i++)
        {
            for (int j = 0; j < GameS.getCol(); j++)
            {
                btnCell[i][j] = new JButton("");
                // 细胞为白色
                btnCell[i][j].setBackground(Color.WHITE);
                // 添加细胞按钮到面板
                centerPanel.add(btnCell[i][j]);
            }
        }
        // 初始化按钮
        btnRestart = new JButton("初始化");
        bottomPanel.add(btnRestart);

        // 自动繁衍按钮
        btnAutoProduce = new JButton("自动繁衍");
        bottomPanel.add(btnAutoProduce);

        // 暂停按钮
        btnStop = new JButton("暂停");
        bottomPanel.add(btnStop);

        // 监听器
        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosed(WindowEvent e)
            {
                System.exit(0);
            }
        });

        btnRestart.addActionListener(this);
        btnAutoProduce.addActionListener(this);
        btnStop.addActionListener(this);

        // 设置窗体大小
        this.setSize(750,750);
        // 窗体大小可变
        this.setResizable(true);
        // 窗体居中显示
        this.setLocationRelativeTo(null);
        // 窗体可见
        this.setVisible(true);
    }

    // 监听事件处理
    public void actionPerformed(ActionEvent e)
    {
        // 初始化
        if(e.getSource() == btnRestart)
        {
            // 随机初始化一部分细胞状态为1
            GameL.randomInit();
            GameS.setIsAlive(GameL.getIsAlive());
            // 显示细胞
            showCell();
            // 未在自动繁衍
            GameS.setIsRunning(false);
        }
        // 自动繁衍
        else if (e.getSource() == btnAutoProduce)
        {
            // 正在自动繁衍
            GameS.setIsRunning(true);
            // 自动繁衍
            autoUpdate();
        }
        // 暂停
        else if (e.getSource() == btnStop)
        {
            // 未在自动繁衍
            GameS.setIsRunning(false);
        }
    }


    // 根据细胞状态显示细胞颜色
    private void showCell()
    {
        int[][] array = GameS.getIsAlive();

        for (int i = 0; i < GameS.getRow(); i++)
        {
            for (int j = 0; j < GameS.getCol(); j++)
            {
                if (array[i + 1][j + 1] == 1)
                {
                    // 活细胞用红色表示
                    btnCell[i][j].setBackground(Color.BLACK);
                }
                else
                {
                    // 死细胞用黑色表示
                    btnCell[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }

    // 生成下一代
    protected void makeNextGeneration()
    {
        // 繁衍
        GameL.update();
        GameS.setIsAlive(GameL.getIsAlive());
        GameS.setIsAllDead(GameL.judgeIsAllDead());
        // 显示细胞
        showCell();
    }

    // 自动繁衍
    private void autoUpdate()
    {
        thread = new Thread(() ->
        {
            while (GameS.getIsRunning())
            {
                // 产生下一代
                makeNextGeneration();
                try
                {
                    Thread.sleep(500);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                if (GameS.getIsAllDead())
                {
                    JOptionPane.showMessageDialog(null, "所有细胞已死亡");
                    GameS.setIsRunning(false);
                    thread = null;
                }
            }
        });
        thread.start();
    }
}

