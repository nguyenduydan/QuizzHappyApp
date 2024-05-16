package ntu.edu.quizzhappyapp.Models;

public class Questions {
    public int quesID;
    public String question;
    public String quesTypeID;
    public String option1;
    public String option2;
    public String option3;
    public String option4;
    public int optionCorrect;

    public Questions() {
    }

    public Questions(int quesID, String question, String quesTypeID, String option1, String option2, String option3, String option4, int optionCorrect) {
        this.quesID = quesID;
        this.question = question;
        this.quesTypeID = quesTypeID;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.optionCorrect = optionCorrect;
    }

    public int getQuesID() {
        return quesID;
    }

    public void setQuesID(int quesID) {
        this.quesID = quesID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuesTypeID() {
        return quesTypeID;
    }

    public void setQuesTypeID(String quesTypeID) {
        this.quesTypeID = quesTypeID;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public int getOptionCorrect() {
        return optionCorrect;
    }

    public void setOptionCorrect(int optionCorrect) {
        this.optionCorrect = optionCorrect;
    }
}
