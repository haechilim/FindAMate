package com.example.findamate.domain;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<MatchingResult> results = new ArrayList<>();

    public void addResult(MatchingResult result) {
        results.add(result);
    }

    public List<MatchingResult> getResults() {
        return results;
    }
}