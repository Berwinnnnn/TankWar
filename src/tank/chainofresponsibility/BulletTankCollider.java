package tank.chainofresponsibility;

import tank.AbstractGameObject;
import tank.Bullet;
import tank.Tank;

import java.awt.*;

//子弹、坦克碰撞器

/*此类逻辑：
boolean collide( go1,  go2)  {
        if（go1 是 子弹 && go2 是 Tank）{
            do something···
        }else if（ go1 是 Tank && go2 是 子弹 ）{
            return collide(go2,go1); // 重用上面 if 代码
        }
     // 两个if都不符合
     逻辑链继续
        }
   */

public class BulletTankCollider  implements  Collider {

    //collide(``)方法    return  true  chain go on  ; return false chain break
    @Override
    public boolean collide (AbstractGameObject go1, AbstractGameObject go2) {
        if (go1 instanceof Bullet && go2 instanceof Tank){
            Bullet b = (Bullet)go1;
            Tank  t  = (Tank) go2;

            //先判断   子弹||敌人 是否活着，如果不活着，不撞了;此处子弹没活着的判断作用：子弹不能一发打死两个在同一位置的敌人坦克
            if ( ! b.isLive() || ! t.isLive() ) return false;
            //判断Group是否是自己，不能打自己
            if ( b.getGroup() == t.getGroup() ) return true;

            Rectangle rect = t.getRect();
            //判断方块是否相交。 如果相交撞上，子弹、坦克都得撞死die()
            if (b.getRect().intersects(rect)){
                b.die();
                t.die();
                return false;
            }
        }else  if (go1 instanceof Tank && go2 instanceof Bullet){
            return collide(go2,go1);
        }
        //两个if条件都不符合，chainofresponsibility 继续
        return true;
    }
}
