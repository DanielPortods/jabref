package org.jabref.logic.importer.fetcher;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.jabref.logic.importer.FetcherException;
import org.jabref.logic.util.BuildInfo;
import org.jabref.testutils.category.FetcherTest;

import org.apache.lucene.queryparser.flexible.core.QueryNodeParseException;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.parser.SyntaxParser;
import org.apache.lucene.queryparser.flexible.standard.parser.StandardSyntaxParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.jabref.logic.importer.fetcher.transformers.AbstractQueryTransformer.NO_EXPLICIT_FIELD;
import static org.junit.jupiter.api.Assertions.assertEquals;

@FetcherTest
public class BiodiversityHeritageLibraryTest {

    private BiodiversityHeritageLibrary fetcher;
    private String API_KEI;

    @BeforeEach
    public void setUp() throws Exception {
        fetcher = new BiodiversityHeritageLibrary();
        API_KEI = new BuildInfo().BiodiversityHeritageLibraryAPIkey;
    }

    @Test
    public void getNameTest() {
        assertEquals("Biodiversity Heritage Library", fetcher.getName());
    }

    @Test
    public void getURLForQueryTest() throws QueryNodeParseException, MalformedURLException, URISyntaxException, FetcherException {
        String url = "https://www.biodiversitylibrary.org/api3?op=PublicationSearch&searchterm=The+birds+of+Cocos+Island&searchtype=C&page=1&apikey=" + API_KEI + "&format=json";
        SyntaxParser parser = new StandardSyntaxParser();
        QueryNode queryNode = parser.parse("The birds of Cocos Island", NO_EXPLICIT_FIELD);

        assertEquals(url, fetcher.getURLForQuery(queryNode).toString());
    }

    @Test
    public void getURLForQueryTestWithSingleWord() throws QueryNodeParseException, MalformedURLException, URISyntaxException, FetcherException {
        String url = "https://www.biodiversitylibrary.org/api3?op=PublicationSearch&searchterm=Foo&searchtype=C&page=1&apikey=" + API_KEI + "&format=json";
        SyntaxParser parser = new StandardSyntaxParser();
        QueryNode queryNode = parser.parse("Foo", NO_EXPLICIT_FIELD);

        assertEquals(url, fetcher.getURLForQuery(queryNode).toString());
    }
}
