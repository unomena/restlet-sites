package com.restlet.frontend.web.resources;

import java.util.HashMap;
import java.util.Map;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;

import com.restlet.frontend.objects.framework.Distribution;
import com.restlet.frontend.objects.framework.Edition;
import com.restlet.frontend.objects.framework.Version;
import com.restlet.frontend.web.applications.RestletCom;

public class DownloadPastServerResource extends BaseResource {

    /** The current distribution */
    private Distribution distribution;

    /** The current edition */
    private Edition edition;

    /** The current version. */
    private Version version;

    @Override
    protected void doInit() throws ResourceException {
        super.doInit();

        // Set the context
        version = getApplication().getVersion(getQuery(), getCookies());
        edition = getApplication()
                .getEdition(getQuery(), getCookies(), version);
        distribution = getApplication().getDistribution(getQuery(),
                getCookies(), version, edition);

        getApplication().updateCookies(version, edition, distribution,
                getResponse());
    }

    @Override
    public RestletCom getApplication() {
        return (RestletCom) super.getApplication();
    }

    @Get
    public Representation toHtml() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("version", version.getFullVersionCompact());
        model.put("versionMaven", version.getMavenVersion());
        model.put("branch", version.getMinorVersion());
        model.put("release", version.getQualifier());

        model.put("distribution", distribution.getFileType());
        model.put("edition", edition.getId());

        if ("zip".equals(getQueryValue("distribution"))) {
            model.put("showNewsletter", Boolean.TRUE);
            model.put("showArchive", Boolean.TRUE);
            model.put("downloadurl", "/download/" + version.getMinorVersion()
                    + "/" + distribution.getFileName());
        } else if ("exe".equals(getQueryValue("distribution"))) {
            model.put("showNewsletter", Boolean.TRUE);
            model.put("showArchive", Boolean.TRUE);
            model.put("downloadurl", "/download/" + version.getMinorVersion()
                    + "/" + distribution.getFileName());
        } else if ("maven".equals(getQueryValue("distribution"))) {
            model.put("showNewsletter", Boolean.TRUE);
            model.put("showMaven", Boolean.TRUE);
        } else if ("p2".equals(getQueryValue("distribution"))) {
            model.put("showNewsletter", Boolean.TRUE);
            model.put("showEclipse", Boolean.TRUE);
        }

        return getHTMLTemplateRepresentation("download/past.html", model);
    }
}
