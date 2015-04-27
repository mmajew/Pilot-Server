package main;


public class Settings {
    private boolean allowAutoConfirm;
    private Integer port;

    public Settings() {
        allowAutoConfirm = false;
        port = 4444;
    }

    public void setAllowAutoConfirm(boolean allowAutoConfirm) {
        this.allowAutoConfirm = allowAutoConfirm;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean getEnableAutoConfirm() {
        return allowAutoConfirm;
    }

    public Integer getPort() {
        return port;
    }
}
