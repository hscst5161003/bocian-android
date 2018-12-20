package com.websarva.wings.android.bocian.model;

public class AttendanceNotice {
    private String participantName;
    private AttendanceState attendanceState;


    public enum AttendanceState {
        PARTICIPATE,
        NOT_ATTENDING,
    }
}
