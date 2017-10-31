

import java.util.ArrayList;
import java.util.List;

public class Contribution {
	
	private List<Integer> contributions;
	private long sum;
	private int median;

	public double getMedian() {
		return median;
	}

	public void setMedian(int median) {
		this.median = median;
	}

	public Contribution() {
		this.contributions = new ArrayList<Integer>();
	}

	public long getSum() {
		return sum;
	}

	public void setSum(long sum) {
		this.sum = sum;
	}

	public List<Integer> getContributionList() {
		return contributions;
	}

	public void setContributions(List<Integer> contributions) {
		this.contributions = contributions;
	}

}
