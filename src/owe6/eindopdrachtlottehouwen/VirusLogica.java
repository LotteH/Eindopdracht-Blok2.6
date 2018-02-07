package owe6.eindopdrachtlottehouwen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

/**
 * Class die logica achter virusGUI bevat. Hierin staan de methodes die worden
 * aangestuurd door de sorteeropties.
 *
 * @author lotte
 * @version 1.00
 * @since 07-02-2018 (7 feb. 2018)
 *
 * Voor de returns is er zoveel mogelijk gekozen voor HashSets. Deze return zijn
 * weer nodig bij het vergelijken en dit is eenvoudiger als er al HashSets zijn.
 */
public class VirusLogica {

    /**
     * Methode die opties van classificatie ophaalt. Aan de hand van deze opties kijkt het welke hosts voldoen aan deze virusclassificatie. 
     * Deze host worden in een lijst opgeslagen en de dropdown met hostid's worden gevult.
     */
    public static void returnHostIDOptions(JComboBox classificatiebox, HashMap<String, HashSet<String>> mapclassenaarhost, JComboBox hostidbox1, JComboBox hostidbox2) {           // Hulp Thijs Weenink
        HashSet<String> hosts = new HashSet<>();
        if (mapclassenaarhost.containsKey(classificatiebox.getSelectedItem().toString())) {
            hosts = mapclassenaarhost.get(classificatiebox.getSelectedItem().toString());
            //System.out.println(hosts);
        }
        ArrayList<String> hostlist = new ArrayList(hosts);
        Collections.sort(hostlist);
        hostidbox1.setModel(new DefaultComboBoxModel(hostlist.toArray()));         //hulp Jonathan Feenstra

        hostidbox2.setModel(new DefaultComboBoxModel(hostlist.toArray()));

    }

    /**
     *Methode haalt geselecteerde HostID met naam op (van virus 1) en haalt alleen de hostid eruit. Deze wordt gereturned.
     */
    public static String returnHost1(JComboBox hostidbox1) {

        String host1 = hostidbox1.getSelectedItem().toString();
        String[] hostidsplitter1 = host1.split(" ");
        String hostid1 = hostidsplitter1[0];
        return hostid1;
    }

    /**
     *Methode haalt geselecteerde HostID met naam op (van virus 2) en haalt alleen de hostid eruit. Deze wordt gereturned.
     */
    public static String returnHost2(JComboBox hostidbox2) {

        String host2 = hostidbox2.getSelectedItem().toString();
        String[] hostidsplitter1 = host2.split(" ");
        String hostid2 = hostidsplitter1[0];
        return hostid2;
    }

    /**
     *Methode geeft lijst (HashSet) van virusid's terug die voldoen aan geselecteerde hostid virus 1.
     *Deze worden gesorteerd in de bijbehorende teksarea gezet. De HashSet kan later gebruikt wordne om de 2 lijsten te vergleijken.
     
     */
    public static HashSet returnVirusIDLijst1(String hostid1, HashMap<String, HashSet<String>> maphostnaarvirussen, JTextArea viruslijst1area) {
        viruslijst1area.setText("");

        HashSet<String> virussen1 = new HashSet<>();
        if (maphostnaarvirussen.containsKey(hostid1)) {
            virussen1 = maphostnaarvirussen.get(hostid1);
            List sortedListvirussen1 = new ArrayList(virussen1);
            Collections.sort(sortedListvirussen1);
            for (int i = 0; i < sortedListvirussen1.size(); i++) {
                viruslijst1area.append(sortedListvirussen1.get(i).toString() + "\n");
            }
        }
        return virussen1;
    }

