package com.example.wwmeet_android.domain;

public class Participant {
    private String name;
    private boolean isFinishVote;

    public Participant(String name, boolean isFinishVote) {
        this.name = name;
        this.isFinishVote = isFinishVote;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFinishVote() {
        return isFinishVote;
    }

    public void setFinishVote(boolean finishVote) {
        isFinishVote = finishVote;
    }
}
