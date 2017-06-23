package test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

public class Test {
	// private String input =
	// "[{"alias":"cajun","title":"Cajun/Creole"},{"alias":"seafood","title":"Seafood"},{"alias":"sandwiches","title":"Sandwiches"}]";
	// [{\"alias\":\"cajun\",\"title\":\"Cajun or Creole\"},
	// {\"alias\":\"seafood\",\"title\":\"Seafood\"},
	// {\"alias\":\"sandwiches\",\"title\":\"Sandwiches\"}]
	private static String input = "{\"alias\":\"cajun\",\"title\":\"Cajun or Creole\"}";

	public static void main(String[] args) throws JSONException {
		// TODO Auto-generated method stub
		// JsonArray jsonArray = input.getAsJsonArray("key");

		Gson gson = new Gson();
		// String[] output = gson.fromJson(jsonArray , String[].class);
		JSONArray jArray = new JSONArray();
		JSONObject json1 = new JSONObject();
		json1.put("alias", "cajun");
		json1.put("title", "Cajun or Creole");
		JSONObject json2 = new JSONObject();
		json2.put("alias", "seafood");
		json2.put("title", "Seafood");
		jArray.put(json1);
		jArray.put(json2);

		// JSONObject json = new
		// JSONObject("{\"alias\":\"cajun\",\"title\":\"Cajun or Creole\"}");
		String jstr = jArray.toString();
		// System.out.println(parseString(input));
		System.out.println("jstr: " + jstr);
		String pStr = parseString(jstr);
		System.out.println("pstr: " + pStr);
		JSONArray jArray2 = new JSONArray(jstr);
		//
		System.out.println(pStr.equals(
				"[{\"alias\":\"cajun\",\"title\":\"Cajun or Creole\"},{\"alias\":\"seafood\",\"title\":\"Seafood\"}]"));
		// System.out.println("astr: "
		// + "[{\"alias\":\"cajun\",\"title\":\"Cajun or
		// Creole\"},{\"alias\":\"seafood\",\"title\":\"Seafood\"}]");
		JSONArray jArray1 = new JSONArray(
				"[{\"alias\":\"cajun\",\"title\":\"Cajun or Creole\"},{\"alias\":\"seafood\",\"title\":\"Seafood\"}]");

		System.out.println();

	}

	public static String jsonArrayToString(JSONArray array) {
		StringBuilder sb = new StringBuilder();
		try {
			for (int i = 0; i < array.length(); i++) {
				String obj = (String) array.get(i);
				sb.append(obj);
				if (i != array.length() - 1) {
					sb.append(",");
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static JSONArray stringToJSONArray(String str) {
		try {
			String pStr = parseString(str);
			return new JSONArray(pStr);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String parseString(String str) {
		return str.replace("\"", "\\\"").replace("/", " or ");
	}

}
