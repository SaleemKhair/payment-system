package com.progressoft.jip.utilities.restful;

@FunctionalInterface
public interface RestfulDataParser<G> {
	G parse(String data);
}
