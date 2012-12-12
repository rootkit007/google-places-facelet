package com.greatnowhere.models;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Extends GooglePlace with utility functions to retrieve country/region/state names and codes
 * @author pzeltins
 *
 */
public class GooglePlaceEx extends GooglePlace {

	protected static final int NAME_TYPE_LONG = 0; 
	protected static final int NAME_TYPE_SHORT = 1; 
	
	/**
	 * Returns full country name 
	 */
	private static final long serialVersionUID = -1991759538177933133L;

	public String getCountry() {
		return searchAddressComponent(NAME_TYPE_LONG, AddressComponentType.country);
	}
	
	public String getCountryCode() {
		return searchAddressComponent(NAME_TYPE_SHORT, AddressComponentType.country);
	}

	/**
	 * Returns region or state
	 * @return
	 */
	public String getRegion() {
		return searchAddressComponent(NAME_TYPE_LONG, AddressComponentType.administrative_area_level_1);
	}
	
	public String getRegionCode() {
		return searchAddressComponent(NAME_TYPE_SHORT, AddressComponentType.administrative_area_level_1);
	}
	
	/**
	 * Returns city name
	 * @return
	 */
	public String getCity() {
		String c = searchAddressComponent(NAME_TYPE_LONG, AddressComponentType.administrative_area_level_3);
		if ( c==null || c.isEmpty()) {
			c = searchAddressComponent(NAME_TYPE_LONG, AddressComponentType.locality);
		}
		return c;
	}
	
	public String getPostalCode() {
		return searchAddressComponent(NAME_TYPE_LONG, AddressComponentType.postal_code);
	}

	/**
	 * Street address without city, state, country components
	 * @return
	 */
	public String getStreetAddress() {
		return searchAddressComponent(NAME_TYPE_LONG, AddressComponentType.street_number) + " " +
				searchAddressComponent(NAME_TYPE_LONG, AddressComponentType.route);
	}
	/**
	 * 
	 * @param nameType 0 = long name, 1 = short name
	 * @param componentType component type to search for
	 * @return first match found for specified component type
	 */
	protected String searchAddressComponent(int nameType, AddressComponentType componentType) {
		String retval = "";
		for ( AddressComponent c : addressComponents ) {
			for ( AddressComponentType t : c.types ) {
				if ( t.equals(componentType) ) {
					retval = ( nameType == 0 ? c.longName : c.shortName );
					break;
				}
			}
			if (retval.length()>0) break;
		}
		return retval;
	}
	
	/**
	 * Parses JSON into GooglePlace
	 * @param _json
	 * @return
	 */
	public static GooglePlaceEx fromJSON(String _json) {
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
		return gson.fromJson(_json, GooglePlaceEx.class);
	}

}
