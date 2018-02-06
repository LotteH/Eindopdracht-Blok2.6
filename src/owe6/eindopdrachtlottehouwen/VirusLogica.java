/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package owe6.eindopdrachtlottehouwen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author lotte
 */
public class VirusLogica {

    public static void ReturnHostIDOptions(JComboBox classificatiebox, HashMap<String, HashSet<String>> mapclassenaarhost, JComboBox hostidbox1, JComboBox hostidbox2) {           // Hulp Thijs Weenink
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

    public static String ReturnHost1(JComboBox hostidbox1) {

        String host1 = hostidbox1.getSelectedItem().toString();
        String[] hostidsplitter1 = host1.split(" ");
        String hostid1 = hostidsplitter1[0];
        return hostid1;
    }

    public static String ReturnHost2(JComboBox hostidbox2) {

        String host2 = hostidbox2.getSelectedItem().toString();
        String[] hostidsplitter1 = host2.split(" ");
        String hostid2 = hostidsplitter1[0];
        return hostid2;
    }

    public static HashSet ReturnVirusLijst1(String hostid1, HashMap<String, HashSet<String>> maphostnaarvirussen) {

        HashSet<String> virussen1 = new HashSet<>();
        if (maphostnaarvirussen.containsKey(hostid1)) {
            virussen1 = maphostnaarvirussen.get(hostid1);
            //System.out.println(hosts);
        }
       
        //Collections.sort(virussen1);
        return virussen1;
    }

    public static HashSet ReturnVirusLijst2(String hostid2, HashMap<String, HashSet<String>> maphostnaarvirussen) {

        HashSet<String> virussen2 = new HashSet<>();
        if (maphostnaarvirussen.containsKey(hostid2)) {
            virussen2 = maphostnaarvirussen.get(hostid2);
            //System.out.println(hosts);
        }
       
        //Collections.sort(virussen1);
        return virussen2;
    }
}
