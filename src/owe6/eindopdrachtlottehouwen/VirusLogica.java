package owe6.eindopdrachtlottehouwen;


// imports
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
/**
 * Class die logica achter virusGUI bevat. Hierin staan de methodes die het bestand kiezen en lezen. 
 * De juiste data wordt eruit gefilterd en uit de dropdown boxen worden opties geselecteerd.
 * De gekozen opties worden ook nog gesorterrd.
 * aangestuurd door de sorteeropties.
 *
 * @author lotte
 * @version 2.00
 * @since 28-02-2018 (28 feb. 2018)
 */
public class VirusLogica {

    // HashMap (hm)
    static HashMap<String, HashSet<Virus>> hmHostIDmetVirusObjecten = new HashMap();

    // HashSets (hs)
    static HashSet<Virus> hsVirusObjectenTotaal = new HashSet();
    static HashSet<Virus> hsVirusIDgekozen1 = new HashSet();
    static HashSet<Virus> hsVirusIDgekozen2 = new HashSet();

    static HashSet<String> hsClasses = new HashSet();

    // Arrays (a)
    static String[] aRegel;
    static Virus[] aVirusIDArea1;
    static Virus[] aVirusIDArea2;

    public static String kiesFile() {

        JFileChooser filechooser = new JFileChooser();

        int reply = filechooser.showOpenDialog(null);
        if (reply == JFileChooser.APPROVE_OPTION) {

            File selectedFile = filechooser.getSelectedFile();
            VirusGUI.bestandField.setText(selectedFile.getName());
            String bestandNaam = selectedFile.getAbsolutePath();
            return bestandNaam;
        }
        return null;
    }

