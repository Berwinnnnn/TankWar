package tank.strategy;

import tank.*;

public class FourDirFireStrategy implements FireStrategy {
    @Override
    public void fire(Player p) {
        //        要使子弹在坦克的中心射出，需要重新计算：
//                子弹的位置bX = 坦克的位置x  +  坦克的宽度/2 - 子弹的宽度/2
//                子弹的位置bY = 同理
        int bX = p.getX() + ResourceMgr.goodTankU.getWidth() / 2 - ResourceMgr.bulletU.getWidth() / 2;
        int bY = p.getY() + ResourceMgr.goodTankU.getHeight() / 2 - ResourceMgr.bulletU.getHeight() / 2;

        Dir[] dirs = Dir.values();
        for (Dir d: dirs)  {
            TankFrame.INSTANCE.getGm().add(new Bullet(bX, bY, d, p.getGroup()));
        }
    }
}