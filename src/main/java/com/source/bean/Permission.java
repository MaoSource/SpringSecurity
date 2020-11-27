package com.source.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author http://www.sm1234.cn
 * @version 1.0
 * @description cn.sm1234.domain
 * @date 18/4/14
 */
@Table(name = "sys_permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //int(10) NOT NULL,
    private String permName; //varchar(50) DEFAULT NULL,
    private String permTag; //varchar(50) DEFAULT NULL,

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName;
    }

    public String getPermTag() {
        return permTag;
    }

    public void setPermTag(String permTag) {
        this.permTag = permTag;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", permName='" + permName + '\'' +
                ", permTag='" + permTag + '\'' +
                '}';
    }
}
