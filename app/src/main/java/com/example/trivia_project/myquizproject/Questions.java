package com.example.trivia_project.myquizproject;
import java.io.Serializable;

public class Questions implements Serializable{

    private String level,category, question,answer1,answer2,answer3,answer4,correctAnswer;

    public Questions(String level, String category, String question, String answer1, String answer2, String answer3,
                     String answer4, String correctAnswer) {
        super();
        this.level = level;
        this.category = category;
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.correctAnswer = correctAnswer;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "Questions [level=" + level + ", category=" + category + ", question=" + question + ", answer1="
                + answer1 + ", answer2=" + answer2 + ", answer3=" + answer3 + ", answer4=" + answer4
                + ", correctAnswer=" + correctAnswer + "]";
    }
}