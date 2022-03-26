package edu.uoc.pac2;

import java.util.*;

public class DNA {

    private String bases;

    public DNA(String bases) throws Exception {
        setBases(bases);
    }

    public String getBases() {
        return bases;
    }

    public void setBases(String bases) throws Exception {

        int counter=0;

        for (int i = 0; i < bases.length(); i++) {

                if (bases.charAt(i) == 'C' || bases.charAt(i) == 'c' || bases.charAt(i) == 'T' || bases.charAt(i) == 't' ||
                        bases.charAt(i) == 'G' || bases.charAt(i) == 'g' || bases.charAt(i) == 'A' || bases.charAt(i) == 'a') {
                    counter++;
                } else
                    throw new Exception("[ERROR] DNA's pattern is incorrect") ;

        }if (bases.length() == counter) this.bases=bases.toUpperCase(Locale.ROOT);

        }
        
    public boolean isProtein() {

        return bases.startsWith("ATG") && bases.endsWith("TGA") &&
                (bases.length() % 3) == 0;
    }

    public void addSequence(String sequence, int... position) throws Exception {

        StringBuilder newSequence = new StringBuilder(getBases());
        String olderBases=getBases();
        int count=0;

        if (position.length<=0) setBases(newSequence.append(sequence).toString());
        else {
            for (int j : position) {
                if (j <= getBases().length()) {
                    count++;
                    setBases(newSequence.insert(j, sequence).toString());
                } else {
                    throw new Exception("[ERROR] position is greater than sequence length");
                }if(count!= position.length)setBases(olderBases);
            }
        }
    }

    public String toRNA() {

       char[] basesCharArray=bases.toCharArray();

       for(int i=0;i<bases.length();i++){
            switch (bases.charAt(i)) {
                   case 'A' -> basesCharArray[i] = 'U';
                   case 'T' -> basesCharArray[i] = 'A';
                   case 'C' -> basesCharArray[i] = 'G';
                   case 'G' -> basesCharArray[i] = 'C';
               }
       }StringBuilder stringBuilder = new StringBuilder(String.valueOf(basesCharArray));
        return stringBuilder.reverse().toString();
    }

    public String sequencesRepeatedLength(int l) {

        StringBuilder repeat=new StringBuilder();
        String subStr;
        String sequences;

       for (int i=0;i<getBases().length();i++){
           if (i + l <= getBases().length()) {
               subStr = getBases().substring(i, i + l);

               for (int j = i + 1; j < getBases().length(); j++) {
                   if (j + l <= getBases().length()) {
                       sequences = getBases().substring(j, j + l);

                       if (subStr.equals(sequences)) {
                           if (repeat.toString().contains(sequences)) continue;
                           repeat.append(sequences).append(" ");
                       }
                   } else break;
               }
           } else break;
       }return repeat.toString();
    }
}


