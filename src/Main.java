import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static HashMap<String, Double> items = new LinkedHashMap<>();
    static {
        items.put("support", new Double(1.0));
        items.put("software", new Double(1.0));
        items.put("recruiter", new Double(1.0));
        items.put("DevOps",new Double(1.0));
        items.put("java", new Double(1.0));
    }

    private static final String profileStr = "\n" +
            "    Miratech\n" +
            "    senior professional services consultant for Genesys products\n" +
            "    Company Name Miratech\n" +
            "    Dates Employed Apr 2016 – Present Employment Duration 1 yr 4 mos\n" +
            "\n" +
            "    • Development of turn-key solutions and architectures on top of Genesys products\n" +
            "    • Full integration of third-party application with Genesys applications (from collecting business requirements till actual code development and delivery)\n" +
            "    • Support of live Genesys environments for several key customers\n" +
            "    • Teaching internal and customer staff\n" +
            "    Cosi Consulting\n" +
            "    Java technical support engineer\n" +
            "    Company Name Cosi Consulting\n" +
            "    Dates Employed Sep 2015 – Mar 2016 Employment Duration 7 mos\n" +
            "\n" +
            "    • Development of new features and implementation of requirements from customers\n" +
            "    • Configuration and maintenance of Document Management Systems: EMC Docu, IBM FileNet\n" +
            "    • Configuration and maintenance of in-house lab environments\n" +
            "    • Development of technical documentation\n" +
            "    Miratech\n" +
            "    tier 3 technical support engineer in Genesys project\n" +
            "    Company Name Miratech\n" +
            "    Dates Employed Dec 2010 – Sep 2015 Employment Duration 4 yrs 10 mos\n" +
            "\n" +
            "    • Support of different application platforms:\n" +
            "    SDKs, Web Engagement, eServices, SIP Server, Voice Platform, Workforce Management\n" +
            "    • Troubleshooting and simulation of problems faced by customers\n" +
            "    • Analysis and improvement of customer's code/architecture\n" +
            "    • Constant communication with development teams responsible for products\n" +
            "    • Teaching internal and customer staff\n" +
            "    Genesys\n" +
            "    tier 3 technical support engineer\n" +
            "    Company Name Genesys\n" +
            "    Dates Employed Dec 2010 – Sep 2015 Employment Duration 4 yrs 10 mos\n" +
            "\n" +
            "    • Support of different application platforms:\n" +
            "    SDKs, Web Engagement, eServices, SIP Server, Voice Platform, Workforce Management\n" +
            "    • Troubleshooting and simulation of problems faced by customers\n" +
            "    • Analysis and improvement of customer's code/architecture\n" +
            "    • Constant communication with development teams responsible for products\n" +
            "    • Teaching internal and customer staff\n" +
            "    Utel\n" +
            "    system administrator of service platforms department\n" +
            "    Company Name Utel\n" +
            "    Dates Employed Nov 2008 – Nov 2010 Employment Duration 2 yrs 1 mo\n" +
            "\n" +
            "    • Maintenance, operation and monitoring software/hardware platforms used for Value Added Services\n" +
            "    • Issue troubleshooting, collaboration with vendor's tech personal\n" +
            "    • Configuration, installation and maintenance of network and server equipment\n" +
            "    • Development, support and integration of supplementary monitoring systems";

    public static void main(String[] args) {
        String key = getMeaningOfText(profileStr, items);
        System.out.println(key);
    }

    private static String getMeaningOfText(String profileStr, HashMap<String, Double> items) {
        for(String keyStr : items.keySet()){
            int count = getCountInText(profileStr, keyStr);
            int firstOccurrence = profileStr.indexOf(keyStr);
            double waight = (double) count/firstOccurrence;
            items.put(keyStr, waight);
        }
        sortByValue(items);
        Map.Entry<String,Double> entry = items.entrySet().iterator().next();
        return entry.getKey();
    }

    private static int getCountInText(String strText, String strSearch) {
        int count = 0;
        String pattern = "\\b"+strSearch+"\\b";
        Pattern p = Pattern.compile(pattern, Pattern.UNICODE_CASE|Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(strText);
        while(m.find()) count++;
        return count;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(/*Collections.reverseOrder()*/))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
