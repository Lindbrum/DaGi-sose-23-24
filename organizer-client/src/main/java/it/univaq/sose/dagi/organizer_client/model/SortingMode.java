package it.univaq.sose.dagi.organizer_client.model;

public enum SortingMode {
	ID_DESC, ID_ASC, ALPHABETICAL_ASC, ALPHABETICAL_DESC;

	@Override
	public String toString() {
		switch (this) {
		default:
		case ID_DESC:
			return "newest";
		case ID_ASC:
			return "oldest";
		case ALPHABETICAL_DESC:
			return "A to Z";
		case ALPHABETICAL_ASC:
			return "Z to A";
		}
	}
}