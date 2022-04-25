package com.pasich.mynotes.Сore.Methods;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A class that receives text in which to find resources, links, mail, phones
 */
public class findSourceForNote {

    private final String textString;

    public findSourceForNote(String string){
        this.textString = string;
    }

    /**
     * Method that returns an array of links
     * @return - ArrayList<String>
     */
    public ArrayList<String> getLinks() {
        ArrayList<String> arrayLink = new ArrayList<>();
        String regLink = "(https?|www|http)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Matcher match = Pattern
                .compile(regLink)
                .matcher(this.textString);

        while (match.find())
            arrayLink.add(match.group());
        return arrayLink;
    }

    /**
     * Method that returns an array of Mail
     * @return - ArrayList<String>
     */
    public ArrayList<String> getMail(){
        ArrayList<String> arrayMail = new ArrayList<>();
        String regMail = "[A-Za-z0-9+_.-]+@(.+)";
        Matcher match = Pattern
                .compile(regMail)
                .matcher(this.textString);

        while (match.find())
            arrayMail.add(match.group());
        return arrayMail;
    }

    /**
     * Method that returns an array of Phone Number
     * @return - ArrayList<String>
     */
    public ArrayList<String> getPhoneNumber(){
        ArrayList<String> arrayMail = new ArrayList<>();
        String regPhoneNumber = "(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}"
                + "|(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}"
                + "|(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}";
        Matcher match = Pattern
                .compile(regPhoneNumber)
                .matcher(this.textString);

        while (match.find())
            arrayMail.add(match.group());
        return arrayMail;
    }


}
