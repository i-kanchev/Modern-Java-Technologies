import java.util.Objects;

public class PrefixExtractor {
    public static String getLongestCommonPrefix(String[] words) {
        if (words == null)
            return "";
        if (words.length == 0)
            return "";
        if (words[0] == null)
            return "";

        int shortestWord = Integer.MAX_VALUE;

        for (int i = 0; i < words.length ; i++) {
            if (words[i].length() < shortestWord)
                shortestWord = words[i].length();
        }

        if (shortestWord == Integer.MAX_VALUE)
            return "";

        int commonIndex = 0;

        for (int i = 0; i < shortestWord ; i++) {
            for (int j = 1; j < words.length; j++) {
                if (words[j].charAt(i) != words[0].charAt(i)) {
                    return words[0].substring(0, commonIndex);
                }
            }
            commonIndex++;
        }

        return words[0].substring(0, commonIndex);
    }

    public static void main(String[] args) {
        System.out.println(getLongestCommonPrefix(new String[]{"flower", "flow", "flight"}));
        System.out.println(getLongestCommonPrefix(new String[]{"dog", "racecar", "car"}));
        System.out.println(getLongestCommonPrefix(new String[]{"cat"}));
    }
}