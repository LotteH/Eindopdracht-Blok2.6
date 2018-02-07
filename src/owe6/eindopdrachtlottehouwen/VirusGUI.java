package owe6.eindopdrachtlottehouwen;

//imports
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import javax.swing.*;

public class VirusGUI extends JFrame implements ActionListener {

    // bestand zoeken
    JLabel bestandlabel;
    JTextField bestandveld;
    JButton bladerbutton;

    // classificatiedropdown
    JLabel classificatielabel;
    String[] classificatiecomboopties = {""};
    JComboBox classificatiebox;
    JButton classificatiebutton;

    //hostdropdown 
    JLabel hostidlabelvirus1;
    String[] hostidcomboopties1 = {""};
    JComboBox hostidbox1;
    JLabel hostidlabelvirus2;
    String[] hostidcomboopties2 = {""};
    JComboBox hostidbox2;

    //sorteeropties
    JLabel radiovergelijklabel;
    ButtonGroup buttongroep;
    JRadioButton radio1;
    JRadioButton radio2;

    //viruslijsten
    JTextArea viruslijst1area;
    JLabel viruslijst1label;
    JTextArea viruslijst2area;
    JLabel viruslijst2label;

    //sorteeropties & output
    JTextArea vergelijkarea;
    JLabel vergelijklabel;

