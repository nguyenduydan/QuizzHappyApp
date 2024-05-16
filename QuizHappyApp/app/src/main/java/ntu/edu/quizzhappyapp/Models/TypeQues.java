package ntu.edu.quizzhappyapp.Models;

public class TypeQues {
    public int typeQuesID;
    public String nameType;
    public String image;

    public TypeQues() {
    }

    public TypeQues(int typeQuesID, String nameType, String image) {
        this.typeQuesID = typeQuesID;
        this.nameType = nameType;
        this.image = image;
    }

    public int getTypeQuesID() {
        return typeQuesID;
    }

    public void setTypeQuesID(int typeQuesID) {
        this.typeQuesID = typeQuesID;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
