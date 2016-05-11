package com.project.graduation.jackben.pedometer.beans;

import cn.bmob.v3.BmobObject;

/**
 * 用户实体类
 * User: XinYuan(1054344254@qq.com)
 * Date: 2015-11-10
 * Time: 16:45
 */
public class UserBean extends BmobObject {

    private String userId;
    private String userName;
    private String userSex;
    private String userPic;
    private Integer userWeight;
    private Integer userHeight;
    private Integer userSensitivity;
    private Integer userStepLength;

    public Integer getUserStepLength() {
        return userStepLength;
    }

    public void setUserStepLength(Integer userStepLength) {
        this.userStepLength = userStepLength;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUserSensitivity() {
        return userSensitivity;
    }

    public void setUserSensitivity(Integer userSensitivity) {
        this.userSensitivity = userSensitivity;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public Integer getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(Integer userWeight) {
        this.userWeight = userWeight;
    }

    public Integer getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(Integer userHeight) {
        this.userHeight = userHeight;
    }
}