    //overig
    JFileChooser filechooser;
    private static BufferedReader filelezer;
    String[] lijn;
    ArrayList<Object> virusclasse_lijst = new ArrayList<>();
    HashMap<String, HashSet<String>> mapclassenaarhost = new HashMap<>();
    HashMap<String, HashSet<String>> maphostnaarvirussen = new HashMap<>();
    HashMap<String, HashSet<String>> mapvirusidnaarhost = new HashMap<>();

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        VirusGUI frame = new VirusGUI();
        frame.setSize(1000, 600);
        frame.setTitle("VirusGUI");
        frame.createGUI();
        frame.show();
    }

    public void createGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container window = getContentPane();
        window.setLayout(new FlowLayout());

        // bestand zoeken
        bestandlabel = new JLabel("FILE      ");
        window.add(bestandlabel);
        bestandveld = new JTextField(70);
        window.add(bestandveld);
        bladerbutton = new JButton("     Bladeren     ");
        bladerbutton.addActionListener(this);
        window.add(bladerbutton);

        // Virusapplicatie
        classificatielabel = new JLabel("Virus Classificatie");
        window.add(classificatielabel);

        classificatiebox = new JComboBox(classificatiecomboopties);
        classificatiebox.setPreferredSize(new Dimension(710, 25));
        window.add(classificatiebox);
        classificatiebutton = new JButton(" Kies Klasse ");
        classificatiebutton.addActionListener(this);
        window.add(classificatiebutton);

        // Virussen vergelijken
        hostidlabelvirus1 = new JLabel("Host ID Virus 1");
        hostidlabelvirus1.setPreferredSize(new Dimension(450, 25));
        window.add(hostidlabelvirus1);

        hostidlabelvirus2 = new JLabel("Host ID Virus 2");
        hostidlabelvirus2.setPreferredSize(new Dimension(450, 25));
        window.add(hostidlabelvirus2);
        bestandlabel.setVisible(true);

        hostidbox1 = new JComboBox(hostidcomboopties1);
        hostidbox1.setPreferredSize(new Dimension(450, 25));
        window.add(hostidbox1);

        hostidbox2 = new JComboBox(hostidcomboopties2);
        hostidbox2.setPreferredSize(new Dimension(450, 25));
        window.add(hostidbox2);

        viruslijst1label = new JLabel("Viruslijst Virus 2");
        viruslijst1label.setPreferredSize(new Dimension(450, 100));

        radiovergelijklabel = new JLabel("Kies een sorteeroptie:     ");
        window.add(radiovergelijklabel);

        radio1 = new JRadioButton("ID");
        radio1.addActionListener(this);
        radio2 = new JRadioButton("Aantal Hosts                                                                                                                                                                                                        ");
        radio2.addActionListener(this);

        window.add(radio1);
        window.add(radio2);

        buttongroep = new ButtonGroup();
        buttongroep.add(radio1);
        buttongroep.add(radio2);

        viruslijst1area = new JTextArea();
        viruslijst1area.setPreferredSize(new Dimension(450, 150));
        window.add(viruslijst1area);

        viruslijst2label = new JLabel("Viruslijst Virus 2");
        viruslijst2label.setPreferredSize(new Dimension(450, 100));

        viruslijst2area = new JTextArea();
        viruslijst2area.setPreferredSize(new Dimension(450, 150));
        window.add(viruslijst2area);

        vergelijklabel = new JLabel("                                                            Overeenkomend tussen Virus 1 en Virus 2                                                            ");
        window.add(vergelijklabel);

        vergelijkarea = new JTextArea();
        vergelijkarea.setPreferredSize(new Dimension(940, 150));
        window.add(vergelijkarea);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        File selectedFile;
        int reply;
        String bestand;

        if (event.getSource() == bladerbutton) {

            
            filechooser = new JFileChooser();
            reply = filechooser.showOpenDialog(this);
            if (reply == JFileChooser.APPROVE_OPTION) {
                selectedFile = filechooser.getSelectedFile();
                bestandveld.setText(selectedFile.getName());
                bestand = selectedFile.getAbsolutePath();

                String line;

                try {
                    filelezer = new BufferedReader(new FileReader(bestand));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(VirusGUI.class.getName()).log(Level.SEVERE, null, ex);
                } 

                ArrayList<Object> virusid_lijst = new ArrayList<>();
                ArrayList<Object> virusnaam_lijst = new ArrayList<>();

                Set<String> uniekevirusclasseset = new HashSet<>();

                try {
                    while ((line = filelezer.readLine()) != null) {
                        lijn = line.split("\t", -1);                // hulp Jonathan Feenstra

                        HashSet<String> hostset = new HashSet<>();          // hulp Jonathan Feenstra
                        String host = lijn[7] + " (" + lijn[8] + ")";
                        hostset.add(host);
                        if (!mapclassenaarhost.containsKey(lijn[2])) {

                            mapclassenaarhost.put(lijn[2], hostset);
                        } else {
                            mapclassenaarhost.get(lijn[2]).add(host);
                        }

                        HashSet<String> virusset = new HashSet<>();
                        String virus = lijn[0];
                        hostset.add(virus);
                        if (!maphostnaarvirussen.containsKey(lijn[7])) {

                            maphostnaarvirussen.put(lijn[7], virusset);
                        } else {
                            maphostnaarvirussen.get(lijn[7]).add(virus);
                        }

                        HashSet<String> virusidset = new HashSet<>();          // hulp Jonathan Feenstra
                        String hosts = lijn[7];
                        virusidset.add(hosts);
                        if (!mapvirusidnaarhost.containsKey(lijn[0])) {

                            mapvirusidnaarhost.put(lijn[0], virusidset);
                        } else {
                            mapvirusidnaarhost.get(lijn[0]).add(hosts);
                        }

                        if (lijn[2] != null) {
                            virusclasse_lijst.add(lijn[2]);
                        } else {
                            virusclasse_lijst.add(" ");
                        }

                        if (lijn[2] != null) {
                            uniekevirusclasseset.add(lijn[2]);
                        } else {
                            uniekevirusclasseset.add(" ");
                        }

                    }
                } catch (IOException ex) {
                    {System.out.println("Er is IO fout aangetroffen. Wellicht niet het juiste bestand ingevoerd.");
                     System.out.println("Info: " + ex.toString());}
                     
                }catch (IndexOutOfBoundsException exc)
                    {System.out.println("Er is IndexOutOfBounce fout aangetroffen. Wellicht niet het juiste bestand ingevoerd.");
                     System.out.println("Info: " + exc.toString());}
                    catch (Exception exce) {
                    System.out.println("Er is een fout opgetreden. Probeer opnieuw.");
                    System.out.println("Info: " + exce.toString());

                ArrayList<String> uniekevirusclasselijst = new ArrayList<>(uniekevirusclasseset);

                for (int i = 1; i < uniekevirusclasselijst.size(); i++) {
                    classificatiebox.addItem(uniekevirusclasselijst.get(i));
                }
            }
        }

        if (event.getSource() == classificatiebutton) {
            VirusLogica.returnHostIDOptions(classificatiebox, mapclassenaarhost, hostidbox1, hostidbox2);

        }

        if (event.getSource() == radio1) {
            
            String hostid1 = VirusLogica.returnHost1(hostidbox1);
            String hostid2 = VirusLogica.returnHost2(hostidbox2);

            HashSet virussenid1 = VirusLogica.returnVirusIDLijst1(hostid1, maphostnaarvirussen, viruslijst1area);
            HashSet virussenid2 = VirusLogica.returnVirusIDLijst2(hostid2, maphostnaarvirussen, viruslijst2area);

            VirusLogica.returnVirusIDOvereenkomst(virussenid1, virussenid2, vergelijkarea);

        }

        if (event.getSource() == radio2) {
            
            String hostid1 = VirusLogica.returnHost1(hostidbox1);
            String hostid2 = VirusLogica.returnHost2(hostidbox2);

            HashSet virussenid1 = VirusLogica.returnVirusIDLijst1(hostid1, maphostnaarvirussen, viruslijst1area);
            HashSet virussenid2 = VirusLogica.returnVirusIDLijst2(hostid2, maphostnaarvirussen, viruslijst2area);

            VirusLogica.returnVirusHostLijst1(virussenid1, mapvirusidnaarhost, viruslijst1area);
            VirusLogica.returnVirusHostLijst2(virussenid2, mapvirusidnaarhost, viruslijst2area);

            VirusLogica.returnVirusIDOvereenkomst(virussenid1, virussenid2, vergelijkarea);


        }

    }
    }}
