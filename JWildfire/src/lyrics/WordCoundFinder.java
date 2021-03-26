package lyrics;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class WordCoundFinder {
    public static void findWordCount() throws Exception {
        String line, word = "";
        String temp;
        String regex = "[!._,'@? ]";
        int count = 0, maxCount = 0;
        List<String> words = new ArrayList<String>();
        List<String> topFiveWords = new ArrayList<String>();

        //Opens file in read mode
        FileReader file = new FileReader("D:\\JWildFireFiles\\" +"lyricsData.txt ");
        BufferedReader br = new BufferedReader(file);

        //Reads each line
        while((line = br.readLine()) != null) {
            line = line.toLowerCase();
            StringTokenizer str = new StringTokenizer(line,regex);
            while(str.hasMoreTokens()) {
                words.add(str.nextToken());
            }
        }

//        System.out.println(words);

        for (int x = 0 ; x < 10 ; x++){
//            System.out.println("Iteration :" + x);
            //Determine the most repeated word in a file
            for(int i = 0; i < words.size(); i++){
                count = 1;
                //Count each word in the file and store it in variable count
                for(int j = i+1; j < words.size(); j++){
                    if(words.get(i).equals(words.get(j))){
                        count++;
                    }
                }
                //If maxCount is less than count then store value of count in maxCount
                //and corresponding word to variable word
                if(count > maxCount){
                    maxCount = count;
                    word = words.get(i);
                }
            }

            topFiveWords.add(word);

            for (int a = 0 ; a < words.size(); a++){
                if(words.get(a).equals(word)){
                    a = 0;
//                    System.out.println("A"+ words);
                    words.remove(word);
                }
//                System.out.println("AA"+a);
            }
//        System.out.println(words);
//        System.out.println(words.size());
            maxCount = 0;
        }

        System.out.println("Most repeated five words: " + topFiveWords);
        br.close();

        JOptionPane.showMessageDialog(null, "Most repeated ten words: "+ topFiveWords);
    }
}