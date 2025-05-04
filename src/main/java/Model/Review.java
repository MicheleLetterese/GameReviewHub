package Model;

public class Review {
    private String idReview;
    private String idGame;
    private double criticScore;
    private double criticCount;
    private double userScore;
    private double userCount;

    public Review(String idReview, String idGame, double criticScore, double criticCount, double userScore, double userCount) {
        this.idReview = idReview;
        this.idGame = idGame;
        this.criticScore = criticScore;
        this.criticCount = criticCount;
        this.userScore = userScore;
        this.userCount = userCount;
    }

    public Review() {
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

    public double getCriticScore() {
        return criticScore;
    }

    public void setCriticScore(double criticScore) {
        this.criticScore = criticScore;
    }

    public double getCriticCount() {
        return criticCount;
    }

    public void setCriticCount(double criticCount) {
        this.criticCount = criticCount;
    }

    public double getUserScore() {
        return userScore;
    }

    public void setUserScore(double userScore) {
        this.userScore = userScore;
    }

    public double getUserCount() {
        return userCount;
    }

    public void setUserCount(double userCount) {
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