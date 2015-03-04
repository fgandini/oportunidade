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
	private static final List<String> dictionary = Arrays.asList("a","acerca","agora","algmas","alguns","ali","ambos","antes","apontar","aquela","aquelas","aquele","aqueles","aqui","atr�s","bem","bom","cada","caminho","cima","com","como","comprido","conhecido","corrente","das","debaixo","dentro","desde","desligado","deve","devem","dever�","direita","diz","dizer","dois","dos","e","�","ela","ele","eles","em","enquanto","ent�o","est�","estado","est�o","estar","estar�","este","estes","esteve","estive","estivemos","estiveram","eu","far�","faz","fazer","fazia","fez","fim","foi","fora","horas","iniciar","inicio","ir","ir�","ista","iste","isto","ligado","maioria","maiorias","mais","mas","mesmo","meu","muito","muitos","n�o","nome","n�s","nosso","novo","o","onde","os","ou","outro","para","parte","pegar","pelo","pessoas","pode","poder�","podia","por","porque","povo","promeiro","qual","qualquer","quando","qu�","quem","quieto","saber","s�o","sem","ser","seu","somente","tal","tamb�m","tem","t�m","tempo","tenho","tentar","tentaram","tente","tentei","teu","teve","tipo","tive","todos","trabalhar","trabalho","tu","�ltimo","um","uma","umas","uns","usa","usar","valor","veja","ver","verdade","voc�","�","ainda","algu�m","algum","alguma","algumas","ampla","amplas","amplo","amplos","ante","ao","aos","ap�s","aquilo","as","at�","atrav�s","coisa","coisas","contra","contudo","da","daquele","daqueles","de","dela","delas","dele","deles","depois","dessa","dessas","desse","desses","desta","destas","deste","destes","devendo","dever","dever�o","deveria","deveriam","devia","deviam","disse","disso","disto","dito","dizem","do","e'","elas","entre","era","essa","essas","esse","esses","esta","estamos","estas","estava","estavam","est�vamos","estou","fazendo","feita","feitas","feito","feitos","for","foram","fosse","fossem","grande","grandes","h�","isso","j�","la","l�","lhe","lhes","lo","me","mesma","mesmas","mesmos","meus","minha","minhas","muita","muitas","na","nas","nem","nenhum","nessa","nessas","nesta","nestas","ningu�m","no","nos","nossa","nossas","nossos","num","numa","nunca","outra","outras","outros","pela","pelas","pelos","pequena","pequenas","pequeno","pequenos","per","perante","p�de","podendo","poder","poderia","poderiam","podiam","pois","por�m","posso","pouca","poucas","pouco","poucos","primeiro","primeiros","pr�pria","pr�prias","pr�prio","pr�prios","quais","quanto","quantos","que","se","seja","sejam","sempre","sendo","ser�","ser�o","seus","si","sido","s�","sob","sobre","sua","suas","talvez","tampouco","te","tendo","tenha","ter","teus","ti","tido","tinha","tinham","toda","todas","todavia","todo","tua","tuas","tudo","�ltima","�ltimas","�ltimos","vendo","vez","vindo","vir","vos","v�s");


	public static String cleanupContent (String content){

		String contentCleaned = content;
		contentCleaned = contentCleaned.toLowerCase();
		contentCleaned = contentCleaned.replaceAll("<.*?>", "");
		contentCleaned = contentCleaned.replaceAll("�", "");
		contentCleaned = contentCleaned.replaceAll("\\p{Punct}","").replaceAll("\\s+"," ");
		
		return contentCleaned;
	}	

	public static int countWordsTotal (String content, String regex){

		int totalWords = 0;		

		for (String string : content.split(regex)) {
			//vendo se a palavra em quest�o N�O esta na lista de preposi��es e artigos
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
			
			//se a palavra estiver contida no array de artigos e preposi��es, ele pula para a pr�xima 
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
