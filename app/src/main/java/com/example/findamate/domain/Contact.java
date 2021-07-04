package com.example.findamate.domain;

public class Contact {
    public static final int CHECK_NONE = 0;
    public static final int CHECK_MALE = 1;
    public static final int CHECK_FEMALE = 2;

    private String name;
    private String number;
    private int checkMode = CHECK_NONE;

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public Contact(String name, String number, int checkMode) {
        this.name = name;
        this.number = number;
        this.checkMode = checkMode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCheckMode() {
        return checkMode;
    }

    public void setCheckMode(int checkMode) {
        this.checkMode = checkMode;
    }

    public void nextCheckMode() {
        if(checkMode == CHECK_NONE) checkMode = CHECK_MALE;
        else if(checkMode == CHECK_MALE) checkMode = CHECK_FEMALE;
        else if(checkMode == CHECK_FEMALE) checkMode = CHECK_NONE;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", checkMode=" + checkMode +
                '}';
    }
}
