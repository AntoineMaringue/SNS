package API;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ParseurJSON implements IParseur {

    private Map<String, String> informationsJson;
     ArrayList<String> words = new ArrayList<>();
        

    public ParseurJSON() {
        informationsJson = new HashMap<>();
        words.add("cl");
        words.add("L");
        words.add("l");
        words.add("ml");
        words.add("kg");
        words.add("g");
        words.add("mg");
    }

    @Override
    public Map<String, String> getInformations(String requestHttpForGoogle) {
        JSONParser parser = null;
        ContainerFactory containerFactory = null;
        Map json = null;
        Iterator iter = null;
        Map.Entry entry = null;


        parser = new JSONParser();
        containerFactory = new ContainerFactory() {
            @Override
            public List creatArrayContainer() {
                return new LinkedList();
            }

            @Override
            public Map createObjectContainer() {
                return new LinkedHashMap();
            }
        };

        try {
            json = (Map) parser.parse(requestHttpForGoogle, containerFactory);
            iter = json.entrySet().iterator();
            System.out.println("==iterate result==");
            while (iter.hasNext()) {
                entry = (Map.Entry) iter.next();
                System.out.println(entry.getKey() + "=>" + entry.getValue());
                if (entry.getKey().equals("totalItems")) {
                    if (Integer.parseInt(entry.getValue().toString()) <= 0) {
                        return new HashMap<>();
                    }
                } else if (entry.getKey().equals("items")) {

                    int x = -1;
                    for (int i = 0; i < entry.getValue().toString().split(", ").length; i++) {
                        String value = entry.getValue().toString().split(", ")[i];
                        System.out.println(value);
                        if (value.contains("price")
                                || value.trim().contains("brand")
                                || value.trim().contains("description")
                                || value.trim().contains("creationTime")
                                || value.trim().contains("modificationTime")
                                || value.trim().contains("images")) {
                            x++;
                            switch (x) {
                                case 0:
                                    informationsJson.put("creationTime", value.split("=")[1]);
                                    break;
                                case 1:
                                    informationsJson.put("modificationTime", value.split("=")[1]);
                                    break;
                                case 2:
                                    informationsJson.put("description", value.split("=")[1]);
                                    informationsJson.put("contenance", getDoubleToString(value.split("=")[1])+"");
                                    informationsJson.put("unite", getContenanceToString(value.split("=")[1],words)+"");
                                    break;
                                case 3:
                                    informationsJson.put("brand", value.split("=")[1]);
                                    break;
                                case 4:
                                    informationsJson.put("price", value.split("=")[1]);
                                    break;
                                case 5:
                                    informationsJson.put("img", value.split("=")[2]);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            }
        } catch (ParseException pe) {
            System.out.println(pe);
        }


        return informationsJson;
    }
    
    private static Integer getNumberToString(String s)
    {
        Pattern intsOnly = Pattern.compile("\\d+");
        Matcher makeMatch = intsOnly.matcher(s);
        makeMatch.find();
        String inputInt = makeMatch.group();
        System.out.println(inputInt);
        return Integer.parseInt(inputInt);
    }

    private static Double getDoubleToString(String s) {
        String doubleString = "0.0";
        Pattern doubleOnly = Pattern.compile("\\d+\\.\\d+");
        s = s.replaceAll(",", ".");
        Matcher makeMatch = doubleOnly.matcher(s);
        if(makeMatch.find())
        {
            doubleString = makeMatch.group();
            System.out.println(doubleString);
        }
        return Double.parseDouble(doubleString);
    }
    
    private static String getContenanceToString(String s,ArrayList<String> pattern) 
    {
        String contenance = "";
        for (String string : pattern) 
        {
            try
            {
                Pattern doubleOnly = Pattern.compile(string);
                Matcher makeMatch = doubleOnly.matcher(s);
                
                if(makeMatch.find())
                {
                    String doubleString = makeMatch.group();
                    System.out.println(doubleString);
                    contenance += doubleString;
                }
            }
            catch(IllegalStateException ise)
            {
            
            }
        }
        
        return contenance;
    }
}
