package com.example.quiz;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

class Question {
    String question;
    @SerializedName("incorrectAnswers")
    String[] incorrectAnswers;
    @SerializedName("correctAnswer")
    String correctAnswer;

    // Construtor
    public Question(String question, String[] incorrectAnswers, String correctAnswer) {
        this.question = question;
        this.incorrectAnswers = incorrectAnswers;
        this.correctAnswer = correctAnswer;
    }

    // Getters e Setters
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(String[] incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String[] getAllAnswers(){
        String[] answers = new String[incorrectAnswers.length + 1];
        answers[answers.length-1] = correctAnswer;
        for (int i = 0; i < incorrectAnswers.length; i++) {
            answers[i] = incorrectAnswers[i];
        }
        return Arrays.stream(answers).sorted().toArray(String[]::new);
    }
}
