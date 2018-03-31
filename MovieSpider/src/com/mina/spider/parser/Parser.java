package com.mina.spider.parser;

public interface Parser<T> {
	
	T parse(String html);

}
