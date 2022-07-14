package application.model;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Vector;

@SuppressWarnings("serial")
public class Party implements Serializable {
	public enum eSection {
		ימין, שמאל, מרכז
	};

	private String name;
	private eSection section;
	private int birthYear;
	private int birthMonth;
	private LocalDate creationDate = LocalDate.now();;
	private Vector <Candidate> candidates= new Vector <Candidate>();


	protected Party(String name, eSection section, int birthYear, int birthMonth) {
		this.name = name;
		this.section = section;
		this.birthYear = birthYear;
		this.birthMonth = birthMonth;
//		this.candidates= new Candidate[1];
	}

	public eSection getSection() {
		return section;
	}

	public String getName() {
		return name;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public Vector <Candidate> getCandidates() {
		return candidates;
	}



	public void addCandidate(Candidate candidate) {

		candidates.add(candidate);

	}


	public static Vector <Candidate> candidatesSort ( Vector <Candidate> candidates) {
	for (int i=0; i<candidates.size(); i++) {
		//for (int i=candidates.length-1; i<0; i--) {
		for (int j=0; j<i;j++) {
			if (candidates.get(j)!=null) {
				if (candidates.get(j).compareTo(candidates.get(j+1))> 0) {
					Candidate temp = candidates.get(j);
					candidates.set(j, candidates.get(j+1));
					candidates.set(j+1, temp);
				}
			}
			else {
				break;
			}
		}
	}
	return candidates;
}
	@Override

	public String toString() {
		// fix and sort
		String str = "Name: " + name + ", section: " + section + ", birthDate: " + birthMonth+"/"+birthYear
		+ ", candidates:\n";
		for (int i = 0; i < candidates.size(); i++)

			str = str +"\t"+ (i + 1) + ")" + candidatesSort (candidates).get(i).showCandidate() + "\n";
		return str;

	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Party))
			return false;
		Party other = (Party) obj;
		return other.getName() == getName();
	}

}
