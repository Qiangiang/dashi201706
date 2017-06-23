package yelp;

import org.json.JSONArray;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.oauth.OAuthService;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class YelpAPI {
	private static final String API_HOST = "api.yelp.com";
	private static final String DEFAULT_TERM = "dinner";
	private static final int SEARCH_LIMIT = 20;
	private static final String SEARCH_PATH = "/v3/businesses/search";
	private static final String CONSUMER_KEY = "KKw660w4OUhkbzYSDN5z3Q";
	private static final String CONSUMER_SECRET = "DbXUtzgbIJgLsVZahdrl9ap5VivlxxsV1Q3yzw764ofu5kRcTbvIseR4X8ppwKUc";
	private static final String TOKEN = "4yiIeb3tUG0wQzPy8Wsg8YA34ZeIsUhvzwZmPFI4M9uSNzCOS89TvDPrfHLxaZZOmroO5TxLddjc9Xdo1JveDlu3494G0QW_rSfhoj6Eb1-mzja4f2ZsRWxZlCguWXYx";
	// private static final String TOKEN_SECRET = "";
	OAuthService service;
	// Token accessToken;

	/**
	 * Setup the Yelp API OAuth credentials.
	 * 
	 * public YelpAPI() { this.service = new
	 * ServiceBuilder().provider(TwoStepOAuth.class).apiKey(CONSUMER_KEY).apiSecret(CONSUMER_SECRET)
	 * .build(); this.accessToken = new Token(TOKEN, TOKEN_SECRET); }
	 */
	public YelpAPI() {
		this.service = new ServiceBuilder().provider(TwoStepOAuth.class).apiKey(CONSUMER_KEY).apiSecret(CONSUMER_SECRET)
				.build();
		// this.accessToken = new Token(TOKEN, TOKEN_SECRET);
	}

	/**
	 * Creates and sends a request to the Search API by term and location.
	 * 
	 * public String searchForBusinessesByLocation(double lat, double lon) {
	 * OAuthRequest request = new OAuthRequest(Verb.GET, "http://" + API_HOST +
	 * SEARCH_PATH); request.addQuerystringParameter("term", DEFAULT_TERM);
	 * request.addQuerystringParameter("ll", lat + "," + lon);
	 * request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));
	 * System.out.println(request); return sendRequestAndGetResponse(request); }
	 */
	public String searchForBusinessesByLocation(double lat, double lon) {
		Client client = Client.create();
		WebResource webResource = client.resource("https://" + API_HOST + SEARCH_PATH).queryParam("term", DEFAULT_TERM)
				.queryParam("latitude", String.valueOf(lat)).queryParam("longitude", String.valueOf(lon))
				.queryParam("limit", String.valueOf(SEARCH_LIMIT));

		ClientResponse response = webResource.accept("application/json").header("Authorization", "Bearer " + TOKEN)
				.get(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP erroe code : " + response.getStatus());
		}
		String output = response.getEntity(String.class);
		return output;
	}

	/**
	 * Sends an {@link OAuthRequest} and returns the {@link Response} body.
	 * 
	 * private String sendRequestAndGetResponse(OAuthRequest request) {
	 * System.out.println("Querying " + request.getCompleteUrl() + " ...");
	 * this.service.signRequest(this.accessToken, request); Response response =
	 * request.send(); return response.getBody(); }
	 */

	/**
	 * Queries the Search API based on the command line arguments and takes the
	 * first result to query the Business API.
	 * 
	 * private static void queryAPI(YelpAPI yelpApi, double lat, double lon) {
	 * String searchResponseJSON = yelpApi.searchForBusinessesByLocation(lat,
	 * lon); JSONObject response = null; try { response = new
	 * JSONObject(searchResponseJSON); JSONArray businesses = (JSONArray)
	 * response.get("businesses"); for (int i = 0; i < businesses.length(); i++)
	 * { JSONObject business = (JSONObject) businesses.get(i);
	 * System.out.println(business); } } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */
	private static void queryAPI(YelpAPI yelpApi, double lat, double lon) {
		String searchResponseJSON = yelpApi.searchForBusinessesByLocation(lat, lon);
		JSONObject response = null;
		try {
			response = new JSONObject(searchResponseJSON);
			JSONArray businesses = (JSONArray) response.get("businesses");
			for (int i = 0; i < businesses.length(); i++) {
				JSONObject business = (JSONObject) businesses.get(i);
				System.out.println(business);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Main entry for sample Yelp API requests.
	 */
	public static void main(String[] args) {
		YelpAPI yelpApi = new YelpAPI();
		queryAPI(yelpApi, 37.38, 122.08);
		/*
		 * Client client = Client.create(); WebResource webResource =
		 * client.resource("https://" + API_HOST +
		 * SEARCH_PATH).queryParam("term", DEFAULT_TERM) .queryParam("latitude",
		 * "37.38").queryParam("longitude", "-122") .queryParam("limit",
		 * String.valueOf(SEARCH_LIMIT));
		 * 
		 * ClientResponse response =
		 * webResource.accept("application/json").header("Authorization",
		 * "Bearer " + TOKEN) .get(ClientResponse.class); if
		 * (response.getStatus() != 200) { throw new
		 * RuntimeException("Failed : HTTP erroe code : " +
		 * response.getStatus()); }
		 * 
		 * String output = response.getEntity(String.class); JSONObject res =
		 * null; try { res = new JSONObject(output); JSONArray businesses =
		 * (JSONArray) res.get("businesses"); for (int i = 0; i <
		 * businesses.length(); i++) { JSONObject business = (JSONObject)
		 * businesses.get(i); System.out.println(business); } } catch (Exception
		 * e) { e.printStackTrace(); }
		 */
	}

}