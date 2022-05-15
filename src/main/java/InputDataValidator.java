import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class InputDataValidator {
	private final List<String> errors = new ArrayList();
	
	
	public Collection<String> getErrors(){
		return errors;
	}

	public boolean lineValidator(String aLine) {
		boolean isValid = true;
		String[] row = null;
		row = aLine.split(",");
		for(int i=0; i<row.length;i++){
			if(row[i].isEmpty()){
				errors.add("Empty value present in the Input File");
				isValid = false;
			}
			if(row[0].length()<=2){
				errors.add("County Name entered is Invalid");
				isValid = false;
				break;
			}
			String rowValue = row[i].trim();
			if(i!=0 && i%2!=0){
				if(!Constants.cropCodeConversion.containsKey(rowValue)){
					errors.add("Crop Code entered for county " +row[0]+ " is invalid");
					isValid = false;
				}
				if(StringUtils.isNumeric(rowValue)){
					errors.add("Crop Code entered for county " +row[0]+ " must be in characters");
					isValid = false;
				}
			}
			else if(i!=0 && i%2==0 && !StringUtils.isNumeric(rowValue)){
				errors.add("Weight entered for county "+row[0]+" has a non Numeric value");
				isValid = false;
			}
		}
		return isValid;
	}
	
}
