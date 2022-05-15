import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Main {
	private static HashMap<String,HashMap> harvestData= new HashMap();
	private static HashMap<String,String> weightHarvested= null;
	private static String line = "";
	private static BufferedReader reader = null;
	private static InputDataValidator validator = new InputDataValidator();
	
	
	public static void main(String[] args){
			String fileOveride = "src\\main\\resources\\override.csv";
			String fileOriginal = "src\\main\\resources\\harvest data - clean.csv";
			String fileValidation = "src\\main\\resources\\harvest data - validation needed.csv";
			
			try{
			mapDataFromFile(fileOveride);
			mapDataFromFile(fileOriginal);
//			mapDataFromFile(fileValidation);
			updateWeightAsPercentage(harvestData);
			printOutput();
 			}catch(Exception e){
				e.printStackTrace();
			}
			
		
	}

	private static void printOutput() {
		printErrorMessages(validator);
		System.out.println(harvestData.toString());
		
	}

	private static void mapDataFromFile(String aFilePath) {
		try {
			reader = new BufferedReader(new FileReader(aFilePath));
			Integer totalWeight = null;
			try {
				while((line = reader.readLine())!=null){
					boolean isValid = validator.lineValidator(line);
					if(isValid){
					String[] row = line.split(",");
					if(harvestData.containsKey(row[0])){
						weightHarvested = harvestData.get(row[0]);
					}
					else{
						weightHarvested= new HashMap();
					}
					for(int i=1;i<row.length-1;i++){
						String cropCode = Constants.cropCodeConversion.get(row[i].trim());
						String weight =  row[i+1].trim();
 						if(!weightHarvested.containsKey(cropCode)){
 							totalWeight = calculateTotalWeight(row[0], weight);
							weightHarvested.put(cropCode, weight);
						}
						i++;
					}
						harvestData.put(row[0], weightHarvested);
				}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}

	private static void updateWeightAsPercentage(HashMap<String, HashMap> aHarvestData) {
		Iterator itr = aHarvestData.entrySet().iterator();
		while(itr.hasNext()){
			Map.Entry mapElement1= (Entry) itr.next();
			HashMap<String, String> weightHarvested = (HashMap) mapElement1.getValue();
			Iterator weightHarvestedIterator = weightHarvested.entrySet().iterator();
			while(weightHarvestedIterator.hasNext()){
				Map.Entry mapElement2= (Entry) weightHarvestedIterator.next();
				String weight = (String) mapElement2.getValue();
				Integer weightAsInt = Integer.parseInt(weight);
				Integer totalWeight = Constants.totalWeightMap.get((String)mapElement1.getKey());
				double percentageOfTotalWeight = Math.round(((double)weightAsInt/(double)totalWeight)*100);
				mapElement2.setValue((percentageOfTotalWeight));
			}
		}
		
	}

	private static Integer calculateTotalWeight(String county,String weight) {
		Integer harvestedWeight = Integer.parseInt(weight);
		Integer total = 0;
		if(Constants.totalWeightMap.containsKey(county)){
			total = Constants.totalWeightMap.get(county);
			total += harvestedWeight;
			Constants.totalWeightMap.put(county, total);		
			}
		else{
			Constants.totalWeightMap.put(county, harvestedWeight);	
			total = harvestedWeight;
		}
		return total;
	}
	
	private static void printErrorMessages(InputDataValidator aValidator) {
		List<String> errors = (List<String>) aValidator.getErrors();
		for(String e:errors){
			System.out.println(e);
		}
		
	}
	
}
