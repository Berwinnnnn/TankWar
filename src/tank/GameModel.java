package tank;


import tank.chainofresponsibility.ColliderChain;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private Player myTank;//面向对象思想：不需要知道坦克类内部的情况，只需要new出来：画坦克（TankFrame给笔，坦克类自己画），移动（键盘给你，自己处理）

    ColliderChain chain = new ColliderChain();

    //所有事物放在此List
    private List<AbstractGameObject> objects;
    /*private  List<Tank> tanks ;  //npc
    private List<Bullet> bullets;  //子弹放在集合里
    private List<Explode> explodes; //爆炸不止一个*/


    public   GameModel(){
        //玩家、npc、子弹、爆炸 都在此方法new
        initGameObjects();
    }

    //玩家、npc、子弹都在此方法new
    private void initGameObjects() {
        myTank = new Player(100, 100, Dir.R, Group.GOOD);

        //创建AbstractGameObject实例
        objects = new ArrayList<>();
        /*//坦克实例
        tanks = new ArrayList<Tank>();
        //创建子弹实例
        bullets = new ArrayList<>();
        //爆炸实例
        explodes = new ArrayList<>();*/

        //用配置文件方法添加坦克        //添加10辆坦克
        int tankCount = Integer.parseInt(PropertyMgr.get("initTankCount"));
        for (int i =0 ; i < tankCount ; i++){
            this.add(new Tank(100 + 50 * i ,200 , Dir.U , Group.BAD ));
        }
        //加墙
        //this.add(new Wall(300, 200, 400, 50));
    }

    public void add(AbstractGameObject go) {
        objects.add(go);
    }

    public  void  paint (Graphics g){

        //统计子弹
        Color c = g.getColor();  //获取现场
        g.setColor(Color.WHITE);  //设置现场
        g.drawString("objects:" + objects.size() , 10 , 50);
        /*g.drawString("enemy:" + tanks.size() , 10 , 70);
        g.drawString("explode:" + explodes.size() , 10 , 90);*/
        g.setColor(c);  //恢复现场

        myTank.paint(g); // 玩家坦克。 画一个方块，模拟坦克，高内聚低耦合思想：画坦克由坦克类来画

        //画所有事物
        for (int i = 0 ; i < objects.size();i++){

            if (!objects.get(i).isLive()){
                objects.remove(i);
                break;
            }

            AbstractGameObject go1 = objects.get(i);
            for (int j = 0 ; j < objects.size();j++){
                AbstractGameObject go2 = objects.get(j);
                chain.collide(go1,go2);
            }

            if (objects.get(i).isLive()){
                objects.get(i).paint(g);
            }


        }

        /*//画敌人坦克,敌人被打死，去除remove ,否则画出来
        for (int i=0 ; i < tanks.size() ; i++){
            if ( ! tanks.get(i).isLive() ) {
                tanks.remove(i);
            }
            else {
                tanks.get(i).paint(g);
            }
        }

        //画子弹、边界判断、碰撞判断。
        // 有多少个子弹就画多少个   ,若子弹飞出边界，即子弹islive（）方法返回false ，要删掉
        for (int i=0 ; i<bullets.size();i++){
            //逻辑：子弹和敌人坦克撞，撞死了remove(i)，不再画paint(g)了--坦克、子弹的paint方法直接return
            for (int j=0 ; j < tanks.size() ; j++){
                bullets.get(i).collidesWithTank(tanks.get(j));
            }

            if ( ! bullets.get(i).isLive() ){ //子弹没活着，删掉
                bullets.remove(i);
            } else {   //子弹活着，获取，画
                bullets.get(i).paint(g);
            }
        }

        //画爆炸,爆炸死了，去除remove ,否则画出来
        for (int i=0 ; i < explodes.size() ; i++){
            if ( ! explodes.get(i).isLive() ) {
                explodes.remove(i);
            }
            else {
                explodes.get(i).paint(g);
            }
        }*/

    }

    public Player getMyTank() {
        return myTank;
    }
}
