package tank.chainofresponsibility;

//Tank、墙碰撞器

import tank.AbstractGameObject;
import tank.Tank;
import tank.Wall;

public class TankWallCollider implements Collider {

    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if (go1 instanceof Tank && go2 instanceof Wall){
            Tank t = (Tank)go1;
            Wall w = (Wall)go2;

            // 如果 Tank 活着  时， 如果Tank 撞上墙，那么Tank   back()
            if (t.isLive()){
                if (t.getRect().intersects(w.getRect())){
                    t.back();
                }
            }

        }else if (go1 instanceof Wall && go2 instanceof Tank){
            collide(go2,go1);
        }

        return true;
    }
}
