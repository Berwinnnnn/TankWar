package tank;

import tank.strategy.FireStrategy;
import tank.strategy.DefaultFireStrategy;
import tank.strategy.FourDirFireStrategy;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Constructor;

public class Player  extends  AbstractGameObject{
    //定义坐标值
    int x = 100, y = 100;
    //炮弹方向 , 即射出炮弹时的方向
    private  Dir dir ;
    //通过上下左右箭头的按键状态:有没有被按下去（有=true  ， 抬起来了=false），判定坦克的移动方向
    private boolean bL , bU , bR , bD= false;
    // 作用：不按方向键是（false）停止， 按下任意方向键应该（true）移动 ；参照move（）、setMainDir（）方法  ；坦克静止是状态，不是方向，不应在Dir类里
    private boolean moving = false;
    //区分敌我
    private Group group;
    //坦克的生命,初始化true，被子弹打中，设置为fasle
    private boolean live = true;

    //定义一个速度，常量
    public   static  final int SPEED = 30 ;

    //构造方法
    public Player(int x  , int y, Dir dir , Group group){
        this.x=x;
        this.y=y;
        this.dir=dir;
        this.group=group;

        //init fire strategy from config
        this.initFireStrategy();
    }

    //设置和获取坐标
    public int getX(){
        return x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return y;
    }

    public void setY(int y){
        this.y = y;
    }

    //获取和设置方向
    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    //获取和设置分组
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group){ this.group = group; }

    //画一个方块，模拟坦克，高内聚低耦合思想：画坦克由坦克类来画
    public void paint(Graphics g) {
        //如果坦克死了，直接return，不画了
        if ( ! this.isLive() )  return;

            switch (dir){
                case L:
                    g.drawImage(ResourceMgr.goodTankL,x,y,null);
                    break;
                case U:
                    g.drawImage(ResourceMgr.goodTankU,x,y,null);
                    break;
                case R:
                    g.drawImage(ResourceMgr.goodTankR,x,y,null);
                    break;
                case D:
                    g.drawImage(ResourceMgr.goodTankD,x,y,null);
                    break;
            }

        //先判断是GOOD 还是BAD ,再画，画完move一下，由于paint方法不断调用，move也会不断被调用，因此敌人坦克就会自己动了
        move();
    }

    //按键盘，做相应的操作
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();// 拿到键盘上下左右操作的代码
        switch (key) {   //按下键盘上下左右，坦克就朝哪个方向
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
        }
        //按下去后，调用此方法重新计算方向
        setMainDir();
    }

    // 根据bL , bU , bR , bD四个值综合来确定坦克的方向
    private void setMainDir() {
        //此方法先判断moving ，再判断方向
        // all dir keys are released , tank should be stop.
        if (!bL && !bU && !bR && !bD)  //什么都不按
            moving = false;

        // any dir key  is pressed , tank should be moving.
        else {
            moving = true;

            if (bL && !bU && !bR && !bD)   // 只按下朝左的键
                dir = Dir.L;
            if (!bL && bU && !bR && !bD)
                dir = Dir.U;
            if (!bL && !bU && bR && !bD)
                dir = Dir.R;
            if (!bL && !bU && !bR && bD)
                dir = Dir.D;
            //还能判断斜着走，例如  if ( bL &&  bU    && !bR && !bD)   可以左上走
        }
    }

    //按了键盘的方向，坦克的移动,每次移动SPEED
    private void move() {
        //如果！moving  ，即 ！false ，直接return   ；否则执行下面的语句，判断方向
        if (!moving) return;

        //执行到这里，意味着 按下了键盘方向， moving为true ， 即！ true ，不执行return
        switch (dir){
            case L:
                x -= SPEED;
                break;
            case U:
                y -= SPEED;
                break;
            case R:
                x += SPEED;
                break;
            case D:
                y += SPEED;
                break;
        }
    }

    //键盘弹起事件，放开键盘后，做相应的操作
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();// 拿到键盘上下左右操作的代码
        switch (key) {   //松开 键盘上下左右
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
            case KeyEvent.VK_CONTROL:    //开炮在抬起键盘的方法里，在keyPressed方法里会不断发射，敌人会受不了
                fire();
                break;
        }
        //抬起键盘，调用此方法重新计算方向
        setMainDir();
    }

    //坦克是否活着
    public boolean isLive(){
        return  live;
    }

    //设置坦克生命
    public void setLive(boolean live){
        this.live = live;
    }

    //坦克死，把生命设置为false
    public void die() {
        this.setLive(false);
    }

    //策略模式
    private FireStrategy strategy = null;

    private void initFireStrategy(){
        String className = PropertyMgr.get("tankFireStrategy");
        try {
            Class clazz = Class.forName("tank.strategy." + className);
            strategy = (FireStrategy) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //开炮,并调整射出子弹的位置
    private void fire() {
        strategy.fire(this);
    }



}
