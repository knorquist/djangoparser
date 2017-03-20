package cs540;

import org.jsoup.*;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Written by Kyle Norquist, 2017
 *
 */
public class App {
    public static void main(String[] args) {
        String url = args[0];
        int django = 0;
        boolean djangoAdmin = false;
        boolean csrfmiddlewaretoken = false;

        System.out.println(
                "\n" +
                "=====================================================================================\n" +
                "                               DJANGO CHECKER (ALPHA)                                \n" +
                "=====================================================================================\n" +
                "This utility grabs the HTML from the URL provided and does three things:             \n" +
                "  1. Counts occurrences of the word \"Django\",                                        \n" +
                "  2. Checks if it can find the admin page,                                           \n" +
                "  3. Checks for the csrfmiddlewaretoken, a Django form idiom.                        \n" +
                "If it can find the admin page, it will then check to see if it is a Django admin page\n" +
                "with a simple keyword search, since developers often don't customize this page.      \n" +
                "Remember, if the developer has done their job right, you shouldn't be able to tell if\n" +
                "they used Django. This utility merely scans for things developers often forget about.\n" +
                "=====================================================================================\n");

        //Find Page
        try {
            Document doc = Jsoup.connect(url).get(); //Download HTML from URL
            System.out.println("Found Page. Testing...");

            String htmlAsString = doc.toString();
            ArrayList<String> tokensList = new ArrayList<String>();
            StringTokenizer tokenizer = new StringTokenizer(htmlAsString, " #.<>\n=+-\"\'");

            while(tokenizer.hasMoreTokens()) {
                tokensList.add(tokenizer.nextToken().toLowerCase());
            }
            tokensList.trimToSize();
            //System.out.println(workingList.toString());

            for (int i = 0; i < tokensList.size(); i++) {
                if(tokensList.get(i).equals("django")) {
                    django++;
                }
            }
            for (int i = 0; i < tokensList.size(); i++) {
                if(tokensList.get(i).equals("csrfmiddlewaretoken")) {
                    csrfmiddlewaretoken = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Invalid URL.");
            System.exit(0);
        }

        //Find Admin Page
        try {
            String adminUrl = url + "/admin";
            Document doc = Jsoup.connect(adminUrl).get();
            System.out.println("Found Admin Page. Testing...");

            String htmlAsString = doc.toString();
            ArrayList<String> tokensList = new ArrayList<String>();
            StringTokenizer tokenizer = new StringTokenizer(htmlAsString, " #.<>\n=+-\"\'");

            while(tokenizer.hasMoreTokens()) {
                tokensList.add(tokenizer.nextToken().toLowerCase());
            }
            tokensList.trimToSize();

            for (int i = 0; i < tokensList.size(); i++) {
                if(tokensList.get(i).equals("django")) {
                    django++;
                    djangoAdmin = true;
                }
            }
            for (int i = 0; i < tokensList.size(); i++) {
                if(tokensList.get(i).equals("csrfmiddlewaretoken")) {
                    csrfmiddlewaretoken = true;
                }
            }

        } catch (IOException e) {
            System.out.println("Can't find admin URL");
        }



        System.out.println("                                     RESULTS                                         \n" +
                           "=====================================================================================\n" +
                           "Django Admin panel found?    |"   + djangoAdmin + "\n" +
                           "\"Django\" found:              |" + django + " time(s).\n" +
                           "\"csrfmiddlewaretoken\" found? |" + csrfmiddlewaretoken + "\n" +
                           "=====================================================================================");

        if(djangoAdmin == true && django > 0 && csrfmiddlewaretoken == true) {
            System.out.println("This site is probably built using Django.");
        }
        else if(djangoAdmin == false && django > 0 && csrfmiddlewaretoken == true) {
            System.out.println("This site could have been built using Django.");
        }
        else if(djangoAdmin == false && django > 0 && csrfmiddlewaretoken == false) {
            System.out.println("\"Django\" was found, but hard to tell if Django was used.");
        }
        else if(djangoAdmin == false && django == 0 && csrfmiddlewaretoken == false) {
            System.out.println("Unable to determine if Django was used.");
        }
        else {
            System.out.println("Unable to determine if Django was used.");
        }
        System.exit(1);
    }
}
