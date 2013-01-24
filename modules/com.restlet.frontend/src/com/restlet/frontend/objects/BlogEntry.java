package com.restlet.frontend.objects;

import java.io.Serializable;

public class BlogEntry implements Serializable {

    /** */
    private static final long serialVersionUID = 1L;

    /** Publication date. */
    private String published;

    /** Title. */
    private String title;

    /** URI of the blog entry. */
    private String uri;

    public String getPublished() {
        return published;
    }

    public String getTitle() {
        return title;
    }

    public String getUri() {
        return uri;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
