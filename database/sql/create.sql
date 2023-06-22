BEGIN;

CREATE TABLE IF NOT EXISTS public.appointments
(
    appointment_id serial NOT NULL,
    appointment_title character varying(50),
    appointment_description character varying(200),
    startTime date NOT NULL,
    endTime date  NOT NULL,
    appointment_status character varying(50),
    PRIMARY KEY (appointment_id)
);

CREATE TABLE IF NOT EXISTS public.users
(
    user_id serial NOT NULL,
    user_name character varying(50) NOT NULL,
    user_password character varying(50),
    user_email character varying(50),
    phone_number character varying(20) NOT NULL,
    PRIMARY KEY (user_id)
);


CREATE TABLE IF NOT EXISTS public.user_appointments
(
    user_id bigint NOT NULL,
    appointment_id bigint NOT NULL
);

ALTER TABLE IF EXISTS public.user_appointments
    ADD FOREIGN KEY (user_id)
    REFERENCES public.users (user_id) MATCH SIMPLE,
    ADD FOREIGN KEY (appointment_id)
    REFERENCES public.appointments (appointment_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;
END;