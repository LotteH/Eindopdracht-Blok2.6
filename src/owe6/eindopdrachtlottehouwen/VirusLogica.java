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
                String virusNaam = aRegel[1];
                String viruslineage = aRegel[2];
                String[] lineageArray = viruslineage.split(";");                        //fini
                String virusclasse = lineageArray[1];
                int host_id = Integer.parseInt(aRegel[7]);
                String hostnaam = aRegel[8];
                String hostIDcompleet = aRegel[7] + " (" + aRegel[8] + ")";
                Virus virusObject = new Virus(virus_id, virusNaam, virusclasse, host_id, hostnaam);

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

        HashSet<Object> hsHostIDBoxOpties = new HashSet();

        for (Object key : sortedKeys) {
            HashSet<Virus> hsVirusObjectenValues = hmHostIDmetVirusObjecten.get(key);
            for (Virus v : hsVirusObjectenValues) {
                if (gekozenClassificatie.contains(v.getVirusclasse()));
                {
                    hsHostIDBoxOpties.add(key);
                }
            }
        }
        return hsHostIDBoxOpties;
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

    public static HashSet<Virus> compareVirusID() {
        HashSet<Virus> hsVirusIDArea1 = new HashSet<>(Arrays.asList(aVirusIDArea1));
        HashSet<Virus> hsVirusIDArea2 = new HashSet<>(Arrays.asList(aVirusIDArea2));

        HashSet <Integer> hsvirusIDint1 = new HashSet<>();
        HashSet <Integer> hsvirusIDint2 = new HashSet<>();
        
        HashSet <Virus> hsVergelijkVirusID = new HashSet(hsvirusIDint1);
        hsVergelijkVirusID.retainAll(hsvirusIDint2);
        return hsVergelijkVirusID;
        
        /* retainall op integers waardoor er wel overeenkomst is. 
        
        for (Virus v : hsVirusIDArea1){
            hsvirusIDint1.add(v.getVirus_id());
        }
        
        for (Virus v : hsVirusIDArea2){
            hsvirusIDint2.add(v.getVirus_id());
        }
        
        HashSet <Integer> hsVergelijkVirusIDint = new HashSet(hsvirusIDint1);
        hsVergelijkVirusIDint.retainAll(hsvirusIDint2);
        return hsVergelijkVirusIDint;
*/
        
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