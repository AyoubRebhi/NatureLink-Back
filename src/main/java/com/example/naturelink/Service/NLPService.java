package com.example.naturelink.Service;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

// NLPService.java
@Service
public class NLPService {

    private final Tokenizer tokenizer;
    private final POSTaggerME posTagger;
    private final List<String> TARGET_SKILLS = List.of(
            "Java", "Angular", "Spring Boot", "AI", "TypeScript", "Security"
    );

    public NLPService() throws IOException {
        // Initialize tokenizer
        try (InputStream tokenModel = getClass().getResourceAsStream("/nlp-models/en-token.bin")) {
            this.tokenizer = new TokenizerME(new TokenizerModel(tokenModel));
        }

        // Initialize POS tagger
        try (InputStream posModel = getClass().getResourceAsStream("/nlp-models/en-pos-maxent.bin")) {
            this.posTagger = new POSTaggerME(new POSModel(posModel));
        }
    }

    public List<String> analyzeBio(String text) {
        List<String> suggestions = new ArrayList<>();
        if (text == null || text.isEmpty()) return suggestions;

        String cleanText = text.replaceAll("[^a-zA-Z0-9.,!? ]", "");
        String[] sentences = cleanText.split("[.!?]");
        String[] tokens = tokenizer.tokenize(cleanText);
        String[] tags = posTagger.tag(tokens);

        // 1. Passive voice detection
        suggestions.addAll(detectPassiveVoice(tokens, tags));

        // 2. Quantifiable achievements check
        if (!cleanText.matches(".*\\d+.*")) {
            suggestions.add("Add quantifiable achievements (e.g., 'Increased performance by 40%')");
        }

        // 3. Verb diversity analysis
        suggestions.addAll(analyzeVerbDiversity(tokens, tags));

        // 4. Structure analysis
        if (sentences.length > 5) {
            suggestions.add("Consider breaking long paragraphs into bullet points");
        }

        // 5. Keyword richness check
        suggestions.addAll(analyzeKeywords(tokens, tags));

        // 6. Sentence starter variety
        suggestions.addAll(analyzeSentenceStarters(sentences));

        return suggestions.stream().distinct().limit(8).collect(Collectors.toList());
    }

    private List<String> detectPassiveVoice(String[] tokens, String[] tags) {
        List<String> passiveSuggestions = new ArrayList<>();
        boolean passiveDetected = false;

        for (int i = 0; i < tags.length; i++) {
            if (tags[i].startsWith("VB") && i+1 < tags.length) {
                String verb = tokens[i].toLowerCase();
                if ((verb.equals("is") || verb.equals("was") || verb.equals("were"))
                        && tags[i+1].equals("VBN")) {
                    passiveDetected = true;
                    break;
                }
            }
        }

        if (passiveDetected) {
            passiveSuggestions.add("Use active voice instead of passive (e.g., 'Developed X' instead of 'X was developed')");
        }
        return passiveSuggestions;
    }

    private List<String> analyzeVerbDiversity(String[] tokens, String[] tags) {
        Set<String> verbs = new HashSet<>();
        for (int i = 0; i < tags.length; i++) {
            if (tags[i].startsWith("VB")) {
                verbs.add(tokens[i].toLowerCase());
            }
        }

        List<String> verbSuggestions = new ArrayList<>();
        if (verbs.size() < 4) {
            verbSuggestions.add("Use more varied action verbs (e.g., developed, implemented, optimized)");
        }
        return verbSuggestions;
    }

    private List<String> analyzeKeywords(String[] tokens, String[] tags) {
        List<String> keywords = new ArrayList<>();
        for (int i = 0; i < tags.length; i++) {
            if (tags[i].startsWith("NN") && tokens[i].length() > 3) {
                keywords.add(tokens[i]);
            }
        }

        List<String> keywordSuggestions = new ArrayList<>();
        if (keywords.size() > 3) {
            keywordSuggestions.add("Elaborate on key terms: " +
                    keywords.stream().distinct().limit(3).collect(Collectors.joining(", ")));
        }
        return keywordSuggestions;
    }

    private List<String> analyzeSentenceStarters(String[] sentences) {
        Set<String> starters = new HashSet<>();
        for (String sentence : sentences) {
            String firstWord = sentence.trim().split("\\s+")[0].toLowerCase();
            if (firstWord.length() > 0) {
                starters.add(firstWord);
            }
        }

        List<String> starterSuggestions = new ArrayList<>();
        if (starters.size() < sentences.length/2) {
            starterSuggestions.add("Vary sentence starters to improve readability");
        }
        return starterSuggestions;
    }
}