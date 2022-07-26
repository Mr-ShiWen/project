package zookeeper;

import org.I0Itec.zkclient.ZkClient;

import java.io.Serializable;

public class ZkConfigMag {
    private Config config;
    ZkClient zkClient = new ZkClient("localhost:2181");

    /**
     * 模拟获取配置文件，从数据库加载或从前台获取
     * @return
     */
    public Config downLoadConfig(){
        config = new Config("test", "123");
        return config;
    }

    /**
     * 更新配置信息
     * @param userName
     * @param password
     */
    public void upLoadConfig(String userName, String password){
        if(config == null){
            config = new Config(userName, password);
        }else{
            config.setUserName(userName);
            config.setPassword(password);
        }
    }



    public void syncConfigToZk(){
        if(!zkClient.exists("/zkConfig")){
            zkClient.createPersistent("/zkConfig", true);
        }
        zkClient.writeData("/zkConfig", config);
    }
}
