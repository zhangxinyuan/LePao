package com.project.graduation.jackben.pedometer.beans;

import cn.bmob.v3.BmobObject;

/**
 * 步数实体Bean
 * User: XinYuan(1054344254@qq.com)
 * Date: 2015-11-10
 * Time: 16:46
 */
public class StepBean extends BmobObject {
    private Integer _id;
    private String userId;
    private Integer stepCount;
    private String date;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public Integer getStepCount() {
        return stepCount;
    }

    public void setStepConut(Integer stepCount) {
        this.stepCount = stepCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
