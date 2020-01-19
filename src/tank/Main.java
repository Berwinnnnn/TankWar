package tank;

public class Main {
    public static void main(String[] args) throws  Exception {
   //     TankFrame tf = new TankFrame();  //不能new了，通过TankFrame.INSTANCE使用TankFrame类
       TankFrame.INSTANCE.setVisible(true);

        while(true) {
            Thread.sleep(100);
            TankFrame.INSTANCE.repaint();   //通过repaint方法调用paint方法
        }

    }
}
