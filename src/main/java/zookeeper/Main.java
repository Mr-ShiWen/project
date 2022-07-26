package zookeeper;

public class Main {

    public static void main(String[] args) throws Exception {
        ZkConfigMag zkConfigMag = new ZkConfigMag();
        Config config = zkConfigMag.downLoadConfig();
        System.out.println("初始化加载配置信息：" + config.toString());
        zkConfigMag.syncConfigToZk();
        System.out.println("同步配置信息至zookeeper");
        System.out.println();

        ZkGetConfigClient zkGetConfigClient = new ZkGetConfigClient();
        Config config1 = zkGetConfigClient.getConfig();
        System.out.println("加载到配置信息：" + config1.toString());
//
        System.out.println();

        //暂停一会
        Thread.sleep(1000);

        zkConfigMag.upLoadConfig("test222", "3336666");
        System.out.println("修改配置文件：" + config.toString());
        zkConfigMag.syncConfigToZk();
        System.out.println("同步配置文件至zookeeper");
        System.out.println();

        System.out.println("主线程名称："+Thread.currentThread().getName());
        System.out.println();

        Thread.sleep(10);
//        System.in.read();
    }
}
