package com.pasich.mynotes.utils;

import com.pasich.mynotes.data.model.SourceModel;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchSourceNote {

    private final String textString;


    public SearchSourceNote(String textString) {
        this.textString = textString;
    }


    public ArrayList<SourceModel> getListSources(String type) {
        ArrayList<SourceModel> listArray = new ArrayList<>();
        switch (type) {
            case "Url":
                for (String link : this.getLinks()) {
                    listArray.add(new SourceModel(link, "Url"));
                }
                break;
            case "Mail":
                for (String mail : getMail()) {
                    listArray.add(new SourceModel(mail, "Mail"));
                }
                break;
            case "Tel":
                for (String number : getPhoneNumber()) {
                    listArray.add(new SourceModel(number, "Tel"));
                }
                break;
        }

        return listArray;
    }

    /**
     * Method that returns an array of links
     *
     * @return - ArrayList<String>
     */
    private ArrayList<String> getLinks() {
        ArrayList<String> arrayLink = new ArrayList<>();
        String regLink = "(https?|www|http)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Matcher match = Pattern.compile(regLink).matcher(textString);

        while (match.find()) if (!arrayLink.contains(match.group())) arrayLink.add(match.group());
        return arrayLink;
    }

    /**
     * Method that returns an array of Mail
     *
     * @return - ArrayList<String>
     */
    private ArrayList<String> getMail() {
        ArrayList<String> arrayMail = new ArrayList<>();
        String regMail = "[A-Za-z0-9+_.-]+@(.+)";
        Matcher match = Pattern.compile(regMail).matcher(textString);

        while (match.find()) if (!arrayMail.contains(match.group())) arrayMail.add(match.group());
        return arrayMail;
    }

    /**
     * Method that returns an array of Phone Number
     *
     * @return - ArrayList<String>
     */
    private ArrayList<String> getPhoneNumber() {
        ArrayList<String> arrayNumber = new ArrayList<>();
        String regPhoneNumber =
                "(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}"
                        + "|(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}"
                        + "|(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}";
        Matcher match = Pattern.compile(regPhoneNumber).matcher(textString);

        while (match.find())
            if (!arrayNumber.contains(match.group())) arrayNumber.add(match.group());
        return arrayNumber;
    }

}
