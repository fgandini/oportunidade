package ms.feed.reader;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import ms.feed.model.ItemModel;


public class Parser {

	final String TITLE = "title";
	final String ITEM = "item";
	final String CONTENT = "encoded";
	final String PUB_DATE = "pubDate";

	final URL url;

	public Parser(String feedUrl) {
		try {
			this.url = new URL(feedUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<ItemModel> readFeed() {

		List<ItemModel> itensList = new ArrayList<ItemModel>();

		try {	

			String title = "";	
			String pubDate = "";
			String content = "";

			XMLInputFactory xmlif = XMLInputFactory.newInstance();	
			InputStream in = url.openStream();			
			XMLEventReader eventReader = xmlif.createXMLEventReader(in);

			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) {
					String text = event.asStartElement().getName().getLocalPart();
					switch (text) {				
					case TITLE:
						title = getTextData(event, eventReader);
						break;
					case PUB_DATE:
						pubDate = getTextData(event, eventReader);
						break;
					case CONTENT:
						content = getTextData(event, eventReader);
						break;
					}
				} else if (event.isEndElement()) {
					if (event.asEndElement().getName().toString() == (ITEM)) {
						ItemModel item = new ItemModel();
						item.setTitle(title);
						item.setPubDate(pubDate);
						item.setContent(content);
						itensList.add(item);
						event = eventReader.nextEvent();
						continue;
					}
				}
			}
		} catch (XMLStreamException | IOException e) {
			throw new RuntimeException(e);
		} 

		return itensList;
	}

	private String getTextData(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException  {
		String result = "";
		event = eventReader.nextEvent();		
		if (event.isCharacters()) {
			result = event.toString();
		}
		return result;
	}

} 
