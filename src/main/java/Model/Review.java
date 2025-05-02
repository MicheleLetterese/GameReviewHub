package Model;

public class Review {
    private String idReview;
    private String idGame;
    private int criticScore;
    private int criticCount;
    private int userScore;
    private int userCount;

    public Review() {
    }

    public Review(String idReview, String idGame, int criticScore, int criticCount, int userScore, int userCount) {
        this.idReview = idReview;
        this.idGame = idGame;
        this.criticScore = criticScore;
        this.criticCount = criticCount;
        this.userScore = userScore;
        this.userCount = userCount;
    }

    public String getIdReview() {
        return idReview;
    }

    public void setIdReview(String idReview) {
        this.idReview = idReview;
    }

    public String getIdGame() {
        return idGame;
    }

    public void setIdGame(String idGame) {
        this.idGame = idGame;
    }

    public int getCriticScore() {
        return criticScore;
    }

    public void setCriticScore(int criticScore) {
        this.criticScore = criticScore;
    }

    public int getCriticCount() {
        return criticCount;
    }

    public void setCriticCount(int criticCount) {
        this.criticCount = criticCount;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    @Override
    public String toString() {
        return "Review{" +
                "idReview='" + idReview + '\'' +
                ", idGame='" + idGame + '\'' +
                ", criticScore=" + criticScore +
                ", criticCount=" + criticCount +
                ", userScore=" + userScore +
                ", userCount=" + userCount +
                '}';
    }
}