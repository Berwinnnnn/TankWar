package tank.chainofresponsibility;

import tank.AbstractGameObject;

//碰撞器
public interface Collider {
    //return  true  chain go on  ; return false chain break
    public boolean collide(AbstractGameObject go1 , AbstractGameObject go2);
}
