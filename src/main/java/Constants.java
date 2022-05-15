import java.util.HashMap;

public final class Constants {

	public static final HashMap<String,Integer> totalWeightMap = new HashMap();
	public static final HashMap<String,String> cropCodeConversion = new HashMap();
	static{
		cropCodeConversion.put("W", "Wheat");
		cropCodeConversion.put("B", "Barley");
		cropCodeConversion.put("M", "Maize");
		cropCodeConversion.put("BE", "Beetroot");
		cropCodeConversion.put("C", "Carrot");
		cropCodeConversion.put("PO", "Potatoes");
		cropCodeConversion.put("PA", "Parsnips"); 
		cropCodeConversion.put("O", "Oats");
	}
}
