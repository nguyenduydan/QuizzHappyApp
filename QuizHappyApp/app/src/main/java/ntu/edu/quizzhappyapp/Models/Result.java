package ntu.edu.quizzhappyapp.Models;

import java.util.Date;

public class Result {
    public int resultID;
    public int score;
    public String timeStamp;
    public int typeQuesID;

    public Result() {
    }

    public Result(int resultID, int score, String timeStamp, int typeQuesID) {
        this.resultID = resultID;
        this.score = score;
        this.timeStamp = timeStamp;
        this.typeQuesID = typeQuesID;
    }

    public int getTypeQuesID() {
        return typeQuesID;
    }

    public void setTypeQuesID(int typeQuesID) {
        this.typeQuesID = typeQuesID;
    }

    public int getResultID() {
        return resultID;
    }

    public void setResultID(int resultID) {
        this.resultID = resultID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
