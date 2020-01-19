package tank;

import java.util.Random;

//此枚举类型代表方向， 即射出炮弹时的方向
public enum Dir {
    L , U , R , D ;

    private static Random r = new Random();

    //产生随机方向，Tank类有调用此方法： 产生随机数 -> values()方法随便取一个方向 -> 敌人坦克动起来
    public static Dir randomDir(){
        return values()[r.nextInt(Dir.values().length)];
    }
}

/*
values()方法API中没有，是编译器自动添加的,  返回的是包含全部枚举值的数组
values().length求数组长度
r.nextInt(Dir.values().length)  即 r.nextInt(4) ： [0,4)
*/

