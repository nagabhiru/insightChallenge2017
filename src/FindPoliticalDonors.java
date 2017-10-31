import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
//import org.apache.commons.lang3;

public class FindPoliticalDonors {
	static Map<RecipientWithZip, Contribution> contributionsByZip = new HashMap<RecipientWithZip, Contribution>();
	static SortedMap<RecipientWithDate, Contribution> contributionsByDate = new TreeMap<RecipientWithDate, Contribution>(new RecipientWithDateComp());
	final static String DATE_FORMAT = "MMddyyyy";
	public static void main(String[] args) throws IOException, ParseException {
		int CMTE_ID = 0;
		int ZIP_CODE = 10;
		int TRANSACTION_DT = 13;
		int TRANSACTION_AMT = 14;
		int OTHER_ID = 15;
		
		BufferedWriter bw1 = new BufferedWriter(new FileWriter(args[1], false));
		BufferedWriter bw2 = new BufferedWriter(new FileWriter(args[2], false));
		
		BufferedReader br = new BufferedReader(new FileReader(args[0]));
		String line = null;
		while ((line = br.readLine()) != null) {
			String[] data = line.split("\\|");
//			System.out.println(Arrays.toString(data));
			if ((data[OTHER_ID] != null && !data[OTHER_ID].isEmpty()) || 
					data[CMTE_ID].isEmpty() || data[CMTE_ID]==null || 
					data[TRANSACTION_AMT].isEmpty() || data[TRANSACTION_AMT]==null) {
				continue;
			}
			
			if (data[ZIP_CODE].length()>=5) {
				int amount = Integer.parseInt(data[TRANSACTION_AMT]);
				String zipcode = data[ZIP_CODE].substring(0, 5);
				
				RecipientWithZip curRecipient = new RecipientWithZip(data[CMTE_ID], zipcode);
				Contribution curContribution = contributionsByZip.getOrDefault(curRecipient, new Contribution());
				List<Integer> listOfContributions = curContribution.getContributionList();
				
				listOfContributions.add(amount);
				Collections.sort(listOfContributions);
				
				double median;
				if (listOfContributions.size() % 2 == 0)
				    median = ((double) listOfContributions.get(listOfContributions.size()/2) 
				    		+ (double) listOfContributions.get(listOfContributions.size()/2-1))/2;
				else
				    median = (double) listOfContributions.get(listOfContributions.size()/2);
				median = Math.floor(median+0.5);
				curContribution.setMedian((int) median);
				
				long totalDonations = curContribution.getSum();
				totalDonations += amount;
				curContribution.setSum(totalDonations);
				
				contributionsByZip.put(curRecipient, curContribution);
				String newLine = data[CMTE_ID] + "|" + zipcode + "|" + (int) median + "|" 
									+ listOfContributions.size() + "|" + totalDonations + "\n";
				bw1.write(newLine);
//				System.out.println(newLine);
			}
			
			if ((data[TRANSACTION_DT] != null && !data[TRANSACTION_DT].isEmpty()) && isDateValid(data[TRANSACTION_DT])) {
				int amount = Integer.parseInt(data[TRANSACTION_AMT]);
				String datestring = data[TRANSACTION_DT];
				
				RecipientWithDate curRecipient = new RecipientWithDate(data[CMTE_ID], datestring);
				Contribution curContribution = contributionsByDate.getOrDefault(curRecipient, new Contribution());
				List<Integer> listOfContributions = curContribution.getContributionList();
				
				listOfContributions.add(amount);
				Collections.sort(listOfContributions);
				
				double median;
				if (listOfContributions.size() % 2 == 0)
				    median = ((double) listOfContributions.get(listOfContributions.size()/2) 
				    		+ (double) listOfContributions.get(listOfContributions.size()/2-1))/2;
				else
				    median = (double) listOfContributions.get(listOfContributions.size()/2);
				median = Math.floor(median+0.5);
				curContribution.setMedian((int) median);
				
				long totalDonations = curContribution.getSum();
				totalDonations += amount;
				curContribution.setSum(totalDonations);
				
				contributionsByDate.put(curRecipient, curContribution);				
			}
		}		
		
		for (RecipientWithDate rec: contributionsByDate.keySet()) {
			Contribution curContribution = contributionsByDate.get(rec);
			String newLine = rec.getName() + "|" + rec.getDatestring() + "|" + (int) curContribution.getMedian() + "|" 
					+ curContribution.getContributionList().size() + "|" + curContribution.getSum() + "\n";
			bw2.write(newLine);
		}
		
		br.close();
		bw1.close();
		bw2.close();
	}
	
	
	public static boolean isDateValid(String date) {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
	}
}