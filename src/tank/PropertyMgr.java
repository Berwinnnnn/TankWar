package tank;

import java.io.IOException;
import java.util.Properties;

//配置文件管理类

public class PropertyMgr {
    private static Properties props;

    static {
        try {
            props = new Properties();
            props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //配置文件是键值对的存储方式
    public static String get(String key){
        return  (String)props.get(key);
    }

}
