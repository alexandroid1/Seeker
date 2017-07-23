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
        items.put("JavaScript", new Double(1.0));
    }

    private static final String profileStr = "\n" +
            "\n" +
            "    Sliceart\n" +
            "    JavaScript developer\n" +
            "    Company Name Sliceart\n" +
            "    Dates Employed Nov 2016 – Present Employment Duration 9 mos\n" +
            "    Location Харьков, Украина\n" +
            "\n" +
            "    Lazy Ants\n" +
            "    Junior JavaScript developer\n" +
            "    Company Name Lazy Ants\n" +
            "    Dates Employed Jul 2016 – Nov 2016 Employment Duration 5 mos\n" +
            "    Location Харьков\n";

    public static void main(String[] args) {
        HashMap<String, Double> mapWithWaights = getMeaningOfText(profileStr, items);
        ValueComparator bvc = new ValueComparator(mapWithWaights);
        TreeMap<String, Double> sorted_map = new TreeMap<>(bvc);
        sorted_map.putAll(mapWithWaights);
        System.out.println("results: " + sorted_map);
    }

    private static HashMap<String, Double> getMeaningOfText(String profileStr, HashMap<String, Double> items) {
        for(String keyStr : items.keySet()){
            int count = getCountInText(profileStr, keyStr);
            int firstOccurrence = profileStr.indexOf(keyStr);
            double waight;
            if (count!=0)  waight= (double) count/firstOccurrence;
            else waight = 0;
            items.put(keyStr, waight);
        }
        return items;
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
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    static class ValueComparator implements Comparator<String> {
        Map<String, Double> base;

        public ValueComparator(Map<String, Double> base) {
            this.base = base;
        }

        // Note: this comparator imposes orderings that are inconsistent with
        // equals.
        public int compare(String a, String b) {
            if (base.get(a) >= base.get(b)) {
                return -1;
            } else {
                return 1;
            } // returning 0 would merge keys
        }
    }
}
