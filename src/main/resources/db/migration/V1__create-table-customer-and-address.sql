create table public.CUSTOMER(

    CUSTOMER_ID serial not null,
    COMPANY_NAME varchar(100) not null,
    CNPJ varchar(14) not null unique,
    STATE_REGISTRATION varchar(12) not null unique,
    EMAIL varchar(100) not null,
    PHONE varchar(100) not null,
    RESPONSIBLE varchar(100) not null,

    primary key(CUSTOMER_ID)

);

create table public.ADDRESS(

    ADDRESS_ID serial not null,
    CUSTOMER_ID bigint not null,
    ZIP_CODE varchar(8) not null,
    STREET varchar(100) not null,
    NUMBER varchar(8),
    COMPLEMENTS varchar(100) ,
    DISTRICT varchar(100) not null,
    CITY varchar(100) not null,
    STATE varchar(100) not null,
    POINT_REFERENCE varchar(200),

    primary key(ADDRESS_ID),
    CONSTRAINT FK_CUSTOMER_ADDRESS FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMER (CUSTOMER_ID)

);