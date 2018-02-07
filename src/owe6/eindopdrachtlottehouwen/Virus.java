
package owe6.eindopdrachtlottehouwen;

/**
 * Class waarin virusobjecten worden gemaakt. getters en setters komen hier tot stand.
 * Nog geen informatie opgehaald uit bestand met deze class en getters. Wordt nog aan gewerkt.
 * @author lotte
 * @version 1.00
 * @since 07-02-2018 (7 feb. 2018)
 */
public class Virus { 

    /**
     *
     * @param virus_id
     * @param virusnaam
     * @param virusclasse
     * @param hostid
     * @param hostnaam
     */
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

    /**
     *
     * @return
     */
    public int getVirus_id() {
        return virus_id;
    }

    /**
     *
     * @param virus_id
     */
    public void setVirus_id(int virus_id) {
        this.virus_id = virus_id;
    }

    /**
     *
     * @return
     */
    public String getVirusnaam() {
        return virusnaam;
    }

    /**
     *
     * @param virusnaam
     */
    public void setVirusnaam(String virusnaam) {
        this.virusnaam = virusnaam;
    }

    /**
     *
     * @return
     */
    public String getVirusclasse() {
        return virusclasse;
    }

    /**
     *
     * @param virusclasse
     */
    public void setVirusclasse(String virusclasse) {
        this.virusclasse = virusclasse;
    }

    /**
     *
     * @return
     */
    public int getHostid() {
        return hostid;
    }

    /**
     *
     * @param hostid
     */
    public void setHostid(int hostid) {
        this.hostid = hostid;
    }

    /**
     *
     * @return
     */
    public String getHostnaam() {
        return hostnaam;
    }

    /**
     *
     * @param hostnaam
     */
    public void setHostnaam(String hostnaam) {
        this.hostnaam = hostnaam;
    }

}
