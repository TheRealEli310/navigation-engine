package com.cleaner;

import java.util.HashSet;

public class DataCleanser {

	public static String StemWords(String input) {
		input = RemoveStopWords(input);
		Porter porterStemmer = new Porter();
		StringBuilder sb = new StringBuilder();
		String[] stemData = input.split(" ");
		for (String str : stemData)
			if (str.length() > 0)
				sb.append(porterStemmer.stripAffixes(str) + " ");
		return sb.toString();
	}

	public static String RemoveTags(String input) {
		String[] tags = { "<div>", "</div>", "<p>", "</p>", "<i>", "</i>", "<b>", "</b>", "<pre>", "</pre>", "<a>",
				"</a>" };
		for (String t : tags) {
			input = input.replace(t, " ");
		}
		return input.replaceAll("[^a-zA-Z0-9]+", " ");
	}

	public static String RemoveCode(String input) {
		if (input.contains("<code>")) {
			String modified = input.substring(0, input.indexOf("<code>")) + " "
					+ input.substring(input.indexOf("</code>") + 7, input.length());
			return RemoveCode(modified);
		} else {
			return input;
		}
	}

	public static String RemoveStopWords(String input) {
		// POPULATE ALL STOP WORDS
		String[] stopWords = { "a", "a's", "able", "about", "above", "according", "accordingly", "across", "actually",
				"after", "afterwards", "again", "against", "ain't", "all", "allow", "allows", "almost", "alone",
				"along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another",
				"any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear",
				"appreciate", "appropriate", "are", "aren't", "around", "as", "aside", "ask", "asking", "associated",
				"at", "available", "away", "awfully", "b", "be", "became", "because", "become", "becomes", "becoming",
				"been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best",
				"better", "between", "beyond", "both", "brief", "but", "by", "c", "c'mon", "c's", "came", "can",
				"can't", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com",
				"come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing",
				"contains", "corresponding", "could", "couldn't", "course", "currently", "d", "definitely", "described",
				"despite", "did", "didn't", "different", "do", "does", "doesn't", "doing", "don't", "done", "down",
				"downwards", "during", "e", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough",
				"entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything",
				"everywhere", "ex", "exactly", "example", "except", "f", "far", "few", "fifth", "first", "five",
				"followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further",
				"furthermore", "g", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got",
				"gotten", "greetings", "h", "had", "hadn't", "happens", "hardly", "has", "hasn't", "have", "haven't",
				"having", "he", "he's", "hello", "help", "hence", "her", "here", "here's", "hereafter", "hereby",
				"herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how",
				"howbeit", "however", "i", "i'd", "i'll", "i'm", "i've", "im", "ive", "ie", "if", "ignored",
				"immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner",
				"insofar", "instead", "into", "inward", "is", "isn't", "it", "it'd", "it'll", "it's", "its", "itself",
				"j", "java", "just", "k", "keep", "keeps", "kept", "know", "knows", "known", "l", "last", "lately",
				"later", "latter", "latterly", "least", "less", "lest", "let", "let's", "like", "liked", "likely",
				"little", "look", "looking", "looks", "ltd", "m", "mainly", "many", "may", "maybe", "me", "mean",
				"meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself",
				"n", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never",
				"nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not",
				"nothing", "novel", "now", "note", "nowhere", "o", "obvious", "obviously", "of", "off", "often", "oh",
				"ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise",
				"ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "p", "particular",
				"particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably",
				"provides", "q", "que", "quite", "qv", "r", "rather", "rd", "re", "really", "reasonably", "regarding",
				"regardless", "regards", "relatively", "respectively", "right", "s", "said", "same", "safe", "saw",
				"say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems",
				"seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall",
				"she", "should", "shouldn't", "since", "six", "so", "some", "somebody", "somehow", "someone",
				"something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify",
				"specifying", "still", "straightforward", "sub", "such", "sup", "sure", "t", "t's", "take", "taken",
				"tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "that's", "thats", "the", "their",
				"theirs", "them", "themselves", "then", "thence", "there", "there's", "thereafter", "thereby",
				"therefore", "therein", "theres", "thereupon", "these", "they", "they'd", "they'll", "they're",
				"they've", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through",
				"throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries",
				"truly", "try", "trying", "twice", "two", "u", "un", "under", "unfortunately", "unless", "unlikely",
				"until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "uucp", "v",
				"value", "various", "very", "via", "viz", "vs", "w", "want", "wants", "was", "wasn't", "way", "we",
				"we'd", "we'll", "we're", "we've", "welcome", "well", "went", "were", "weren't", "what", "what's",
				"whatever", "when", "whence", "whenever", "where", "where's", "whereafter", "whereas", "whereby",
				"wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "who's", "whoever",
				"whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "won't",
				"wonder", "would", "would", "wouldn't", "x", "y", "yes", "yet", "you", "you'd", "you'll", "you're",
				"you've", "your", "yours", "yourself", "yourselves", "z", "zero" };

		// SEPARATE INPUT SENTENCE INTO WORDS
		input = input.replaceAll("[\\t\\n\\r]+", " ");
		String[] splitWords = input.split(" ");
		// BUILD STOP WORD HASH SET
		HashSet<String> stopWordHash = new HashSet<String>();
		for (String s : stopWords)
			stopWordHash.add(s);
		StringBuilder stringBuilder = new StringBuilder();
		// FIND AND REMOVE STOP WORDS
		for (String s : splitWords) {
			s = s.replaceAll("[^0-9a-zA-Z]+", " ").trim().toLowerCase();
			if (s.length() > 0 && !(stopWordHash.contains(s)) && !(isInteger(s))) {
				stringBuilder.append(s + " ");
			}
		}
		return stringBuilder.toString();
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}

		return true;
	}

}
