INSERT INTO `event` (`name`,`description`, `organizer_id`, `location`, `start_date`, `end_date`, `nr_tickets`) VALUES
("Evening with fans", "InnTale has organized an evening with fans.", 3, "Lucca", '2024-07-29 18:00:00', '2024-07-29 22:00:00', 50),
("D&D ESO campaign", "InnTale D&D campaign based on Elder Scrolls Online.", 2, "Lucca", '2024-07-30 20:00:00', '2024-07-30 23:00:00', 10),
("Antonello Venditti live", "Canta Antonello Venditti in live per la perdonanza.", 2, "L'Aquila", '2024-08-12 17:00:00', '2024-08-12 21:00:00', 400),
("Linkin Park live", "I linkin Park tornano a Milano (Ippodromo SNAI la Maura) 7 anni dopo l'ultima volta!.", 3, "Milano", '2025-06-24 17:00:00', '2025-06-24 21:00:00', 400);

INSERT INTO `feedback` (`user_id`,`event_id`, `rating`, `body`) VALUES
(1, 1, 5, "Ho conosciuto Kurolily, 5/5!"),
(3, 1, 4, "Li seguo su twitch da tempo, ve li consiglio."),
(2, 3, 5, "Ne è valsa la pensa prenotare mesi in anticipo, mi so beccato la seconda fila."),
(5, 3, 5, "Bellissimo, bravo Antonello e agli organizzatori."),
(1, 2, 5, "Prima volta che vedo una campagna di D&D dal vivo e anche da spettatore mi so divertito un mondo."),
(3, 2, 5, "Ovviamente il barbaro di Simone Fancazzista il mio preferito."),
(4, 1, 3, "Molto bello, solo che sono rimasta in piedi le prime ore.");

INSERT INTO `sold_ticket` (`user_id`,`event_id`, `reference_date`) VALUES
(1, 1, '2024-07-29 18:00:00'),
(3, 1, '2024-07-29 18:00:00'),
(2, 3, '2024-08-12 21:00:00'),
(5, 3, '2024-08-12 21:00:00'),
(1, 2, '2024-07-30 23:00:00'),
(3, 2, '2024-07-30 23:00:00'),
(4, 3, '2024-08-12 21:00:00'),
(2, 4, '2024-06-24 17:00:00');

INSERT INTO `ticket_info` (`event_id`,`reference_date`, `available_tickets`) VALUES
(1, '2025-07-29 18:00:00', 50),
(2, '2025-07-30 20:00:00', 10),
(3, '2025-08-12 17:00:00', 400),
(4, '2025-06-24 17:00:00', 400);