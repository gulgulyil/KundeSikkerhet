package oslomet.kundesikkerhet;

public class Kunde {
    private int id;
    private String navn;
    private String adresse;
    private String passord;

    public Kunde(int id, String navn, String adresse, String passord) {
        this.id = id;
        this.navn = navn;
        this.adresse = adresse;
        this.passord = passord;
    }

    public Kunde() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPassord() {
        return passord;
    }

    public void setPassord(String passord) {
        this.passord = passord;
    }
}
