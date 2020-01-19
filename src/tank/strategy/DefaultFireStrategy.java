package tank.strategy;

import tank.Bullet;
import tank.Player;
import tank.ResourceMgr;
import tank.TankFrame;

public class DefaultFireStrategy implements FireStrategy {
    @Override
    public void fire(Player p) {
        //        要使子弹在坦克的中心射出，需要重新计算：
//                子弹的位置bX = 坦克的位置x  +  坦克的宽度/2 - 子弹的宽度/2
//                子弹的位置bY = 同理
        int bX = p.getX() + ResourceMgr.goodTankU.getWidth()/2  -  ResourceMgr.bulletU.getWidth()/2;
        int bY = p.getY() + ResourceMgr.goodTankU.getHeight()/2 - ResourceMgr.bulletU.getHeight()/2;
        TankFrame.INSTANCE.getGm().add(new Bullet(bX,bY,p.getDir(),p.getGroup()));

//        这样设置的子弹，会在坦克的左上方射出
//        //子弹的x , y位置、方向、好坏是坦克的
//        TankFrame.INSTANCE.add(new Bullet(x,y,dir,group));
    }
}
