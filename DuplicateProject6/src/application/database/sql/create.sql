BEGIN;

CREATE TABLE IF NOT EXISTS public.appointments
(
    id serial NOT NULL,
    description character varying(200),
    location character varying(200),
    title character varying(200),
    date character varying(200),
    starttime character varying(200),
    endtime character varying(200),
    name character varying(200),
    UNIQUE (name, starttime, endtime),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.records
(
    username character varying(200),
    password character varying(200)
);


CREATE TABLE IF NOT EXISTS public.user_appointments
(
    user_id bigint NOT NULL,
    name character varying(200) NOT NULL
);

END;
