package it.univaq.sose.dagi.merchandising_rest.model;

import java.util.Comparator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "merchandise")
public class Merchandise implements Comparable<Merchandise>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true)
	private Long eventId;

	@Column(nullable = false)
	private long barCode;

	@Column(nullable = false)
	private String name;

	@Column(nullable = true)
	private String description;

	public Merchandise() {
		super();
	}

	public Merchandise(Long id, Long eventId, long barCode, String name, String description) {
		super();
		this.id = id;
		this.eventId = eventId;
		this.barCode = barCode;
		this.name = name;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public long getBarCode() {
		return barCode;
	}

	public void setBarCode(long barCode) {
		this.barCode = barCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// This method uses a StringBuilder to construct a string that includes the
	// values of the object's fields, each prefixed
	// with its field name and separated by newline characters for clarity. This
	// method starts
	// by initializing the StringBuilder with a header indicating that the string
	// represents a Merchandise object.
	// It then appends the id, eventId, barCode, name, and description fields, each
	// followed by a newline.
	// Finally, it converts the StringBuilder content to a string and returns it.
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Merchandise object:\n\n");
		sb.append("id: ");
		sb.append(id);
		sb.append("\n");
		sb.append("eventId: ");
		sb.append(eventId);
		sb.append("\n");
		sb.append("barCode: ");
		sb.append(barCode);
		sb.append("\n");
		sb.append("name: ");
		sb.append(name);
		sb.append("\n");
		sb.append("description: ");
		sb.append(description);
		sb.append("\n");
		return sb.toString();
	}

	@Override
	public int compareTo(Merchandise o) {
		if (this.id > o.id)
			return 1;
		else if (this.id < o.id)
			return -1;
		else
			return 0;
	}

	public static MerchandiseIdDescendingComparator getIdDescComparator() {
		return new MerchandiseIdDescendingComparator();
	}

	public static MerchandiseNameAscendingComparator getNameAscComparator() {
		return new MerchandiseNameAscendingComparator();
	}

	public static MerchandiseNameDescendingComparator getNameDescComparator() {
		return new MerchandiseNameDescendingComparator();
	}

	// This method compares the current object to another object to determine
	// whether they are equal.
	// If the object passed as a parameter is null or not an instance of the Merchandise
	// class, returns false.
	// Otherwise, cast the object to type Merchandise and compare the merch IDs.
	// If the IDs are equal, the method returns true, indicating that the Merchandises are
	// considered equal.
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Merchandise))
			return false;
		Merchandise casted = (Merchandise) obj;
		return this.getId().equals(casted.getId());
	}
}

//This represents a comparator that sorts merchs by their ID in descending order.
//The compare method compares the IDs of two Merchandises. If the ID of the first merch (o1) is greater than that of the second (o2), it returns -1, indicating that o1 must precede o2.
//If the ID of o1 is less, returns 1, placing o1 after o2. If the IDs are equal, it returns 0, maintaining the original order between the two merchs.
class MerchandiseIdDescendingComparator implements Comparator<Merchandise> {

	@Override
	public int compare(Merchandise o1, Merchandise o2) {
		if (o1.getId() > o2.getId())
			return -1;
		else if (o1.getId() < o2.getId())
			return 1;
		else
			return 0;
	}

}

//This instead sorts the merchs by their name in ascending order.
//The compare method uses the compareTo method of the String class to compare merch names.
//If the name of the first merch (o1) is alphabetically before that of the second merch (o2), it returns a negative value.
//If the name of o1 is after that of o2, returns a positive value. If the names are identical, returns 0.
class MerchandiseNameAscendingComparator implements Comparator<Merchandise> {

	@Override
	public int compare(Merchandise o1, Merchandise o2) {
		return o1.getName().compareTo(o2.getName());
	}

}

//Finally, this sorts merchs by their name in descending order.
//The compare method compares the merch names using compareTo and then multiplies the result by -1.
//This reverses the natural order, so that an merch with a name that would be first in an ascending sort will now come after an merch with a name that would be after in an ascending sort.
//If the name of o1 comes before that of o2, it returns a positive value. If o1 comes after o2, it returns a negative value. If the names are equal, returns 0.
class MerchandiseNameDescendingComparator implements Comparator<Merchandise> {

	@Override
	public int compare(Merchandise o1, Merchandise o2) {
		return o1.getName().compareTo(o2.getName()) * -1;
	}

}
