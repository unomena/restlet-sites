package com.restlet.frontend.web.services;

import java.util.Map;
import java.util.TreeMap;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.service.StatusService;

import com.restlet.frontend.web.applications.RefreshApplication;

/**
 * Status service that serves the representation of error status based on
 * freemarker templates.
 * 
 */
public class RefreshStatusService extends StatusService {
    /** The application using this status service. */
    private final RefreshApplication application;

    /**
     * Constructor.
     * 
     * @param enabled
     *            Indicates of the status service is enabled or not.
     * @param application
     *            the application using this status service.
     */
    public RefreshStatusService(boolean enabled, RefreshApplication application) {
        super(enabled);
        this.application = application;
    }

    @Override
    public Representation getRepresentation(Status status, Request request,
            Response response) {
        Map<String, Object> dataModel = new TreeMap<String, Object>();
        dataModel.put("status", status);
        dataModel.put("contactEmail", this.getContactEmail());
        dataModel.put("homeUri", "/");

        if (status.isError()) {
            dataModel.put("exception", status.getThrowable());
        }

        try {
            ClientResource cr = new ClientResource(
                    "riap://application/tmpl/error/");
            if (status.isInformational()) {
                cr.getReference().addSegment("error-100");
            } else if (status.isServerError()) {
                cr.getReference().addSegment("error-500");
            } else {
                cr.getReference().addSegment("error-400");
            }

            cr.getClientInfo().setAcceptedLanguages(
                    request.getClientInfo().getAcceptedLanguages());
            cr.accept(MediaType.TEXT_HTML);
            cr.head();
            return new TemplateRepresentation("tmpl/error/"
                    + cr.getResponse().getEntity().getLocationRef()
                            .getLastSegment(), application.getFmc(), dataModel,
                    MediaType.TEXT_HTML);
        } catch (Exception e) {
        }

        return super.getRepresentation(status, request, response);
    }
}
