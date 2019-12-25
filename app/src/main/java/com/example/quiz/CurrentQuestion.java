package com.example.quiz;

public class CurrentQuestion extends Question {

    private int currentQuestionIndex;

    public CurrentQuestion(Question question, int index) {
        super(question);
        this.currentQuestionIndex = index;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }

}
