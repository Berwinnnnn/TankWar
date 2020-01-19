package tank;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Tank  extends  AbstractGameObject{
    //定义坐标值
    int x = 100, y = 100;
    //炮弹方向 , 即射出炮弹时的方向
    private  Dir dir ;
    //通过上下左右箭头的按键状态:有没有被按下去（有=true  ， 抬起来了=false），判定坦克的移动方向
    private boolean bL , bU , bR , bD= false;
    // 作用：不按方向键是（false）停止， 按下任意方向键应该（true）移动 ；参照move（）、setMainDir（）方法  ；坦克静止是状态，不是方向，不应在Dir类里
    private boolean moving = true;
    //区分敌我
    private Group group;
    //坦克的生命,初始化true，被子弹打中，设置为fasle
    private boolean live = true;
    //定义坦克的长度、宽度
    private int width , height ;
    //定义前一帧的位置，出界判断方法判定坦克会出界，把位置返回前一帧
    private int oldX , oldY;

    //定义 Tank的方块
    Rectangle rect;

    //定义一个速度，常量
    public   static  final int SPEED = 10 ;

    //构造方法
    public  Tank(int x  ,int y, Dir dir , Group group){
        this.x=x;
        this.y=y;
        this.dir=dir;
        this.group=group;

        //一开始位置都一样
        oldX = x;
        oldY = y;

        //初始化坦克长宽
        this.width = ResourceMgr.badTankU.getWidth();
        this.height = ResourceMgr.badTankU.getHeight();

        //初始化Tank方块
        this.rect = new Rectangle(x,y,width,height);
    }

    //获取子弹的方块
    public Rectangle getRect() {
        return rect;
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

    //返回敌人组
    public Group getGroup(){
        return this.group;
    }

    //画一个方块，模拟坦克，高内聚低耦合思想：画坦克由坦克类来画
    public void paint(Graphics g) {
        //如果坦克死了，直接return，不画了
        if ( ! this.isLive() )  return;

            switch (dir){
                case L:
                    g.drawImage(ResourceMgr.badTankL,x,y,null);
                    break;
                case U:
                    g.drawImage(ResourceMgr.badTankU,x,y,null);
                    break;
                case R:
                    g.drawImage(ResourceMgr.badTankR,x,y,null);
                    break;
                case D:
                    g.drawImage(ResourceMgr.badTankD,x,y,null);
                    break;
            }

        //画，画完move一下，由于paint方法不断调用，move也会不断被调用，因此敌人坦克就会自己动了
        move();

        //update rect
        rect.x = x;
        rect.y = y;
    }

    // 获取方向、边界判断  。  获取到方向，坦克会移动,每次移动SPEED
    private void move() {
        //如果！moving  ，即 ！false ，直接return   ；否则执行下面的语句，判断方向
        if (!moving) return;

        //每次移动前记录x、y位置，然后坦克会发生改变
        oldX = x;
        oldY = y;

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

        boundsCheck();

        randomDir();
        if ( r.nextInt(100) > 90 )
            fire();   //敌人发炮太频繁，加入if限制
    }

    private Random r = new  Random() ;

    //产生随机方向，敌人坦克会自己动起来，由于paint方法不断调用，move方法也不断调用，randomDir()会不断调用
    private void randomDir() {
        //换方向太频繁，加入if判断限制
        if ( r.nextInt(100) > 80 )
            this.dir = Dir.randomDir();
    }

    //坦克是否活着
    public boolean isLive(){
        return  live;
    }

    //设置坦克生命
    public void setLive(boolean live){
        this.live = live;
    }

    //坦克死，把生命设置为false , 爆炸产生new  , 需要加入add()到TankFrame类
    public void die() {
        this.setLive(false);
        TankFrame.INSTANCE.getGm().add(new Explode(x,y));
    }

    //开炮,并调整射出子弹的位置
    private void fire() {
//        要使子弹在坦克的中心射出，需要重新计算：
//                子弹的位置bX = 坦克的位置x  +  坦克的宽度/2 - 子弹的宽度/2
//                子弹的位置bY = 同理
        int bX = x + ResourceMgr.goodTankU.getWidth()/2  -  ResourceMgr.bulletU.getWidth()/2;
        int bY = y + ResourceMgr.goodTankU.getHeight()/2 - ResourceMgr.bulletU.getHeight()/2;
        TankFrame.INSTANCE.getGm().add(new Bullet(bX,bY,dir,group));

//        这样设置的子弹，会在坦克的左上方射出
//        //子弹的x , y位置、方向、好坏是坦克的
//        TankFrame.INSTANCE.add(new Bullet(x,y,dir,group));
    }

    //坦克边界判断，坦克不能出边界：  解决：把前一帧位置记录，出了边界返回前一帧的位置 ，注意此处还要考虑坦克的长度宽度
    private void boundsCheck() {
        //  左边界    上边界，目录栏占30          右边界 考虑坦克长宽                      下边界 考虑坦克长宽
        if ( x < 0  ||  y < 30  || x+width > TankFrame.GAME_WIDTH  || y+height > TankFrame.GAME_HEIGHT){
            this.back();
        }
    }

    //记录前一帧位置，坦克边界判断时，坦克出了边界，返回前一帧位置
    //撞墙就返回前一帧位置
    public void back() {
        this.x = oldX;
        this.y = oldY;
    }


}
