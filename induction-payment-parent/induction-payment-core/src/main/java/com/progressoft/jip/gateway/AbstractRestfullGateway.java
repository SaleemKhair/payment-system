package com.progressoft.jip.gateway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.progressoft.jip.utilities.restful.RestfulDataParser;

public abstract class AbstractRestfullGateway<T>  implements CurrencyExchangeRateGateway{

	private RestfulDataParser<T> parser;

	public AbstractRestfullGateway(RestfulDataParser<T> parser) {
		this.parser = parser;
	}

	protected T response(String url) {
		try {
			URL urlObject = new URL(url);
			String line;
			StringBuilder data = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlObject.openStream()));
			while ((line = reader.readLine()) != null)
				data.append(line);
			return parser.parse(data.toString());
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

}
