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
    private int userWeight;
    private int userHeight;
    private int userSensitivity;
    private double userStepLength;

    public double getUserStepLength() {
        if (userStepLength==0){
            return  0.6;
        }
        return userStepLength;
    }

    public void setUserStepLength(double userStepLength) {
        this.userStepLength = userStepLength;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getUserSensitivity() {
        return userSensitivity;
    }

    public void setUserSensitivity(int userSensitivity) {
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

    public int getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(int userWeight) {
        this.userWeight = userWeight;
    }

    public int getUserHeight() {
        if (userWeight==0){
            return 50;
        }
        return userHeight;
    }

    public void setUserHeight(int userHeight) {
        this.userHeight = userHeight;
    }
}
