package com.fathom.nfs.DataModels;

public class QuestionDataModel {

    /**
     * @class question data model
     * @desription  Hold question and answer data in FAQ
     * @date 4 feb 2021
     */
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
