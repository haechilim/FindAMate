package com.example.findamate.domain;

public class Poll {
    private int id;
    private boolean agree;

    public Poll() {}

    public Poll(int id, boolean agree) {
        this.id = id;
        this.agree = agree;
    }

    public Poll(boolean agree) {
        this(0, agree);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }

    @Override
    public String toString() {
        return "Poll{" +
                "id=" + id +
                ", agree=" + agree +
                '}';
    }
}
