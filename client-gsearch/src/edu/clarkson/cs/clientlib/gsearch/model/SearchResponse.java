package edu.clarkson.cs.clientlib.gsearch.model;

import java.util.List;

public class SearchResponse {

	private List<SearchResult> results;

	private SearchCursor cursor;

	public List<SearchResult> getResults() {
		return results;
	}

	public void setResults(List<SearchResult> results) {
		this.results = results;
	}

	public SearchCursor getCursor() {
		return cursor;
	}

	public void setCursor(SearchCursor cursor) {
		this.cursor = cursor;
	}

}
