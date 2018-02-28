package owe6.eindopdrachtlottehouwen;


import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Class waarin virusobjecten worden gemaakt. getters en setters komen hier tot
 * stand. Nog geen informatie opgehaald uit bestand met deze class en getters.
 * Wordt nog aan gewerkt.
 *
 * @author lotte
 * @version 2.00
 * @since 28-02-2018 (28 feb. 2018)
 *
 * Praktische gegevens: Student: Lotte Houwen Studentnummer: 542221 Klas: Bin2c
 * Opleiding: Bioinformatica aan Hogeschool Arnhem en Nijmegen (HAN)
 *
 * Opmerkingen: Met deze code heb ik af en toe samengewerkt met andere
 * studenten. Ook hebben we een middag bij elkaar gezeten. Code kan dus soms
 * overeenkomen. Soms hebben andere studenten mij geholpen/overlegd met een
 * klein stukje. Als het goed is staat er in het commentaar dan bij wie dit
 * gedaan heeft. Studenten met wie ik het meest overlegt heb of in diens
 * aanwezigheid aan mijn code gewerkt heb zijn: Jonathan Feenstra, Nicky van
 * Bergen, Justin Huberts, Thijs Weenink en in mindere mate Alex Janse en Damian
 * Bolwerk en Huub GoldtStein.
 */
/**
 * Class VirusGUI om Gui aan te maken en acties uit te voeren als er op bepaalde
 * buttons gedrukt wordt.
 */
public class VirusGUI extends JFrame implements ActionListener {

    // bestand zoeken
    JLabel bestandLabel;
    static JTextField bestandField;
    JButton bladerButton;

    // classificatiedropdown
    JLabel classificatieLabel;
    String[] classificatieComboOpties = {""};
    JComboBox classificatieBox;
    JButton classificatieButton;

    //hostdropdown 
    JLabel hostIDLabelVirus1;
    String[] hostidcomboopties1 = {""};
    JComboBox hostIDBox1;
    JLabel hostIDLabelVirus2;
    String[] hostIDComboOpties2 = {""};
    JComboBox hostIDBox2;

    //sorteeropties
    JLabel radioVergelijkLabel;
    ButtonGroup buttonGroup;
    JRadioButton radioButton1;
    JRadioButton radioButton2;

    //viruslijsten
    JTextArea virusLijstArea1;
    JLabel virusLijst1Label;
    JTextArea virusLijstArea2;
    JLabel virusLijst2Label;

    //sorteeropties & output
    JTextArea vergelijkArea;
    JLabel vergelijkLabel;

    //overig
    String[] lijn;

    /**
     * Maakt GUI frame aan.
     */
    public static void main(String[] args) {
        VirusGUI frame = new VirusGUI();
        frame.setSize(1000, 600);
        frame.setTitle("VirusGUI");
        frame.createGUI();
        frame.show();
    }

    /**
     * Creeërt inhoud van GUI
     */
    public void createGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container window = getContentPane();
        window.setLayout(new FlowLayout());

        // bestand zoeken
        bestandLabel = new JLabel("FILE      ");
        window.add(bestandLabel);
        bestandField = new JTextField(70);
        window.add(bestandField);
        bladerButton = new JButton("     Bladeren     ");
        bladerButton.addActionListener(this);
        window.add(bladerButton);

        // Virusapplicatie
        classificatieLabel = new JLabel("Virus Classificatie");
        window.add(classificatieLabel);

        classificatieBox = new JComboBox(classificatieComboOpties);
        classificatieBox.setPreferredSize(new Dimension(710, 25));
        window.add(classificatieBox);
        classificatieButton = new JButton(" Kies Klasse ");
        classificatieButton.addActionListener(this);
        window.add(classificatieButton);

        // Virussen vergelijken
        hostIDLabelVirus1 = new JLabel("Host ID 1");
        hostIDLabelVirus1.setPreferredSize(new Dimension(450, 25));
        window.add(hostIDLabelVirus1);

        hostIDLabelVirus2 = new JLabel("Host ID 2");
        hostIDLabelVirus2.setPreferredSize(new Dimension(450, 25));
        window.add(hostIDLabelVirus2);
        bestandLabel.setVisible(true);

        hostIDBox1 = new JComboBox(hostidcomboopties1);
        hostIDBox1.setPreferredSize(new Dimension(450, 25));
        window.add(hostIDBox1);

        hostIDBox2 = new JComboBox(hostIDComboOpties2);
        hostIDBox2.setPreferredSize(new Dimension(450, 25));
        window.add(hostIDBox2);

