package tank;

import java.awt.*;

//爆炸
public class Explode extends AbstractGameObject {
    private int x, y; //爆炸位置
    private  int width , height;  // 爆炸的长宽

    //每次paint时只画一步
    private int step = 0;

    private boolean live = true ;  // 爆炸的生命，初始化true

    //初始化爆炸的位置，长宽
    Explode(int x, int y) {
        this.x = x;
        this.y = y;

        this.width = ResourceMgr.explodes[0].getWidth();
        this.height = ResourceMgr.explodes[0].getHeight();
    }

    //画爆炸
    public void paint(Graphics g) {
        //如果爆炸死了，别画了
        if (!live)  return;

        g.drawImage(ResourceMgr.explodes[step],x,y,null);
        //每次画完step++，下次paint时画下一步
        step++;

        //什么时候结束？
        if (step >= ResourceMgr.explodes.length){
            this.die();
        }

    }

    //爆炸是否活着
    public boolean isLive(){
        return  live;
    }

    //设置爆炸的生命
    public void setLive(boolean live){
        this.live = live;
    }

    //爆炸死，把生命设置为false
    public void die(){
        this.setLive(false);
    }


}
