package com.fathom.nfs.DataModels;

public class QuestionDataModel {

    private String question;
    private String answer;

    public QuestionDataModel(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