    /**
     *Methode geeft lijst (HashSet) van virusid's terug die voldoen aan geselecteerde hostid virus 2.
     *Deze worden gesorteerd in de bijbehorende teksarea gezet. De HashSet kan later gebruikt wordne om de 2 lijsten te vergleijken.
     */
    public static HashSet returnVirusIDLijst2(String hostid2, HashMap<String, HashSet<String>> maphostnaarvirussen, JTextArea viruslijst2area) {
        viruslijst2area.setText("");

        HashSet<String> virussen2 = new HashSet<>();
        if (maphostnaarvirussen.containsKey(hostid2)) {
            virussen2 = maphostnaarvirussen.get(hostid2);
            List sortedListvirussen2 = new ArrayList(virussen2);
            Collections.sort(sortedListvirussen2);
            for (int i = 0; i < sortedListvirussen2.size(); i++) {
                viruslijst2area.append(sortedListvirussen2.get(i).toString() + "\n");
            }

        }
        return virussen2;
    }

    /**
     * Methode vergelijkt de virusid's van HostID Virus 1 en HostID Virus 2.
     * Virusid's die bij beide voorkomen worden in de vergelijkarea gezet.
     */
    public static void returnVirusIDOvereenkomst(HashSet virussenid1, HashSet virussenid2, JTextArea vergelijkarea) {
        vergelijkarea.setText("");

        Set vergelijkvirussenid = new TreeSet(virussenid1);
        vergelijkvirussenid.retainAll(virussenid2);
        List vergelijkidvirussen = new ArrayList(vergelijkvirussenid);
        for (int i = 0; i < vergelijkidvirussen.size(); i++) {
            vergelijkarea.append(vergelijkidvirussen.get(i).toString() + "\n");
        }
    }

    /**
     * Voorlopig geeft deze methode de virusid's die voorkomen bij de hostid
     * virus 1 is geselecteerd. In de toekomst moet het de virusid's teruggeven,
     * gesorteerd op basis van aantal host's die horen bij hostid virus 1.
     * Daarnaast is er nu een printstatement gemaakt om de aantal hosts per
     * virusid weer te geven. Er moet nog gesorteerd worden en dit moet in de
     * area geplaatst worden.
     */
    static void returnVirusHostLijst1(HashSet<String> virussen1, HashMap<String, HashSet<String>> mapvirusidnaarhost, JTextArea viruslijst1area) {

        for (String item : virussen1) {
            if (mapvirusidnaarhost.containsKey(item)) {
                System.out.println(mapvirusidnaarhost.get(item).size());
            }
        }
    }

    /**
     * Voorlopig geeft deze methode de virusid's die voorkomen bij de hostid
     * virus 2 is geselecteerd. In de toekomst moet het de virusid's teruggeven,
     * gesorteerd op basis van aantal host's die horen bij hostid virus 2.
     * Daarnaast is er nu een printstatement gemaakt om de aantal hosts per
     * virusid weer te geven. Er moet nog gesorteerd worden en dit moet in de
     * area geplaatst worden.
     */
    static void returnVirusHostLijst2(HashSet<String> virussen2, HashMap<String, HashSet<String>> mapvirusidnaarhost, JTextArea viruslijst2area) {

        for (String item : virussen2) {
            if (mapvirusidnaarhost.containsKey(item)) {
                System.out.println(mapvirusidnaarhost.get(item).size());
            }
        }
    }

    /**
     * Voorlopig geeft deze methode de overeenkomstige virusid's terug. In de
     * toekomst moet het de virusid's teruggeven, gesorteerd op basis van aantal
     * host's
     */
    public static void returnVirusHostOvereenkomst(HashSet virussenid1, HashSet virussenid2, JTextArea vergelijkarea) {

        Set vergelijkvirussenid = new TreeSet(virussenid1);
        vergelijkvirussenid.retainAll(virussenid2);
        List vergelijkidvirussen = new ArrayList(vergelijkvirussenid);
        for (int i = 0; i < vergelijkidvirussen.size(); i++) {
            vergelijkarea.append(vergelijkidvirussen.get(i).toString() + "\n");
        }
    }
}
