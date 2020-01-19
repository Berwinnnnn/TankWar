package tank.chainofresponsibility;

import tank.AbstractGameObject;
import tank.Bullet;
import tank.Wall;

//子弹、墙碰撞器

public class BulletWallCollider implements Collider {

    @Override
    public boolean collide (AbstractGameObject go1, AbstractGameObject go2) {
        if (go1 instanceof Bullet && go2 instanceof Wall){
            Bullet b = (Bullet)go1;
            Wall   w = (Wall)  go2;

            //如果子弹活着 时， 如果 子弹撞墙，子弹死，返回false ，chainofresponsibility  break
            if (b.isLive()){
                if (b.getRect().intersects(w.getRect())){
                    b.die();
                    return false;
                }
            }

        }else if (go1 instanceof Wall && go2 instanceof Bullet){
            collide(go2,go1);
        }

        return true;
    }
}
