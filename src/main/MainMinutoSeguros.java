package main;

import java.util.Map.Entry;

import ms.feed.model.ItemModel;
import ms.feed.reader.Parser;
import ms.feed.utils.Utils;

public class MainMinutoSeguros {

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		
		Parser parser = new Parser("http://www.minutoseguros.com.br/blog/feed/");
			
		for (ItemModel item : parser.readFeed()) {
			
			System.out.println("Titulo deste tópico: " + item.getTitle());
			
			String cleanedText = Utils.cleanupContent(item.getContent());			
			System.out.println("Total de palavras neste topico: " + Utils.countWordsTotal(cleanedText, " "));	
					
			System.out.println("Ranking\t | Total\t | Palavra");
			int count = 1;			
			for (Entry<String, Integer> entry: Utils.rankingWordsCount(cleanedText, " ")) {
	            System.out.printf("%d\t | %d\t | %s\n", count, entry.getValue(), entry.getKey());
	            count++;
	            if (count > 10) break;
	        }
			
		}

	}

}
