package net.sf.jabref.logic.fetcher;

import net.sf.jabref.model.entry.BibEntry;
import org.junit.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class DoiResolutionTest {
    DoiResolution finder;
    BibEntry entry;

    @Before
    public void setup() {
        finder = new DoiResolution();
        entry = new BibEntry();
    }

    @Test(expected = NullPointerException.class)
    public void rejectNullParameter() throws IOException {
        finder.findFullText(null);
    }

    @Test
    public void doiNotPresent() throws IOException {
        Assert.assertEquals(Optional.empty(), finder.findFullText(entry));
    }

    @Test
    public void findByDOI() throws IOException {
        entry.setField("doi", "10.1051/0004-6361/201527330");

        Assert.assertEquals(
                Optional.of(new URL("http://www.aanda.org/articles/aa/pdf/2016/01/aa27330-15.pdf")),
                finder.findFullText(entry)
        );
    }

    @Ignore
    @Test
    public void notReturnAnythingWhenMultipleLinksAreFound() throws IOException {
    }

    @Test
    public void notFoundByDOI() throws IOException {
        entry.setField("doi", "10.1186/unknown-doi");

        Assert.assertEquals(Optional.empty(), finder.findFullText(entry));
    }
}