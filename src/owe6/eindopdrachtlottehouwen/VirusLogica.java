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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;

/**
 * Class die logica achter virusGUI bevat. Hierin staan de methodes die het
 * bestand kiezen en lezen. De juiste data wordt eruit gefilterd en uit de
 * dropdown boxen worden opties geselecteerd. De gekozen opties worden ook nog
 * gesorterrd. aangestuurd door de sorteeropties.
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
    static HashSet<String> hsClasses = new HashSet();

    // Arrays (a)
    static String[] aRegel;
    static Virus[] aVirusIDArea1;
    static Virus[] aVirusIDArea2;

    /**
     * In deze methode wordt er een bestand gekozen.
     *
     * @return
     */
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

    /**
     * In deze methode wordt het gekozen bestand gelezen.
     *
     * @param bestandNaam
     */
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
            System.out.println("Er is een fout opgetreden. Bestand niet gevonden.");
            Logger.getLogger(VirusLogica.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException | NullPointerException | ArrayIndexOutOfBoundsException ex) {
            System.out.println("Er is een fout opgetreden.");
            Logger.getLogger(VirusLogica.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (Exception ex) {
            System.out.println("Er is een fout opgetreden.");
            Logger.getLogger(VirusLogica.class
                    .getName()).log(Level.SEVERE, null, ex);

        }
    }

    /**
     * In deze methode worden elementen uit de dataset gefilterd en in de
     * attributen van virusclass gestopt. Er wordt een HashMap gemaakt met als
     * key hostid's en als value een HashSet van VirusObjecten die voorkomen bij
     * deze host. Ook wordt er een hashset gemaakt met alle virusclasse.
     */
    public static void getElementen() {
        if (aRegel[7] != null) {// && !"".equals(array[7])) {
            if (!"".equals(aRegel[7])) {
                int virus_id = Integer.parseInt(aRegel[0]);
                String virusNaam = aRegel[1];
                String virusLineage = aRegel[2];
                String[] lineageArray = virusLineage.split(";");                        //Fini
                String virusClasse = lineageArray[1];
                int hostID = Integer.parseInt(aRegel[7]);
                String hostNaam = aRegel[8];
                String hostIDCompleet = aRegel[7] + " (" + aRegel[8] + ")";
                Virus virusObject = new Virus(virus_id, virusNaam, virusClasse, hostID, hostNaam);

                hsVirusObjectenTotaal.add(virusObject);
                if (!hmHostIDmetVirusObjecten.containsKey(hostIDCompleet)) {            // Jonathan
                    hmHostIDmetVirusObjecten.put(hostIDCompleet, (HashSet) hsVirusObjectenTotaal.clone());     //als die niet in map dan key:value toevoegen
                } else {
                    hmHostIDmetVirusObjecten.get(hostIDCompleet).add(virusObject);                                                                // als key wel instaat voeg alleen een extra virus toe aan value
                }

                hsClasses.add(virusClasse);
                virusObject.getVirus_id();

                hsVirusObjectenTotaal.clear();
            }
        }
    }

    /**
     * methode die de het dropdownmenu van classificatie vult.
     *
     * @param classificatiebox
     */
    public static void classificatievullen(JComboBox classificatiebox) {
        for (String s : hsClasses) {
            classificatiebox.addItem(s);
        }
    }

    /**
     * Methode pakt de gekozen classificatie en retouneert een Hashset met
     * strings waar de HostID's in staan.
     *
     * @param classificatiebox
     * @return
     */
    public static HashSet<String> hostidboxenvullen(JComboBox classificatiebox) {
        String gekozenClassificatie = classificatiebox.getSelectedItem().toString();

        Object[] sortedKeys = hmHostIDmetVirusObjecten.keySet().toArray();
        Arrays.sort(sortedKeys);

        HashSet<String> hsHostIDBoxOpties = new HashSet();

        for (Object key : sortedKeys) {
            HashSet<Virus> hsVirusObjectenValues = hmHostIDmetVirusObjecten.get(key);
            for (Virus v : hsVirusObjectenValues) {
                if (gekozenClassificatie.contains(v.getVirusclasse()));
                {
                    hsHostIDBoxOpties.add(key.toString());
                }
            }
        }
        return hsHostIDBoxOpties;
    }

    /**
     * Methode die de items uit de combobox haalt (leeghaalt) De comboboxen
     * daarna gevuld met de beschikbare hostid's en namen.
     *
     *
     * @param hostid1box
     * @param hostid2box
     * @param hsIDVan1en2
     */
    public static void fillHostIDBoxen(JComboBox hostid1box, JComboBox hostid2box, HashSet<String> hsIDVan1en2) {
        hostid1box.removeAllItems();
        hostid2box.removeAllItems();

        List<String> alTempList = new ArrayList<>(hsIDVan1en2);
        Collections.sort(alTempList);

        hostid1box.setModel(new DefaultComboBoxModel(alTempList.toArray()));
        hostid2box.setModel(new DefaultComboBoxModel(alTempList.toArray()));
    }

    /**
     * Methode neemt de gekozen opties van de HostID comboboxen. Vervolgens
     * worden deze waardes gebruikt als keys in de HashMap. Van deze keys worden
     * de waardes (een array van Virussen) opgehaalt en in nieuwe variabele
     * opgeslagen.
     *
     * @param hostid1box
     * @param hostid2box
     */
    public static void makeVirusIDArrays(JComboBox hostid1box, JComboBox hostid2box) {
        String gekozenHostIDBox1 = hostid1box.getSelectedItem().toString();
        String gekozenHostIDBox2 = hostid2box.getSelectedItem().toString();

        aVirusIDArea1 = hmHostIDmetVirusObjecten.get(gekozenHostIDBox1).toArray(new Virus[0]);
        aVirusIDArea2 = hmHostIDmetVirusObjecten.get(gekozenHostIDBox2).toArray(new Virus[0]);
    }

    /**
     * Methode die de arrays sorteert.
     *
     * @param viruslijst1area
     * @param viruslijst2area
     */
    public static void sortVirusIDArrays(JTextArea viruslijst1area, JTextArea viruslijst2area) {
        Arrays.sort(aVirusIDArea1, Virus.VirusIDComparator);
        Arrays.sort(aVirusIDArea2, Virus.VirusIDComparator);
    }

    /**
     * Methode die eerste area vult met de gesorteerde array van HostID1.
     *
     * @param viruslijst1area
     */
    public static void fillArea1(JTextArea viruslijst1area) {
        String sVirusID1 = "";
        for (Virus v : aVirusIDArea1) {
            sVirusID1 += v.getVirus_id() + "\n";
        }
        viruslijst1area.setText(sVirusID1);
    }

    /**
     * Methode die tweede area vult met de gesorteerde array van HostID2.
     *
     * @param viruslijst2area
     */
    public static void fillArea2(JTextArea viruslijst2area) {
        String sVirusIDArea2 = "";
        for (Virus v : aVirusIDArea2) {
            sVirusIDArea2 += v.getVirus_id() + "\n";
        }
        viruslijst2area.setText(sVirusIDArea2);
    }

    /**
     * Methode die de VirusIDLijsten array split op tabs en in een String[] zet.
     * Daarna worden deze arrays omgezet in LinkedHashSet waardoor er gekeken
     * kan worden welke overeenkomen. De overlap wordt geretouneerd.
     *
     * @param lijst1
     * @param lijst2
     * @return
     */
    public static Set<String> compareVirusID(String lijst1, String lijst2) {
        String[] aVirusIDLijst1 = lijst1.split("\n");
        String[] aVirusIDLijst2 = lijst2.split("\n");

        Set<String> hsVirusIDArea1 = new LinkedHashSet<>(Arrays.asList(aVirusIDLijst1));
        Set<String> hsVirusIDArea2 = new LinkedHashSet<>(Arrays.asList(aVirusIDLijst2));

        hsVirusIDArea1.retainAll(hsVirusIDArea2);
        return hsVirusIDArea1;
    }

    /**
     * Methode vult de vergelijkingsarea met de VirusID die bij beide hosts
     * voorkomen.
     *
     * @param vergelijkArea
     * @param aVergelijkVirusID
     */
    public static void fillVergelijkArea(JTextArea vergelijkArea, Set<String> aVergelijkVirusID) {
        for (String v : aVergelijkVirusID) {
            vergelijkArea.append(v + System.lineSeparator());
        }
    }
}
