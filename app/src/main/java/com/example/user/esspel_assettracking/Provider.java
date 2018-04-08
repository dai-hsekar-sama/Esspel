package com.example.user.esspel_assettracking;

/**
 * Created by user on 3/16/2018.
 */

public class Provider {
    String name,pid,type,phone;

    public Provider(String name, String pid, String type,String phone) {
        this.name = name.toLowerCase();
        this.pid = pid.toLowerCase();
        this.type = type.toLowerCase();
        this.phone = phone.toLowerCase();
    }

    public String getName() {
        return name;
    }

    public String getPid() {
        return pid;
    }

    public String getType() {
        return type;
    }

    public String getPhone() {
        return phone;
    }
}
