import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TextAnalyzer {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        String paragraph = readInput(scanner);
        
        int totalCharacters = paragraph.length();
        int totalWords = countWords(paragraph);
        char mostCommonChar = findMostCommonChar(paragraph);
        
        char searchChar = getCharacterFromUser(scanner);
        int charOccurrences = countCharacter(paragraph, searchChar);
        
        String searchWord = getWordFromUser(scanner);
        int wordOccurrences = countWord(paragraph, searchWord);
        
        int uniqueWordCount = countUniqueWords(paragraph);
        
        printResults(paragraph, totalCharacters, totalWords, mostCommonChar, 
                    searchChar, charOccurrences, searchWord, wordOccurrences, uniqueWordCount);
        
        scanner.close();
    }
    
    private static String readInput(Scanner scanner) {
        System.out.println("Enter a long text:");
        String input = scanner.nextLine();
        
        while (input.trim().isEmpty()) {
            System.out.println("Please enter some text, it cannot be empty:");
            input = scanner.nextLine();
        }
        
        return input;
    }
    
    private static int countWords(String text) {
        if (text.trim().isEmpty()) return 0;
        return text.trim().split("\\s+").length;
    }
    
    private static char findMostCommonChar(String text) {
        HashMap<Character, Integer> charFreq = new HashMap<>();
        String lowerText = text.toLowerCase();
        
        for (int i = 0; i < lowerText.length(); i++) {
            char character = lowerText.charAt(i);
            if (character != ' ') {
                if (charFreq.containsKey(character)) {
                    charFreq.put(character, charFreq.get(character) + 1);
                } else {
                    charFreq.put(character, 1);
                }
            }
        }
        
        char result = ' ';
        int max = 0;
        
        for (char key : charFreq.keySet()) {
            if (charFreq.get(key) > max) {
                max = charFreq.get(key);
                result = key;
            }
        }
        
        return result;
    }
    
    private static char getCharacterFromUser(Scanner scanner) {
        System.out.println("\nPlease enter a character you want to search on the text:");
        String input = scanner.nextLine();
        
        while (input.length() != 1) {
            System.out.println("You cannot input more than one character. Please enter exactly one character:");
            input = scanner.nextLine();
        }
        
        return input.charAt(0);
    }
    
    private static int countCharacter(String text, char target) {
        int count = 0;
        char lowerTarget = Character.toLowerCase(target);
        
        for (char c : text.toLowerCase().toCharArray()) {
            if (c == lowerTarget) count++;
        }
        
        return count;
    }
    
    private static String getWordFromUser(Scanner scanner) {
        System.out.println("\nPlease enter a word you want to search on the text:");
        String input = scanner.nextLine().trim();
        
        while (input.isEmpty() || input.contains(" ")) {
            System.out.println("You cannot input more than one word. Please enter a single word (no spaces):");
            input = scanner.nextLine().trim();
        }
        
        return input;
    }
    
    private static int countWord(String text, String target) {
        String[] words = text.trim().split("\\s+");
        int count = 0;
        
        for (String word : words) {
            if (word.equalsIgnoreCase(target)) {
                count++;
            }
        }
        
        return count;
    }
    
    private static int countUniqueWords(String text) {
        String[] words = text.trim().split("\\s+");
        HashSet<String> unique = new HashSet<>();
        
        for (String word : words) {
            unique.add(word.toLowerCase());
        }
        
        return unique.size();
    }
    
    private static void printResults(String text, int chars, int words, char commonChar,
                                    char searchChar, int charCount, String searchWord, 
                                    int wordCount, int uniqueWords) {
        String separator = "=".repeat(50);
        String subSeparator = "-".repeat(50);
        System.out.println("\n" + separator);
        System.out.println("TEXT ANALYSIS");
        System.out.println(separator);
        System.out.println("\nOriginal text:\n" + text);
        System.out.println("\n" + subSeparator);
        System.out.println("Total characters: " + chars);
        System.out.println("Total words: " + words);
        System.out.println("Most common character: '" + commonChar + "'");
        System.out.println("'" + searchChar + "' appears " + charCount + " times");
        System.out.println("\"" + searchWord + "\" appears " + wordCount + " times");
        System.out.println("Unique words: " + uniqueWords);
        System.out.println(separator);
    }
}
