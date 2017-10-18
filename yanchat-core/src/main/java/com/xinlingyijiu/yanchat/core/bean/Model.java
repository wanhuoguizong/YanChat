package com.xinlingyijiu.yanchat.core.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laotou on 2017/10/18.
 */
public class Model {
    private Integer model;
    private List<Address> addressList;

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public synchronized void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public void addAddress(Address address) {
        initAddressList();
        this.addressList.add(address);
    }

    private synchronized void initAddressList() {
        if (this.addressList == null) this.addressList = new ArrayList<>();
    }
}
