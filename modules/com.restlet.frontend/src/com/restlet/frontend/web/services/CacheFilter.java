package com.restlet.frontend.web.services;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.CacheDirective;
import org.restlet.data.Status;
import org.restlet.routing.Filter;

/**
 * Filter that simply add cache information based on expiration date, or
 * explicitely add nocache directive.
 * 
 * @author Thierry Boileau
 */

public class CacheFilter extends Filter {
    /**
     * Constructor.
     * 
     * @param context
     *            Context.
     * @param next
     *            The next Restlet to transmit the request.
     */
    public CacheFilter(Context context, Restlet next) {
        super(context, next);
    }

    @Override
    protected void afterHandle(Request request, Response response) {
        super.afterHandle(request, response);
        if (Status.SUCCESS_OK.equals(response.getStatus())
                && response.getEntity() != null) {
            if (request.getResourceRef().toString(false, false)
                    .contains("nocache")) {
                response.getEntity().setModificationDate(null);
                response.getEntity().setExpirationDate(null);
                response.getEntity().setTag(null);
                response.getCacheDirectives().add(CacheDirective.noCache());
            } else {
                // One day.
                Calendar c = new GregorianCalendar();
                c.setTime(new Date());
                c.add(Calendar.DAY_OF_MONTH, 1);
                response.getEntity().setExpirationDate(c.getTime());
                response.getEntity().setModificationDate(null);
            }
        }
    }
}
