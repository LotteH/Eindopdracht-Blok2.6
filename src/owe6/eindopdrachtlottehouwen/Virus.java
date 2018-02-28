package owe6.eindopdrachtlottehouwen;

import java.util.Comparator;

/**
 * Class waarin virusobjecten worden gemaakt. getters en setters komen hier tot
 * stand en door middel van de comperator worden virusid's gesorteerd.
 *
 * @author Lotte Houwen
 * @version 2.0
 * @since 28-02-2018 (28 feb. 2018)
 */
public class Virus {// implements Comparable {

    private int virus_id;
    private String virusnaam;
    private String virusclasse;
    private int host_id;
    private String hostnaam;

    public static int sorteerMethode;

    /**
     * Constructor.
     *
     * @param virus_id
     * @param virusnaam
     * @param virusclasse
     * @param host_id
     * @param hostnaam
     */
    public Virus(int virus_id, String virusnaam, String virusclasse, int host_id, String hostnaam) {
        this.virus_id = virus_id;
        this.virusnaam = virusnaam;
        this.virusclasse = virusclasse;
        this.host_id = host_id;
        this.hostnaam = hostnaam;
    }

    /**
     * Virus_id getter.
     *
     * @return virus_id
     */
    public int getVirus_id() {
        return virus_id;
    }

    /**
     * Virus_id setter.
     *
     * @param virus_id
     */
    public void setVirus_id(int virus_id) {
        this.virus_id = virus_id;
    }

    /**
     * Virusnaam getter.
     *
     * @return virusnaam
     */
    public String getVirusnaam() {
        return virusnaam;
    }

    /**
     * Virusnaam setter.
     *
     * @param virusnaam
     */
    public void setVirusnaam(String virusnaam) {
        this.virusnaam = virusnaam;
    }

    /**
     * Virusclasse getter.
     *
     * @return virusclasse
     */
    public String getVirusclasse() {
        return virusclasse;
    }

    /**
     * Virusclasse setter.
     *
     * @param virusclasse
     */
    public void setVirusclasse(String virusclasse) {
        this.virusclasse = virusclasse;
    }

    /**
     * Host_id getter.
     *
     * @return host_id
     */
    public int getHost_id() {
        return host_id;
    }

    /**
     * HostSet setter.
     *
     * @param host_id
     */
    public void setHost_id(int host_id) {
        this.host_id = host_id;
    }

    /** 
     * hostnaam getter.
     *
     * @return hostnaam
     */
    public String getHostnaam() {
        return hostnaam;
    }
    
     /**
     * hostnaam setter.
     *
     * @param hostnaam
     */
    public void setHostnaam(String hostnaam) {
        this.hostnaam = hostnaam;
    }

    /** 
     * Pakt 2 virusobjecten, kijkt vervolgens naar de virusid's en sorteerd de 
     * getallen van klein naar groot zodat de lijsten gesorteerd terugkomen.
     */
    
    public static Comparator<Virus> VirusIDComparator = new Comparator<Virus>() {    //Huub

        @Override
        public int compare(Virus vir1, Virus vir2) {

            Integer virID1 = vir1.getVirus_id();
            Integer virID2 = vir2.getVirus_id();

            return virID1.compareTo(virID2);

        }

    };

    /*
    @Override
    public int compareTo(Object o) {
         if (o instanceof Virus) {
            Virus that = (Virus) o;
            switch (sorteerMethode) {
                case 0:
                    return this.virus_id - that.virus_id;
                case 1:
                    return this.virusclasse.compareTo(that.virusclasse);
                case 2:
                    return this.hostSet.size() - that.hostSet.size();
                default:
                    return 0;
            }
        }
        return 0;
    }
     
     */
}
