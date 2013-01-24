package com.restlet.frontend.web.services;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.restlet.Application;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
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

    /** List of supported error variants. */
    private final List<Variant> errorVariants;

    /**
     * Constructor.
     * 
     * @param enabled
     *            Indicates of the status service is enabled or not.
     * @param application
     *            the application using this status service.
     */
    public RefreshStatusService(boolean enabled,
            RefreshApplication application, List<Variant> errorVariants) {
        super(enabled);
        this.application = application;
        this.errorVariants = errorVariants;
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

        // TODO a bit awkward.
        Application app = (Application) this.application;
        Variant variant = app.getConnegService().getPreferredVariant(
                errorVariants, request, app.getMetadataService());
        if (variant != null) {
            StringBuilder builder = new StringBuilder("error/error");
            if (status.isInformational()) {
                builder.append("-100");
            } else if (status.isClientError()) {
                builder.append("-400");
            } else if (status.isServerError()) {
                builder.append("-500");
            }

            if (!variant.getLanguages().isEmpty()) {
                builder.append(".").append(
                        variant.getLanguages().get(0).getName());
            }
            builder.append(".html");

            return new TemplateRepresentation(builder.toString(),
                    application.getFmc(), dataModel, MediaType.TEXT_HTML);
        }

        return super.getRepresentation(status, request, response);
    }
}
