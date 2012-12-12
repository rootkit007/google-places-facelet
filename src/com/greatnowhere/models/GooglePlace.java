package com.greatnowhere.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class GooglePlace implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6216689236814357307L;
	
	/**
	 * The collection of address components for this Place's location. See the Geocoding service's Address Component Types section for more details. 
	 */
	public Collection<AddressComponent> addressComponents; 
	/**
	 * The Place's full address.
	 */
	public String formattedAddress;

	/**
	 * The Place's full address. Includes SPAN elements denoting address components
	 */
	public String adrAddress;
	
	/**
	 * The Place's geometry-related information. 
	 */
	public Geometry geometry; 

	/**
	 * Attribution text to be displayed for this Place result.
	 */
	public String[] htmlAttributions; 
	
	/**
	 *  URL to an image resource that can be used to represent this Place's type.
	 */
	public String icon;
	
	/**
	 * contains a unique identifier denoting this place. This identifier may not be used to retrieve information about this place, but can be used to consolidate data about this Place, and to verify the identity of a Place across separate searches. As ids can occasionally change, it's recommended that the stored id for a Place be compared with the id returned in later Details requests for the same Place, and updated if necessary.
	 */
	public String id;
	
	/**
	 *  contains the Place's phone number in international format. International format includes the country code, and is prefixed with the plus (+) sign. For example, the international_phone_number for Google's Sydney, Australia office is +61 2 9374 4000.
	 */
	public String internationalPhoneNumber;
	
	/**
	 * The Place's name.
	 */
	public String name;
	
	/**
	 * contains a token that can be used to query the Details service in future. This token may differ from the reference used in the request to the Details service. It is recommended that stored references for Places be regularly updated. Although this token uniquely identifies the Place, the converse is not true: a Place may have many valid reference tokens.
	 */
	public String reference;
	
	/**
	 * URL of the associated Google Place Page.
	 */
	public String url; 

	/**
	 *  A simplified address for the Place, including the street name, street number, and locality, but not the province/state, postal code, or country. For example, Google's Sydney, Australia office has a vicinity value of 5/48 Pirrama Road, Pyrmont. The vicinity property is only returned for a Nearby Search.
	 */
	public String vicinity;
	
	/**
	 * An array of types for this Place (e.g., ["political", "locality"] or ["restaurant", "establishment"]).
	 */
	public String[] types;
	
	/**
	 * contains the number of minutes this Place’s current timezone is offset from UTC. For example, for Places in Sydney, Australia during daylight saving time this would be 660 (+11 hours from UTC), and for Places in California outside of daylight saving time this would be -480 (-8 hours from UTC).
	 */
	public Integer utc_offset;
	
	
	// TODO: add all this opening hours and ratings stuff
	/*
	opening_hours may contain the following information:
	open_now is a boolean value indicating if the Place is open at the current time.
	periods[] is an array of opening periods covering seven days, starting from Sunday, in chronological order. Each period may contain:
	open contains a pair of day and time objects describing when the Place opens:
	day a number from 0–6, corresponding to the days of the week, starting on Sunday. For example, 2 means Tuesday.
	time may contain a time of day in 24-hour hhmm format (values are in the range 0000–2359). The time will be reported in the Place’s timezone.
	close contains a pair of day and time objects describing when the Place closes.
	rating: The Place's rating, from 0.0 to 5.0, based on user reviews. For more granular ratings, inspect the contents of the aspects[] array.
	reviews an array of up to five reviews. Each review consists of several components:
	aspects[] contains an array of PlaceAspectRating objects, each of which provides a rating of a single attribute of the establishment. The first object in the array is considered the primary aspect. Each PlaceAspectRating is defined as:
	type the name of the aspect that is being rated. eg. atmosphere, service, food, overall, etc.
	rating the user's rating for this particular aspect, from 0 to 3.
	author_name the name of the user who submitted the review. Anonymous reviews are attributed to "A Google user". If a language parameter was set, then the phrase "A Google user" will return a localized string.
	author_url the URL to the users Google+ profile, if available.
	text contains the user's review. When reviewing a location with Google Places, text reviews are considered optional; therefore, this field may by empty.
	*/
	
	/**
	 * lists the authoritative website for this Place, such as a business' homepage.
	 */
	public String website; 
	
	public class AddressComponent implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 4770170332312605577L;
		
		public String longName;
		public String shortName;
		public Collection<AddressComponentType> types;
	}
	
	public enum AddressComponentType {
		street_address, // indicates a precise street address.
		route, // indicates a named route (such as "US 101").
		intersection, // indicates a major intersection, usually of two major roads.
		political, // indicates a political entity. Usually, this type indicates a polygon of some civil administration.
		country, // indicates the national political entity, and is typically the highest order type returned by the Geocoder.
		administrative_area_level_1, // indicates a first-order civil entity below the country level. Within the United States, these administrative levels are states. Not all nations exhibit these administrative levels.
		administrative_area_level_2, // indicates a second-order civil entity below the country level. Within the United States, these administrative levels are counties. Not all nations exhibit these administrative levels.
		administrative_area_level_3, // indicates a third-order civil entity below the country level. This type indicates a minor civil division. Not all nations exhibit these administrative levels.
		colloquial_area, // indicates a commonly-used alternative name for the entity.
		locality, // indicates an incorporated city or town political entity.
		sublocality, // indicates an first-order civil entity below a locality
		neighborhood, // indicates a named neighborhood
		premise, // indicates a named location, usually a building or collection of buildings with a common name
		subpremise, // indicates a first-order entity below a named location, usually a singular building within a collection of buildings with a common name
		postal_code, // indicates a postal code as used to address postal mail within the country.
		natural_feature, // indicates a prominent natural feature.
		airport, // an airport.
		park, // indicates a named park.
		point_of_interest, // orly?
		post_box, // indicates a specific postal box.
		street_number, // indicates the precise street number.
		floor, // indicates the floor of a building address.
		room; // indicates the room of a building address.
	}
	
	public class LatLng implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2316978160798888654L;
		
		@SerializedName("$a")
		public BigDecimal latitude;
		@SerializedName("ab")
		public BigDecimal longitude;
	}
	
	public class Geometry implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 8963192970199737864L;
		
		/**
		 * provides the latitude and longitude of the Place.
		 */
		public LatLng location;
		/**
		 * defines the preferred viewport on the map when viewing this Place.
		 */
		public LatLng viewport;
	}
	
	/**
	 * Parses JSON into GooglePlace
	 * @param _json
	 * @return
	 */
	public static GooglePlace fromJSON(String _json) {
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
		return gson.fromJson(_json, GooglePlace.class);
	}
	
	/**
	 * 
	 * @param place
	 * @return
	 */
	public String toJSON() {
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
		return gson.toJson(this);
	}

}
