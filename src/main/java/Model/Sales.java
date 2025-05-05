package Model;

public class Sales {
    private String idSales;
    private String idGame;
    private double naSales;
    private double euSales;
    private double jpSales;
    private double otherSales;
    private double globalSales;

    public Sales() {
    }

    public Sales(String idSales, String idGame, double naSales, double euSales,
                 double jpSales, double otherSales, double globalSales) {
        this.idSales = idSales;
        this.idGame = idGame;
        this.naSales = naSales;
        this.euSales = euSales;
        this.jpSales = jpSales;
        this.otherSales = otherSales;
        this.globalSales = globalSales;
    }

    public String getIdSales() {
        return idSales;
    }

    public void setIdSales(String idSales) {
        this.idSales = idSales;
    }

    public String getIdGame() {
        return idGame;
    }

    public void setIdGame(String idGame) {
        this.idGame = idGame;
    }

    public double getNaSales() {
        return naSales;
    }

    public void setNaSales(double naSales) {
        this.naSales = naSales;
    }

    public double getEuSales() {
        return euSales;
    }

    public void setEuSales(double euSales) {
        this.euSales = euSales;
    }

    public double getJpSales() {
        return jpSales;
    }

    public void setJpSales(double jpSales) {
        this.jpSales = jpSales;
    }

    public double getOtherSales() {
        return otherSales;
    }

    public void setOtherSales(double otherSales) {
        this.otherSales = otherSales;
    }

    public double getGlobalSales() {
        return globalSales;
    }

    public void setGlobalSales(double globalSales) {
        this.globalSales = globalSales;
    }

    @Override
    public String toString() {
        return "Sales{" +
                "idSales='" + idSales + '\'' +
                ", idGame='" + idGame + '\'' +
                ", naSales=" + naSales +
                ", euSales=" + euSales +
                ", jpSales=" + jpSales +
                ", otherSales=" + otherSales +
                ", globalSales=" + globalSales +
                '}';
    }
}
