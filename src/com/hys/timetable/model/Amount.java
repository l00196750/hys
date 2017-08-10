package com.hys.timetable.model;

import com.google.common.base.MoreObjects;

public class Amount {
    /**
     * 科任教员ID
     */
    private String courseTeacherId;

    /**
     * 周期总数
     */
    private int totalPeriodAmount;

    /**
     * 学员总数
     */
    private int totalStudentAmount;

    /**
     * 每天是否必须有学员
     */
    private boolean existEveryDay;

    /**
     * 如果有学员，最好是几个
     */
    // private int avgAmountIfExist;

    private float avgAmountEveryDay;

    public int getMaxAmountIfExist() {
        return 1 + Double.valueOf(Math.ceil(avgAmountEveryDay)).intValue();
    }

    public float getAvgAmountEveryDay() {
        return avgAmountEveryDay;
    }

    public String getCourseTeacherId() {
        return courseTeacherId;
    }

    public int getTotalPeriodAmount() {
        return totalPeriodAmount;
    }

    public int getTotalStudentAmount() {
        return totalStudentAmount;
    }

    // public void setAvgAmountIfExist(int avgAmountIfExist) {
    // this.avgAmountIfExist = avgAmountIfExist;
    // }

    public boolean isExistEveryDay() {
        return existEveryDay;
    }

    public void setAvgAmountEveryDay(float avgAmountEveryDay) {
        this.avgAmountEveryDay = avgAmountEveryDay;
    }

    public void setCourseTeacherId(String courseTeacherId) {
        this.courseTeacherId = courseTeacherId;
    }

    public void setExistEveryDay(boolean existEveryDay) {
        this.existEveryDay = existEveryDay;
    }

    public void setTotalPeriodAmount(int totalPeriodAmount) {
        this.totalPeriodAmount = totalPeriodAmount;
    }

    public void setTotalStudentAmount(int totalStudentAmount) {
        this.totalStudentAmount = totalStudentAmount;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("courseTeacherId", courseTeacherId).add("totalPeriodAmount", totalPeriodAmount)
            .add("totalStudentAmount", totalStudentAmount).add("existEveryDay", existEveryDay)
            // .add("amountIfExist", avgAmountIfExist)
            .add("avgAmountEveryDay", avgAmountEveryDay).add("maxAmountIfExist", getMaxAmountIfExist()).toString();
    }

}