    public static void readFile(String bestandNaam) {

        try {
            BufferedReader inFile;
            inFile = new BufferedReader(new FileReader(bestandNaam));
            String line;
            inFile.readLine();
            while ((line = inFile.readLine()) != null) {
                aRegel = line.split("\t", -1); // -1 zorgt dat hij de lege tabs ook ziet
                getElementen();
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(VirusLogica.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(VirusLogica.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }
/**
 * wordt aangeroepen in readFile
 */
    public static void getElementen() {
        if (aRegel[7] != null) {// && !"".equals(array[7])) {
            if (!"".equals(aRegel[7])) {
                int virus_id = Integer.parseInt(aRegel[0]);
                String virusnaam = aRegel[1];
                String viruslineage = aRegel[2];
                String[] lineageArray = viruslineage.split(";");                        //fini
                String virusclasse = lineageArray[1];
                int host_id = Integer.parseInt(aRegel[7]);
                String hostnaam = aRegel[8];
                String hostIDcompleet = aRegel[7] + " (" + aRegel[8] + ")";
                Virus virusObject = new Virus(virus_id, virusnaam, virusclasse, host_id, hostnaam);

                hsVirusObjectenTotaal.add(virusObject);
                if (!hmHostIDmetVirusObjecten.containsKey(hostIDcompleet)) {
                    hmHostIDmetVirusObjecten.put(hostIDcompleet, (HashSet) hsVirusObjectenTotaal.clone());     //als die niet in map dan key:value toevoegen
                } else {
                    hmHostIDmetVirusObjecten.get(hostIDcompleet).add(virusObject);                                                                // als key wel instaat voeg alleen een extra virus toe aan value
                }

                hsClasses.add(virusclasse);
                virusObject.getVirus_id();

                hsVirusObjectenTotaal.clear();
            }
        }
    }
    //System.out.println(classeset);

    public static void classificatievullen(JComboBox classificatiebox) {
        System.out.println(hsClasses);
        //ArrayList<String> classelijst = new ArrayList<>();
        //classelijst.addAll(classeset);
        for (String s : hsClasses) {
            classificatiebox.addItem(s);
        }
    }

    public static HashSet<Object> hostidboxenvullen(JComboBox classificatiebox, JComboBox hostid1box, JComboBox hostid2box) {
        String gekozenClassificatie = classificatiebox.getSelectedItem().toString();

        Object[] sortedKeys = hmHostIDmetVirusObjecten.keySet().toArray();
        Arrays.sort(sortedKeys);

        HashSet<Object> lijstid12 = new HashSet();

        for (Object key : sortedKeys) {
            HashSet<Virus> hsVirusObjectenValues = hmHostIDmetVirusObjecten.get(key);
            for (Virus v : hsVirusObjectenValues) {
                if (gekozenClassificatie.contains(v.getVirusclasse()));
                {
                    lijstid12.add(key);
                }
            }
        }
        return lijstid12;
    }

    public static void fillHostIDBoxen(JComboBox hostid1box, JComboBox hostid2box, HashSet<Object> lijstid12) {
        for (Object o : lijstid12) {
            hostid1box.addItem(o);
            hostid2box.addItem(o);
        }
    }

    public static void makeVirusIDArrays(JComboBox hostid1box, JComboBox hostid2box) {
        String gekozenHostIDBox1 = hostid1box.getSelectedItem().toString();
        String gekozenHostIDBox2 = hostid2box.getSelectedItem().toString();

        aVirusIDArea1 = hmHostIDmetVirusObjecten.get(gekozenHostIDBox1).toArray(new Virus[0]);
        aVirusIDArea2 = hmHostIDmetVirusObjecten.get(gekozenHostIDBox2).toArray(new Virus[0]);
    }

    public static void sortVirusIDArrays(JTextArea viruslijst1area, JTextArea viruslijst2area) {
        Arrays.sort(aVirusIDArea1, Virus.VirusIDComparator);
        Arrays.sort(aVirusIDArea2, Virus.VirusIDComparator);
    }

    public static void fillArea1(JTextArea viruslijst1area) {
        String sVirusID1 = "";
        for (Virus v : aVirusIDArea1) {
            sVirusID1 += v.getVirus_id() + "\n";
        }
        viruslijst1area.setText(sVirusID1);
    }

    public static void fillArea2(JTextArea viruslijst2area) {
        String sVirusIDArea2 = "";
        for (Virus v : aVirusIDArea2) {
            sVirusIDArea2 += v.getVirus_id() + "\n";
        }
        viruslijst2area.setText(sVirusIDArea2);
    }

    public static HashSet<Integer> compareVirusID() {
        HashSet<Virus> hsVirusIDArea1 = new HashSet<>(Arrays.asList(aVirusIDArea1));
        HashSet<Virus> hsVirusIDArea2 = new HashSet<>(Arrays.asList(aVirusIDArea2));

        HashSet <Integer> hsvirusIDint1 = new HashSet<>();
        HashSet <Integer> hsvirusIDint2 = new HashSet<>();
        
        for (Virus v : hsVirusIDArea1){
            hsvirusIDint1.add(v.getVirus_id());
        }
        
        for (Virus v : hsVirusIDArea2){
            hsvirusIDint2.add(v.getVirus_id());
        }
        
        HashSet <Integer> hsVergelijkVirusID = new HashSet(hsvirusIDint1);
        hsVergelijkVirusID.retainAll(hsvirusIDint2);
       // System.out.println(hsVergelijkVirusID);
        return hsVergelijkVirusID;

    }

    public static Virus [] sortedVergelijkVirusID(HashSet hsVergelijkVirusID) {
        ArrayList<Virus> alVergelijkVirusID = new ArrayList(hsVergelijkVirusID);

        Virus[] aVergelijkVirusID = alVergelijkVirusID.toArray(new Virus[0]);
        Arrays.sort(aVergelijkVirusID, Virus.VirusIDComparator);
     
        return aVergelijkVirusID;
    }
    
    public static void fillVergelijkArea(JTextArea vergelijkArea, Virus [] aVergelijkVirusID) {
        String sVergelijkVirusIDArea = "";
        for (Virus v : aVergelijkVirusID) {
            sVergelijkVirusIDArea += v.getVirus_id() + "\n";
        }
        vergelijkArea.setText(sVergelijkVirusIDArea);
    }
}
/*
    
    
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
     
    public static String returnHost1(JComboBox hostidbox1) {

        String host1 = hostidbox1.getSelectedItem().toString();
        String[] hostidsplitter1 = host1.split(" ");
        String hostid1 = hostidsplitter1[0];
        return hostid1;
    }

    /**
     *Methode haalt geselecteerde HostID met naam op (van virus 2) en haalt alleen de hostid eruit. Deze wordt gereturned.
     
    public static String returnHost2(JComboBox hostidbox2) {

        String host2 = hostidbox2.getSelectedItem().toString();
        String[] hostidsplitter1 = host2.split(" ");
        String hostid2 = hostidsplitter1[0];
        return hostid2;
    }

    /**
     *Methode geeft lijst (HashSet) van virusid's terug die voldoen aan geselecteerde hostid virus 1.
     *Deze worden gesorteerd in de bijbehorende teksarea gezet. De HashSet kan later gebruikt wordne om de 2 lijsten te vergleijken.
     
     
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
     * Virusid's die bij beide hosts voorkomen worden in de vergelijkarea gezet.
     
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
     
    public static void returnVirusHostOvereenkomst(HashSet virussenid1, HashSet virussenid2, JTextArea vergelijkarea) {

        Set vergelijkvirussenid = new TreeSet(virussenid1);
        vergelijkvirussenid.retainAll(virussenid2);
        List vergelijkidvirussen = new ArrayList(vergelijkvirussenid);
        for (int i = 0; i < vergelijkidvirussen.size(); i++) {
            vergelijkarea.append(vergelijkidvirussen.get(i).toString() + "\n");
        }
    }
}
*/