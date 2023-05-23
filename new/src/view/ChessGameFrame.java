package view;

import controller.GameController;
import model.Chessboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    boolean isSelected = false;
    private final int ONE_CHESS_SIZE;

    private ChessboardComponent chessboardComponent;

    public ChessGameFrame(int width, int height) {
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addChessboard();
        addLabel();
        addRuleButton();
        addRestartButton();
        addRegretButton();
        addOfferLoseButton();
        addImage();
    }

    //加入重新开始按钮
    private void addRestartButton() {
        JButton button = new JButton("Restart");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //在此写入重新开局的方法

            }
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    public void addRuleButton() {
        JButton button = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g.create();

                // 设置圆形的位置和大小
                int circleSize = Math.min(getWidth(), getHeight()) - 10;
                int x = (getWidth() - circleSize) / 2;
                int y = (getHeight() - circleSize) / 2;

                // 绘制外部圆形边框
                g2d.setColor(Color.BLUE);
                g2d.setStroke(new BasicStroke(2)); // 设置边框线条粗细
                g2d.drawOval(x, y, circleSize, circleSize);

                // 绘制圆形背景
                g2d.setColor(Color.ORANGE);
                g2d.fillOval(x, y, circleSize, circleSize);

                // 绘制问号符号
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Arial", Font.BOLD, 40));
                g2d.drawString("?", getWidth() / 2 - 10, getHeight() / 2 + 15);

                g2d.dispose();
            }
        };
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RuleFrame ruleFrame = new RuleFrame(400, 400);
                ruleFrame.setVisible(true);
                isSelected=true;
            }
        });
        button.setBorderPainted(false); // 移除边框
        button.setContentAreaFilled(false); // 移除内容区域填充
        button.setLocation(HEIGTH, HEIGTH / 10 + 25);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }


    private void addImage() {
        ImageIcon icon1 = new ImageIcon("resource\\bg.png");
        //创建一个JLabel的对象（管理容器）
        JLabel jLabel1 = new JLabel(icon1);
        //图片位置
        jLabel1.setBounds(-10, 0, 1200, 800);
        this.add(jLabel1);
        this.getContentPane().add(jLabel1);
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
    }

    private void addLabel() {
        JLabel statusLabel = new JLabel("新局");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }


    private void addOfferLoseButton() {
        JButton button1 = new JButton("Surrender");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //在此写入投降的方法

            }
        });
        button1.setLocation(HEIGTH, HEIGTH / 10 + 500);
        button1.setSize(200, 60);
        button1.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button1);
    }

    private void addRegretButton() {
        JButton button1 = new JButton("Regret");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //在此写入悔棋的方法

            }
        });
        button1.setLocation(HEIGTH, HEIGTH / 10 + 300);
        button1.setSize(200, 60);
        button1.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button1);
    }


//    private void addLoadButton() {
//        JButton button = new JButton("Load");
//        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
//        button.setSize(200, 60);
//        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(button);
//
//        button.addActionListener(e -> {
//            System.out.println("Click load");
//            String path = JOptionPane.showInputDialog(this,"Input Path here");
//            gameController.loadGameFromFile(path);
//        });
//    }


}