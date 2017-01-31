import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Currency;
import java.util.Set;

public class test {
 public static void main(String[] args) throws IOException {
	
	 FileInputStream fis = new FileInputStream("");
	 BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	 Set<Currency> availableCurrencies = Currency.getAvailableCurrencies();
	 String line = br.readLine();
	 while (line != null){
		 for(Currency currency : availableCurrencies){
			 String countryName = line.split(",")[1];
			 
				 
			 
		 }
	
	 }
}
}
