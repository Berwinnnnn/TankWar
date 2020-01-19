package tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;


public class TankFrame extends Frame {
    //单例模式,要用TankFrame，
   public static final TankFrame  INSTANCE = new TankFrame();

    static final int GAME_WIDTH = 800, GAME_HEIGHT = 600;  //游戏窗口大小

    private GameModel gm = new GameModel(); // MVC，Model和View分离

    // 私有 构造方法，只在创建INSTANCE调用
    private TankFrame() {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setLocation(400, 100);
        setTitle("tank war");
        setVisible(true);

        //添加键盘监听，方法在下面
        this.addKeyListener(new MyKeyListener());
        //添加窗口监听，使用匿名内部类
        addWindowListener(new WindowAdapter() {
            @Override  //按x，就退出
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override   //画 方块，模拟坦克 , 画子弹、边界判断、碰撞判断
    public void paint(Graphics g) {
        gm.paint(g);
    }

//    //把事物添加到窗口
//    public  void  add(AbstractGameObject go){
//        //此add是集合中的方法
//        objects.add(go);
//    }
    /* 上面方法把所有事物add了
    //把子弹加载到窗口
    public  void add (Bullet bullet){
        this.bullets.add(bullet);
    }
*/
    //下面的代码是用来消除闪烁
    Image offScreenImage = null;
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

   /* 上面方法把所有事物add了   //方法重载
    //爆炸产生 , 需要加入add()到TankFrame类：    坦克死,会产生爆炸，通过此方法把爆炸加入到TankFrame
    public void add(Explode explode) {
        this.explodes.add(explode);
    }*/

    //键盘监听，
    class MyKeyListener extends KeyAdapter {

        boolean bL = false;//通过上下左右箭头的按键状态，判定坦克的移动方向
        boolean bU = false;
        boolean bR = false;
        boolean bD = false;

        @Override  //移动：按键盘，做相应的操作,高内聚低耦合思想：移动方向由坦克类自己做
        public void keyPressed(KeyEvent e) {
            gm.getMyTank().keyPressed(e);
        }

        @Override  //松开键盘，做相应的操作，
        public void keyReleased(KeyEvent e) {
            gm.getMyTank().keyReleased(e);
        }

    }

    public GameModel getGm() {
        return gm;
    }
}
