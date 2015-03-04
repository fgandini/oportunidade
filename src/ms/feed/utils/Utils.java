package ms.feed.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Utils {

	//http://www.ranks.nl/stopwords/portugese
	//http://miningtext.blogspot.com.br/2008/11/listas-de-stopwords-stoplist-portugues.html
	private static final List<String> dictionary = Arrays.asList("a","acerca","agora","algmas","alguns","ali","ambos","antes","apontar","aquela","aquelas","aquele","aqueles","aqui","atrás","bem","bom","cada","caminho","cima","com","como","comprido","conhecido","corrente","das","debaixo","dentro","desde","desligado","deve","devem","deverá","direita","diz","dizer","dois","dos","e","é","ela","ele","eles","em","enquanto","então","está","estado","estão","estar","estará","este","estes","esteve","estive","estivemos","estiveram","eu","fará","faz","fazer","fazia","fez","fim","foi","fora","horas","iniciar","inicio","ir","irá","ista","iste","isto","ligado","maioria","maiorias","mais","mas","mesmo","meu","muito","muitos","não","nome","nós","nosso","novo","o","onde","os","ou","outro","para","parte","pegar","pelo","pessoas","pode","poderá","podia","por","porque","povo","promeiro","qual","qualquer","quando","quê","quem","quieto","saber","são","sem","ser","seu","somente","tal","também","tem","têm","tempo","tenho","tentar","tentaram","tente","tentei","teu","teve","tipo","tive","todos","trabalhar","trabalho","tu","último","um","uma","umas","uns","usa","usar","valor","veja","ver","verdade","você","à","ainda","alguém","algum","alguma","algumas","ampla","amplas","amplo","amplos","ante","ao","aos","após","aquilo","as","até","através","coisa","coisas","contra","contudo","da","daquele","daqueles","de","dela","delas","dele","deles","depois","dessa","dessas","desse","desses","desta","destas","deste","destes","devendo","dever","deverão","deveria","deveriam","devia","deviam","disse","disso","disto","dito","dizem","do","e'","elas","entre","era","essa","essas","esse","esses","esta","estamos","estas","estava","estavam","estávamos","estou","fazendo","feita","feitas","feito","feitos","for","foram","fosse","fossem","grande","grandes","há","isso","já","la","lá","lhe","lhes","lo","me","mesma","mesmas","mesmos","meus","minha","minhas","muita","muitas","na","nas","nem","nenhum","nessa","nessas","nesta","nestas","ninguém","no","nos","nossa","nossas","nossos","num","numa","nunca","outra","outras","outros","pela","pelas","pelos","pequena","pequenas","pequeno","pequenos","per","perante","pôde","podendo","poder","poderia","poderiam","podiam","pois","porém","posso","pouca","poucas","pouco","poucos","primeiro","primeiros","própria","próprias","próprio","próprios","quais","quanto","quantos","que","se","seja","sejam","sempre","sendo","será","serão","seus","si","sido","só","sob","sobre","sua","suas","talvez","tampouco","te","tendo","tenha","ter","teus","ti","tido","tinha","tinham","toda","todas","todavia","todo","tua","tuas","tudo","última","últimas","últimos","vendo","vez","vindo","vir","vos","vós");


	public static String cleanupContent (String content){

		String contentCleaned = content;
		contentCleaned = contentCleaned.toLowerCase();
		contentCleaned = contentCleaned.replaceAll("<.*?>", "");
		contentCleaned = contentCleaned.replaceAll("–", "");
		contentCleaned = contentCleaned.replaceAll("\\p{Punct}","").replaceAll("\\s+"," ");
		
		return contentCleaned;
	}	

	public static int countWordsTotal (String content, String regex){

		int totalWords = 0;		

		for (String string : content.split(regex)) {
			//vendo se a palavra em questão NÃO esta na lista de preposições e artigos
			if(!dictionary.contains(string)){
				totalWords += 1;				
			}
		}

		return totalWords;
	}	

	public static List<Entry<String, Integer>> rankingWordsCount(String content, String regex){

		List<Entry<String,Integer>> finalResult; 
		
		Map<String, Integer> countByWord = new HashMap<>();
		
		for (String word : content.split(regex)) {  
			
			//se a palavra estiver contida no array de artigos e preposições, ele pula para a próxima 
			if (dictionary.contains(word)) continue;			
			
			Integer count = countByWord.get(word);
			if (count == null) {
				count = 0;
			}
			count++;
			countByWord.put(word, count);
		}
	
		finalResult = new ArrayList<>(countByWord.entrySet());
		Collections.sort(finalResult, new EntryComparator());		
		
		return finalResult;
	}


	
	private static class EntryComparator implements Comparator<Entry<String,Integer>> {

		public int compare(Entry<String,Integer> v1, Entry<String,Integer> v2) {			
			
			if (v1.getValue() != v2.getValue()) {
				//v2 - v1 caso contrario o sort vai ser ascendente
				return v2.getValue() - v1.getValue();
			}			
			return v1.getKey().compareTo(v2.getKey());

		}
	}
}
