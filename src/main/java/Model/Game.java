package Model;

public class Game {
    private String idGame;
    private String name;
    private String platform;
    private int yearOfRelease;
    private String genre;
    private String publisher;
    private String developer;
    private String rating;

    public Game(String idGame, String name, String platform, int yearOfRelease,
                String genre, String publisher, String developer, String rating) {
        this.idGame = idGame;
        this.name = name;
        this.platform = platform;
        this.yearOfRelease = yearOfRelease;
        this.genre = genre;
        this.publisher = publisher;
        this.developer = developer;
        this.rating = rating;
    }

    public String getIdGame() {
        return idGame;
    }

    public void setIdGame(String idGame) {
        this.idGame = idGame;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Game{" +
                "idGame='" + idGame + '\'' +
                ", name='" + name + '\'' +
                ", platform='" + platform + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                ", genre='" + genre + '\'' +
                ", publisher='" + publisher + '\'' +
                ", developer='" + developer + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}
