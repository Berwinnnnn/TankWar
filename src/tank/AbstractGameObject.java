package tank;

import java.awt.*;

//所有物体都继承此类，都由此类画
public abstract class AbstractGameObject {
    public abstract void paint(Graphics g);

    //物体都有判断自己是否活着
    public abstract boolean isLive();
}
