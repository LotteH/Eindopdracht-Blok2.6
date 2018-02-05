/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package owe6.eindopdrachtlottehouwen;

import java.util.List;

/**
 *
 * @author lotte
 */
public class VirusLogica {

    public static int ReturnHostID(List virusclasse_lijst, String classificatiebox) {           // Hulp Thijs Weening
        //System.out.println(virusclasse_lijst.size());
        int i= 0;
        for (i=0; i < virusclasse_lijst.size(); i++) {                                  
            if (classificatiebox.equals(virusclasse_lijst.get(i))) {
                System.out.println(i);

            }
        }
        return i;
    }
}
