package zookeeper;

import lombok.Data;

import java.io.Serializable;

@Data
public class Config implements Serializable {
    private static final long serialVersionUID = -3960940730727782588L;

    private String userName;
    private String password;

    public Config(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
