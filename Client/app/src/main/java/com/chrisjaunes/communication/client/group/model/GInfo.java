package com.chrisjaunes.communication.client.group.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GInfo {
    @PrimaryKey
    private int group;
    private String group_name;
    private String group_avatar;

    public void setGroup(int group) {
        this.group = group;
    }
    public int getGroup() {return group; }

    public void setGroup_name(String group_name) { this.group_name = group_name; }
    public String getGroup_name() { return group_name; }

    public void setGroup_avatar(String group_avatar) { this.group_avatar = group_avatar; }
    public String getGroup_avatar() {return group_avatar;}
}
