package com.fast0n.parsingxml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parseXML();
    }

    private void parseXML() {
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();

            //Qui va inserito il percorso del XML
            InputStream is = getAssets().open("data.xml");

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            processParsing(parser);
        } catch (XmlPullParserException e) {
        } catch (IOException e) {
        }
    }

    private void processParsing(XmlPullParser parser) throws IOException, XmlPullParserException{

        ArrayList<Parsing> elements = new ArrayList<>();
        int eventType = parser.getEventType();
        Parsing currentElement = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName = null;

            switch (eventType) {
            case XmlPullParser.START_TAG:
                eltName = parser.getName();

                // entra dentro la specifica tabella <elements>
                if ("elements".equals(eltName)) {
                    currentElement = new Parsing();
                    elements.add(currentElement);
                } else if (currentElement != null) {
                    if ("name".equals(eltName)) {
                        currentElement.name = parser.nextText();
                    } else if ("age".equals(eltName)){
                        currentElement.age = parser.nextText();
                    } else if ("position".equals(eltName)){
                        currentElement.position = parser.nextText();
                    }
                }
                break;

            }
            eventType = parser.next();
        }
            printElements(elements);
    }

    private void printElements(ArrayList<Parsing> elements){
        StringBuilder builder = new StringBuilder();
        
        for (Parsing element : elements){
            builder.append(element.name).append("\n").append(element.age).append("\n").append(element.position).append("\n\n");
        }

        String[] array = builder.toString().split("\n\n");

        for(int i=0; i<=array.length-1; i++){

            Toast.makeText(MainActivity.this, array[i], Toast.LENGTH_LONG).show();
        }
    }
}
