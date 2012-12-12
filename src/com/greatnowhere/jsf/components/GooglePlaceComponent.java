package com.greatnowhere.jsf.components;

import java.util.ArrayList;
import java.util.Collection;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.UpdateModelException;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.faces.event.PhaseId;

import org.primefaces.component.inputtext.InputText;

import com.greatnowhere.models.GooglePlaceEx;
import com.sun.faces.util.MessageFactory;

/**
 * Renders autocomplete textinput that gets predictions from Google Places API
 * when user selects a place (address) the 'place' attribute will be set to GooglePlace object containing
 * all data returned by Google for this location
 * Fires "place_changed" event
 * 
 *   Usage example:
 *			<gn:GooglePlace id="address" place="#{customer.place}" value="#{customer.fullAddress}" style="width:100%;">
 *				<p:ajax event="place_changed" update="placeResult"></p:ajax>
 *			</gn:GooglePlace>
 *			<p:panelGrid id="placeResult" columns="6">
 *				<h:outputText value="#{customer.place.getStreetAddress()}" ></h:outputText>
 *				<h:outputText value="#{customer.place.getCity()}" ></h:outputText>
 *				<h:outputText value="#{customer.place.getRegion()}" ></h:outputText>
 *				<h:outputText value="#{customer.place.getCountry()}"></h:outputText>
 *				<h:outputText value="#{customer.place.getCountryCode()}"></h:outputText>
 *				<h:outputText value="#{customer.place.getPostalCode()}"></h:outputText>
 *			</p:panelGrid>
 *
 *   
 * @author pzeltins
 *
 */
@FacesComponent(value="GooglePlace")
public class GooglePlaceComponent extends InputText {

	public static final String COMPONENT_FAMILY = "com.greatnowhere.jsf.components";
	public static final String PLACE_CHANGED_EVENT = "place_changed";

	protected static final String GC_ID = "place";
    
    /**
     * <p>The standard component type for this component.</p>
     */
    public static final String COMPONENT_TYPE = GooglePlaceComponent.class.getName();

    public GooglePlaceComponent() {
    	super();
    	setRendererType(GooglePlaceComponentRenderer.class.getCanonicalName());
    }

    @Override
    public String getRendererType() {
        return GooglePlaceComponentRenderer.class.getCanonicalName();
    }

    public String getFamily() {
		return COMPONENT_FAMILY;
	}

    public Collection<String> getEventNames() {
    	Collection<String> retval = new ArrayList<String>(super.getEventNames());
    	retval.add(PLACE_CHANGED_EVENT);
        return retval;
    }
    
    protected enum PropertyKeys {

    	place;

    	/*
    	useClientLocation, // if true will bias search to addresses closest to user's location
    	*/ 
    	
    	
        String toString;
        PropertyKeys(String toString) { this.toString = toString; }
        PropertyKeys() { }
        public String toString() {
            return ((toString != null) ? toString : super.toString());
        }
        
    }

	/**
	 * @return the _placeResult
	 */
	public GooglePlaceEx getPlace() {
		return (GooglePlaceEx) getStateHelper().eval(PropertyKeys.place, null);
	}

	/**
	 * @param _placeResult the _placeResult to set
	 */
	public void setPlace(GooglePlaceEx _placeResult) {
		getStateHelper().put(PropertyKeys.place, _placeResult);
		handleAttribute(PropertyKeys.place.toString(), _placeResult);
	}

	public String getPlaceResultElementId(FacesContext context) {
		return this.getClientId(context) + UINamingContainer.getSeparatorChar(context) + GC_ID;
	}
	
	
    public void updateModel(FacesContext context) {

    	super.updateModel(context);
        if (!isValid()) {
            return;
        }
        
        ValueExpression ve = getValueExpression(PropertyKeys.place.toString());
        if (ve != null) {
            Throwable caught = null;
            FacesMessage message = null;
            try {
                ve.setValue(context.getELContext(), getPlace());
            } catch (ELException e) {
                caught = e;
                String messageStr = e.getMessage();
                Throwable result = e.getCause();
                while (null != result &&
                     result.getClass().isAssignableFrom(ELException.class)) {
                    messageStr = result.getMessage();
                    result = result.getCause();
                }
                if (null == messageStr) {
                    message =
                         MessageFactory.getMessage(UPDATE_MESSAGE_ID,
                              MessageFactory.getLabel(
                                   context, this));
                } else {
                    message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                               messageStr,
                                               messageStr);
                }
                setValid(false);
            } catch (Exception e) {
                caught = e;
                message =
                     MessageFactory.getMessage(context, UPDATE_MESSAGE_ID,
                          MessageFactory.getLabel(
                               context, this));
                setValid(false);
            }
            if (caught != null) {
                assert(message != null);
                UpdateModelException toQueue =
                      new UpdateModelException(message, caught);
                ExceptionQueuedEventContext eventContext =
                      new ExceptionQueuedEventContext(context,
                                                toQueue,
                                                this,
                                                PhaseId.UPDATE_MODEL_VALUES);
                context.getApplication().publishEvent(context,
                                                      ExceptionQueuedEvent.class,
                                                      eventContext);
                
            }
            
        }
    }
	
}
