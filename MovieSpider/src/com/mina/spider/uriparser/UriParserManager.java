package com.mina.spider.uriparser;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;

public class UriParserManager {

	private UriParserManager() {
	}

	private static class InstanceHolder {
		private static UriParserManager instance = new UriParserManager();
	}

	public static UriParserManager get() {
		return InstanceHolder.instance;
	}

	public synchronized AbstractParser.Chain getParserChain(Document document) {

		List<AbstractParser> parsers = new ArrayList<>();

		Ed2kParser ed2kParser = new Ed2kParser();
		MagnetParser magnetParser = new MagnetParser();
		ThunderParser thunderParser = new ThunderParser();
		MovieInfoParser movieInfoParser = new MovieInfoParser();
		BaseParser baseParser = new BaseParser();

		parsers.add(ed2kParser);
		parsers.add(magnetParser);
		parsers.add(thunderParser);
		parsers.add(movieInfoParser);
		parsers.add(baseParser);

		AbstractParser.Chain chain = new RealChain(parsers, document, 0);

		return chain;

	}

}
