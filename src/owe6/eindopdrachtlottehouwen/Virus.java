
package owe6.eindopdrachtlottehouwen;


public class Virus {

    public Virus(int virus_id, String virusnaam, String virusclasse, int hostid, String hostnaam) {
        this.virus_id = virus_id;
        this.virusnaam = virusnaam;
        this.virusclasse = virusclasse;
        this.hostid = hostid;
    }
    private int virus_id;
    private String virusnaam;
    private String virusclasse;
    private int hostid;
    private String hostnaam;

    public int getVirus_id() {
        return virus_id;
    }

    public void setVirus_id(int virus_id) {
        this.virus_id = virus_id;
    }

    public String getVirusnaam() {
        return virusnaam;
    }

    public void setVirusnaam(String virusnaam) {
        this.virusnaam = virusnaam;
    }

    public String getVirusclasse() {
        return virusclasse;
    }

    public void setVirusclasse(String virusclasse) {
        this.virusclasse = virusclasse;
    }

    public int getHostid() {
        return hostid;
    }

    public void setHostid(int hostid) {
        this.hostid = hostid;
    }

    public String getHostnaam() {
        return hostnaam;
    }

    public void setHostnaam(String hostnaam) {
        this.hostnaam = hostnaam;
    }

}
