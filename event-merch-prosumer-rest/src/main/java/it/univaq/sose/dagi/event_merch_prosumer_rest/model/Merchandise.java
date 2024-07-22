package it.univaq.sose.dagi.event_merch_prosumer_rest.model;

public class Merchandise {
	
	private Long id;
	private Long eventId;
	private long barCode;
	private String name;
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

