package tank;

import java.awt.*;

public class Wall  extends  AbstractGameObject{
    private int x, y, w, h;

    //墙的方块，用于碰撞检测
    private Rectangle rect;

    public Wall(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        this.rect = new Rectangle(x,y,w,h);
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(x, y, w, h);
        g.setColor(c);
    }

    //获取方块
    public Rectangle getRect() {
        return rect;
    }

    //墙一直都在
    @Override
    public boolean isLive() {
        return true;
    }
}
