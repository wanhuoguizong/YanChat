package com.xinlingyijiu.yanchat.core.bean;

/**
 * Created by laotou on 2017/10/18.
 */
public class Address {
    private String host;
    private Integer port;

    public Address() {
    }

    public Address(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (host != null ? !host.equals(address.host) : address.host != null) return false;
        return !(port != null ? !port.equals(address.port) : address.port != null);

    }

    @Override
    public int hashCode() {
        int result = host != null ? host.hashCode() : 0;
        result = 31 * result + (port != null ? port.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s:%d",host,port);
    }
}
