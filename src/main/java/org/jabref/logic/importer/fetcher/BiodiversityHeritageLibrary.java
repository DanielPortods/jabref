package org.jabref.logic.importer.fetcher;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;

import org.jabref.logic.importer.FetcherException;
import org.jabref.logic.importer.Parser;
import org.jabref.logic.importer.SearchBasedParserFetcher;
import org.jabref.logic.importer.fetcher.transformers.DefaultQueryTransformer;
import org.jabref.logic.importer.util.JsonReader;
import org.jabref.logic.util.BuildInfo;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.http.client.utils.URIBuilder;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;

public class BiodiversityHeritageLibrary implements SearchBasedParserFetcher {

    private static final String API_URL = "https://www.biodiversitylibrary.org/api3?op=PublicationSearch";
    private static final String API_KEY = new BuildInfo().BiodiversityHeritageLibraryAPIkey;

    @Override
    public String getName() {
        return "Biodiversity Heritage Library";
    }

    @Override
    public Parser getParser() {
        return inputStream -> {
            JSONObject response = JsonReader.toJsonObject(inputStream);
            if (response.isEmpty()) {
                return Collections.emptyList();
            }

            JSONArray results = response.getJSONArray("Result");
            if (results.isEmpty()) {
                return Collections.emptyList();
            }

            return null;
        };
    }

    @Override
    public URL getURLForQuery(QueryNode luceneQuery) throws URISyntaxException, MalformedURLException, FetcherException {
        URIBuilder uriBuilder = new URIBuilder(API_URL);
        uriBuilder.addParameter("searchterm", new DefaultQueryTransformer().transformLuceneQuery(luceneQuery).orElse(""));
        uriBuilder.addParameter("searchtype", "C");
        uriBuilder.addParameter("page", "1");
        uriBuilder.addParameter("apikey", API_KEY);
        uriBuilder.addParameter("format", "json");
        return uriBuilder.build().toURL();
    }
}