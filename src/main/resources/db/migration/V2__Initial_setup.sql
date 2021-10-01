CREATE TABLE public.golf_tournament (
	id int8 NOT NULL,
	external_id varchar(255) NOT NULL,
	external_source varchar(255) NOT NULL,
	"name" varchar(255) NULL,
	course_name varchar(255) NULL,
	country_code varchar(255) NULL,
	start_date date NOT NULL,
	end_date date NOT NULL,
	rounds int4 NOT NULL,
	CONSTRAINT golf_tournament_pkey PRIMARY KEY (id)
);

CREATE INDEX external_id_idx ON public.golf_tournament USING btree (external_id);