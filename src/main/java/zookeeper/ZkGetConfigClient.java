package zookeeper;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class ZkGetConfigClient {
    private Config config;

    public Config getConfig() throws Exception {
        ZkClient zkClient = new ZkClient("localhost:2181");
        if(!zkClient.exists("/zkConfig")){
            throw new Exception("配置信息节点不存在");
        }
        config = zkClient.readData("/zkConfig");

        zkClient.subscribeDataChanges("/zkConfig", new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                config = (Config)o;
                System.out.println("监听到配置信息被修改" + config.toString());
                System.out.println("客户端对象："+zkClient);
                System.out.println("监听线程："+Thread.currentThread().getName());
                System.out.println("--------------------------");
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                config = null;
                System.out.println("监听到配置信息被删除");
            }
        });
        return config;
    }
}