        virusLijst1Label = new JLabel("Viruslijst Virus 2");
        virusLijst1Label.setPreferredSize(new Dimension(450, 100));

        radioVergelijkLabel = new JLabel("Kies een sorteeroptie:     ");
        window.add(radioVergelijkLabel);

        radioButton1 = new JRadioButton("ID");
        radioButton1.addActionListener(this);
        radioButton2 = new JRadioButton("Aantal Hosts                                                                                                                                                                                                        ");
        radioButton2.addActionListener(this);

        window.add(radioButton1);
        window.add(radioButton2);

        buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);

        virusLijstArea1 = new JTextArea();
        virusLijstArea1.setPreferredSize(new Dimension(450, 150));
        window.add(virusLijstArea1);

        virusLijst2Label = new JLabel("Viruslijst Virus 2");
        virusLijst2Label.setPreferredSize(new Dimension(450, 100));

        virusLijstArea2 = new JTextArea();
        virusLijstArea2.setPreferredSize(new Dimension(450, 150));
        window.add(virusLijstArea2);

        vergelijkLabel = new JLabel("                                                            Overeenkomend tussen Virus 1 en Virus 2                                                            ");
        window.add(vergelijkLabel);

        vergelijkArea = new JTextArea();
        vergelijkArea.setPreferredSize(new Dimension(940, 150));
        window.add(vergelijkArea);
    }

    /**
     * Methode die acties uitvoerd na activatie van bepaalde knop/list/button.
     * Het laat zien welke methoden worden aangeroepen als er op bepaalde
     * buttons gedrukt worden.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        File selectedFile;
        int reply;
        String bestand;

        /**
         * Met Bladerbutton kan er een bestand gekozen worden. Deze wordt
         * gelezen en juiste data wordt eruit gefilterd.
         */
        if (event.getSource() == bladerButton) {
            VirusLogica.readFile(VirusLogica.kiesFile());
            VirusLogica.classificatievullen(classificatieBox);
        }

        /**
         * Met classificatiebutton kan er een virusclassificatie gekozen worden.
         * Ook worden hier de hostID dropdowns gevult.
         *
         */
        if (event.getSource() == classificatieButton) {
            HashSet<Object> lijstid12 = VirusLogica.hostidboxenvullen(classificatieBox, hostIDBox1, hostIDBox2);
            VirusLogica.fillHostIDBoxen(hostIDBox1, hostIDBox2, lijstid12);
        }

        /**
         * Met radioButton 1 kan er gesorteerd worden op VirusID, de virussen 
         * uit de 2 host worden vergeken en de overeenkomt wordt weergegeven.
         */
        
        if (event.getSource() == radioButton1) {
            VirusLogica.makeVirusIDArrays(hostIDBox1, hostIDBox2);
            VirusLogica.sortVirusIDArrays(virusLijstArea1, virusLijstArea2);
            VirusLogica.fillArea1(virusLijstArea1);
            VirusLogica.fillArea2(virusLijstArea2);
            HashSet<Integer> hsVergelijkVirusID = VirusLogica.compareVirusID();
            Virus[] aVergelijkVirusID = VirusLogica.sortedVergelijkVirusID(hsVergelijkVirusID);
            VirusLogica.fillVergelijkArea(vergelijkArea, aVergelijkVirusID);
        }

        /**
         * Met radioButton 2 kan er gesorteerd worden op aantal hosts, er wordt van de virussen 
         * uit de 2 host gekeken hoeveel host's ze hebben. Dde overeenkomt wordt weergegeven.
         */
        if (event.getSource() == radioButton2) {
            //VirusLogica.makeVirusIDArrays(hostidbox1, hostidbox2, viruslijst1area, viruslijst2area);
            //VirusLogica.sortVirusIDArrays(hostidbox1, hostidbox2, viruslijst1area, viruslijst2area);String hostid1 = VirusLogica.returnHost1(hostidbox1);
            //String hostid2 = VirusLogica.returnHost2(hostidbox2);

            //HashSet virussenid1 = VirusLogica.returnVirusIDLijst1(hostid1, maphostnaarvirussen, viruslijst1area);
            //HashSet virussenid2 = VirusLogica.returnVirusIDLijst2(hostid2, maphostnaarvirussen, viruslijst2area);

            //VirusLogica.returnVirusHostLijst1(virussenid1, mapvirusidnaarhost, viruslijst1area);
            //VirusLogica.returnVirusHostLijst2(virussenid2, mapvirusidnaarhost, viruslijst2area);

            //VirusLogica.returnVirusIDOvereenkomst(virussenid1, virussenid2, vergelijkarea);
        }
    }
}