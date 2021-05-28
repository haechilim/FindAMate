package com.example.findamate.domain;

import java.util.ArrayList;
import java.util.List;

public class MatchingManager {
    private List<History> histories = new ArrayList<>();

    public void addHistory(History history) {
        histories.add(history);
    }

    public List<History> getHistories() {
        return histories;
    }
}
