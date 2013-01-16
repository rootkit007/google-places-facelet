package com.greatnowhere.jsf.components;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtext.InputTextRenderer;

import com.greatnowhere.models.GooglePlaceEx;

@FacesRenderer(componentFamily=GooglePlaceComponent.COMPONENT_FAMILY,rendererType="com.greatnowhere.jsf.components.GooglePlaceComponentRenderer")
public class GooglePlaceComponentRenderer extends InputTextRenderer {

	/**
	 * Returns ID to be assigned to the hidden input element that holds PlacesResult
	 * @param context
	 * @param component
	 * @return
	 */

	@Override
	public void decode(FacesContext context, UIComponent component) {
    	super.decode(context, component);

    	GooglePlaceComponent _component = (GooglePlaceComponent) component;    	
    	
        if(_component.isDisabled() || _component.isReadonly()) {
            return;
        }

		String placeResultId = _component.getPlaceResultElementId(context);
		String submittedValue = (String) context.getExternalContext().getRequestParameterMap().get(placeResultId);

        if(submittedValue != null) {
            _component.setPlace(GooglePlaceEx.fromJSON(submittedValue));
        }
	}

	
	@Override
	protected void encodeScript(FacesContext context, InputText component) throws IOException {
		super.encodeScript(context, component);
		
		GooglePlaceComponent _component = (GooglePlaceComponent) component;
		ResponseWriter writer = context.getResponseWriter();
		String clientId = component.getClientId(context);
		String placeResultId = _component.getPlaceResultElementId(context);

        startScript(writer, clientId);
        
        // hook up textinput with Google Places Autocomplete
        writer.write(
        		// specify types to appear in results
        		"jQuery(function(){\n" +
        			// TODO: make types configurable via attribute
        			"var options={types:['geocode','establishment'] };" + // allowed types are geocode and establishment
        			"var autocomplete=new google.maps.places.Autocomplete(" + _component.resolveWidgetVar() + ".getJQ().get(0),options);\n" +
        			"google.maps.event.addListener(autocomplete, 'place_changed', function() {\n" +
	        			"var place=autocomplete.getPlace();\n" + // handler for user selection
	        			"if (!!place) {\n" + // if place selected
	        				"jQuery(PrimeFaces.escapeClientId('" + placeResultId + "')).val(JSON.stringify(place));\n" +
    					"}\n" +
        				_component.resolveWidgetVar() + ".getJQ().trigger('place_changed');\n" + // Google events do not trigger DOM events so have to do it manually 
	        		"});" +
	        		"if (navigator.geolocation) {\n" + // allow for automatic search results bias towards user location
	        			"navigator.geolocation.getCurrentPosition(function(position) {\n" +
	        				"var geolocation=new google.maps.LatLng(position.coords.latitude,position.coords.longitude); \n" +
	        				"autocomplete.setBounds(new google.maps.LatLngBounds(geolocation, geolocation));\n" +
	        			"});\n" +
	        		"}\n" +
        		"});\n"
        );

		endScript(writer);
	}

	@Override
	protected void encodeMarkup(FacesContext context, InputText component) throws IOException {
		super.encodeMarkup(context, component);

		GooglePlaceComponent _component = (GooglePlaceComponent) component;
		ResponseWriter writer = context.getResponseWriter();
		String placesResultId = _component.getPlaceResultElementId(context);
		
		if ( _component.getLoadAPI() ) {
			writer.startElement("script", null);
			String _apiURL= "https://maps.googleapis.com/maps/api/js?libraries=places&sensor=true";
			if ( _component.getGoogleAPIKey() != null && _component.getGoogleAPIKey().length() > 0 ) {
				_apiURL += "&key=" + _component.getGoogleAPIKey();
			}
			// FIXME: include script in head only once per view. this should be ResourceDependency but they dont allow external resources
			writer.writeAttribute("src", _apiURL, null);
			writer.endElement("script");
		}

		writer.startElement("input", null);
		writer.writeAttribute("id", placesResultId, null);
		writer.writeAttribute("name", placesResultId, null);
		writer.writeAttribute("type", "hidden", null);

		GooglePlaceEx place = _component.getPlace();
		if(place != null) {
			String valueToRender = place.toJSON();
			writer.writeAttribute("value", valueToRender , null);
		}

        writer.endElement("input");
	}

}
