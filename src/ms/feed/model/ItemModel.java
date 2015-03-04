package ms.feed.model;

import java.util.SortedMap;

public class ItemModel {

	String title;
	String pubDate;
	String content;

	Integer totalWords;
	SortedMap<Integer, String> rankingWords;


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}	

	public Integer getTotalWords() {
		return totalWords;
	}

	public void setTotalWords(Integer totalWords) {
		this.totalWords = totalWords;
	}

	public SortedMap<Integer, String> getRankingWord() {
		return rankingWords;		  
	}

	public void setRankingWords(SortedMap<Integer, String> rankingWords){
		this.rankingWords = rankingWords;
	}

	@Override
	public String toString() {
		return "Title : " + title + "\r\n "
				+ "Content : " + content + "\r\n "
				+ "10 Mosted used words : " + rankingWords.toString();
	}





}


