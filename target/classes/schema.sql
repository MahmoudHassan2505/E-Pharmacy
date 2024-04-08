BEGIN;

CREATE TABLE IF NOT EXISTS users
(
    username character varying(50) COLLATE pg_catalog."default" NOT NULL,
    password character varying(50) COLLATE pg_catalog."default" NOT NULL,
    enabled smallint NOT NULL,
    phone bigint NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS authorities
(
    username character varying(50) COLLATE pg_catalog."default" NOT NULL,
    authority character varying(50) COLLATE pg_catalog."default" NOT NULL,
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    CONSTRAINT authorities_idx_1 UNIQUE (username, authority),
    CONSTRAINT authorities_ibfk_1 FOREIGN KEY (username)
        REFERENCES users (username) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION

);

CREATE TABLE IF NOT EXISTS disease
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT disease_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS inventory
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    medicine_id bigint NOT NULL,
    amount bigint NOT NULL,
    price bigint NOT NULL,
    expire_date date NOT NULL,
    CONSTRAINT inventory_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS medicine_category
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT medicine_category_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS medicine
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    barcode bigint NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    dosageform character varying COLLATE pg_catalog."default",
    strength character varying COLLATE pg_catalog."default",
    manufacturer character varying COLLATE pg_catalog."default",
    activeingredient character varying COLLATE pg_catalog."default" NOT NULL,
    alertexpired date NOT NULL,
    alertamount bigint NOT NULL,
    category_id bigint NOT NULL,
    arabicname character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT medicine_pkey PRIMARY KEY (id),
    CONSTRAINT barcode_unique UNIQUE (barcode),
    CONSTRAINT "medicine_category_FK" FOREIGN KEY (category_id)
        REFERENCES medicine_category (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION

);

CREATE TABLE IF NOT EXISTS supplier
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT supplier_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS orders
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    supplyrequest bigint NOT NULL,
    deliveryrequest bigint NOT NULL,
    supplier_id bigint NOT NULL,
    dateofsupply date NOT NULL,
    CONSTRAINT "Order_pkey" PRIMARY KEY (id),
    CONSTRAINT "order_supplier_FK" FOREIGN KEY (supplier_id)
        REFERENCES supplier (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);



CREATE TABLE IF NOT EXISTS order_medicine
(
    order_id bigint NOT NULL,
    medicine_id bigint NOT NULL,
    amount bigint NOT NULL,
    expirydate date NOT NULL,
    price bigint NOT NULL,
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    CONSTRAINT order_medicine_pkey PRIMARY KEY (id),
    CONSTRAINT "medicineFK" FOREIGN KEY (medicine_id)
        REFERENCES medicine (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
       ,
    CONSTRAINT "orderFK" FOREIGN KEY (order_id)
        REFERENCES orders (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION

);



CREATE TABLE IF NOT EXISTS patient
(
    national_id bigint NOT NULL,
    student_id bigint,
    is_chronic boolean NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    gender character varying COLLATE pg_catalog."default",
    level character varying COLLATE pg_catalog."default",
    phone_number character varying COLLATE pg_catalog."default",
    CONSTRAINT pateint_pkey PRIMARY KEY (national_id)
);

CREATE TABLE IF NOT EXISTS patient_disease
(
    patient_id bigint NOT NULL,
    disease_id bigint NOT NULL,
    CONSTRAINT "disease_FK" FOREIGN KEY (disease_id)
        REFERENCES disease (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        ,
    CONSTRAINT "patient_FK" FOREIGN KEY (patient_id)
        REFERENCES patient (national_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION

);

CREATE TABLE IF NOT EXISTS prescription_category
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT prescription_category_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS prescription
(
    id bigint NOT NULL,
    category_id bigint NOT NULL,
    is_allowed boolean NOT NULL,
    pateint_id bigint NOT NULL,
    diagnosis character varying COLLATE pg_catalog."default",
    CONSTRAINT prescription_pkey PRIMARY KEY (id),
    CONSTRAINT prescription_id_key UNIQUE (id),
    CONSTRAINT "category_FK" FOREIGN KEY (category_id)
        REFERENCES prescription_category (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "patient_prescription_FK" FOREIGN KEY (pateint_id)
        REFERENCES patient (national_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);






CREATE TABLE IF NOT EXISTS useage
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    prescription_id bigint NOT NULL,
    date date NOT NULL,
    CONSTRAINT useage_pkey PRIMARY KEY (id),
    CONSTRAINT "prescription_FK" FOREIGN KEY (prescription_id)
        REFERENCES prescription (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION

);

CREATE TABLE IF NOT EXISTS useage_medicine
(
    useage_id bigint NOT NULL,
    medicine_id bigint NOT NULL,
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    price bigint NOT NULL,
    amount bigint NOT NULL,
    CONSTRAINT useage_medicine_pkey PRIMARY KEY (id),
    CONSTRAINT "medicine_useage_FK" FOREIGN KEY (medicine_id)
        REFERENCES medicine (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        ,
    CONSTRAINT "useage_FK" FOREIGN KEY (useage_id)
        REFERENCES useage (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION

);

CREATE TABLE IF NOT EXISTS notifications
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 0 MINVALUE 0 MAXVALUE 9223372036854775807 CACHE 1 ),
    type smallint NOT NULL,
    medicine_id bigint NOT NULL,
    CONSTRAINT notifications_pkey PRIMARY KEY (id),
    CONSTRAINT "medicine_Fk" FOREIGN KEY (medicine_id)
        REFERENCES medicine (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS prescription_medicine
(
    prescription_id bigint NOT NULL,
    medicine_id bigint NOT NULL
);

CREATE TABLE IF NOT EXISTS notifications
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 0 MINVALUE 0 MAXVALUE 9223372036854775807 CACHE 1 ),
    medicine_id bigint NOT NULL,
    message character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT notifications_pkey PRIMARY KEY (id),
    CONSTRAINT "medicine_Fk" FOREIGN KEY (medicine_id)
        REFERENCES medicine (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE IF EXISTS public.inventory
    ADD CONSTRAINT "order_medicineFK" FOREIGN KEY (medicine_id)
        REFERENCES order_medicine (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;
CREATE INDEX IF NOT EXISTS "fki_order_medicineFK"
    ON inventory(medicine_id);

END;