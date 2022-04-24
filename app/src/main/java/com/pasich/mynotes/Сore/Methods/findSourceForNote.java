package com.pasich.mynotes.Сore.Methods;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A class that receives text in which to find resources, links, mail, phones
 */
public class findSourceForNote {

    /**
     * Method that returns an array of links
     * @param string
     * @return
     */
    public ArrayList<String> getLinks(String string) {
        ArrayList<String> arrayLink = new ArrayList<>();
        Matcher match = Pattern
                .compile("(https?|www|http)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
                .matcher(string);

        while (match.find())
            arrayLink.add(match.group());
        return arrayLink;
    }
}
