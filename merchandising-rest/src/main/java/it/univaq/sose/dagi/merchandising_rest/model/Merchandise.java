package it.univaq.sose.dagi.merchandising_rest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "merchandise")
public class Merchandise {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = true)
	private Long eventId;
	
	@Column(nullable = false)
	private long barCode;
	
	@Column(nullable = false)
	private String name;
	
	@Column()
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

	//This method uses a StringBuilder to construct a string that includes the values of the object's fields, each prefixed
	//with its field name and separated by newline characters for clarity. This method starts
	//by initializing the StringBuilder with a header indicating that the string represents a Merchandise object.
	//It then appends the id, eventId, barCode, name, and description fields, each followed by a newline.
	//Finally, it converts the StringBuilder content to a string and returns it.
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Merchandise object:\n\n");
		sb.append("id: "); sb.append(id); sb.append("\n");
		sb.append("eventId: "); sb.append(eventId); sb.append("\n");
		sb.append("barCode: "); sb.append(barCode); sb.append("\n");
		sb.append("name: "); sb.append(name); sb.append("\n");
		sb.append("description: "); sb.append(description); sb.append("\n");
		return sb.toString();
	}
	
	
	
}
