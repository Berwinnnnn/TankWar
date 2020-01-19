package tank;

//子弹，设计成好坦克不打好坦克

import java.awt.*;

public class Bullet  extends  AbstractGameObject{
    private int x, y; //子弹位置
    private Group group;  //设计成好坦克不打好坦克
    private Dir dir; //子弹的方向
    public static final int SPEED = 20;
    private boolean live = true ;  // 子弹的生命，初始化true ， 飞出了边界boundsCheck（）方法设置为false

    // 子弹的长宽
    private int w = ResourceMgr.bulletU.getWidth()  ;
    private int h = ResourceMgr.bulletU.getHeight() ;

    private Rectangle rect;  // 获取子弹的方块 ,用于碰撞检测

    public Bullet(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;

        this.rect = new Rectangle(x,y,w,h);
    }

    //画子弹
    public void paint(Graphics g) {
        //如果子弹死了，直接return，不画了
        if ( ! this.isLive() )  return;

        switch (dir) {
            case L:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case U:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case R:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
            case D:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
        }
        //先画，画完move一下，子弹要能飞
        move();

        //move完，要更新子弹的位置
        rect.x = x;
        rect.y = y;
    }

    //子弹要飞
    private void move() {
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
    }

    //获取 子弹方块
    public Rectangle getRect() {
        return rect;
    }

    //获取和设置组别
    public void setGroup(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    //子弹是否活着
    public boolean isLive(){
        return  live;
    }

    //设置子弹的生命
    public void setLive(boolean live){
        this.live = live;
    }

    //子弹死，把生命设置为false
    public void die(){
        this.setLive(false);
    }

//  碰撞检查，此方法原理： 子弹与坦克是否相交。获取子弹和坦克的方块，判断是否相交。
//       先判断断敌人是否活着，如果没有，不撞了
//       判断Group
//       再判断是否相交，若相交，此坦克、此子弹的生命结束，并不再画出此坦克、此子弹；
//  此方法什么时候调用？ TankFrame类的paint（）画出子弹后
    /*public void collidesWithTank(Tank tank){
        //先判断   子弹||敌人 是否活着，如果不活着，不撞了;此处子弹没活着的判断作用：子弹不能一发打死两个在同一位置的敌人坦克
        if ( ! this.isLive() || ! tank.isLive() ) return;
        //判断Group是否是自己，不能打自己
        if ( this.group == tank.getGroup() ) return;

        //获取子弹和坦克的方块
        Rectangle rect = new Rectangle(x,y,ResourceMgr.bulletU.getWidth(),ResourceMgr.bulletU.getHeight());
        Rectangle rectTank = new Rectangle(tank.getX(),tank.getY(),ResourceMgr.badTankU.getWidth(),ResourceMgr.badTankU.getHeight());

        //判断方块是否相交。 如果相交撞上，子弹、坦克都得撞死die()
        if (rect.intersects(rectTank)){
            this.die();
            tank.die();
        }
    }*/

    //子弹边界判断，出了边界要把子弹对象从List中删除，避免内存堆积对象
    private void boundsCheck() {
        //若子弹飞出边界，live设置为false
        //  左边界    上边界，目录栏占30              右边界                         下边界
        if ( x < 0  ||  y < 30  || x > TankFrame.GAME_WIDTH  || y > TankFrame.GAME_HEIGHT){
            live = false ;
        }
    }

}
