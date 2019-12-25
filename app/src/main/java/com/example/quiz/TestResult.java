package com.example.quiz;

import java.io.Serializable;
import java.util.List;

public class TestResult implements Serializable {

    private List<Question> questionList;
    private List<Integer> userAnswersIndexesList;

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public List<Integer> getUserAnswersIndexesList() {
        return userAnswersIndexesList;
    }

    public void setUserAnswersIndexesList(List<Integer> userAnswersIndexesList) {
        this.userAnswersIndexesList = userAnswersIndexesList;
    }

}
