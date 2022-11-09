create table if not exists GENRES
(
    ID   INTEGER auto_increment,
    NAME CHARACTER VARYING,
    constraint GENRE_PK
        primary key (ID)
);

create table if not exists MPA
(
    ID   INTEGER auto_increment,
    NAME CHARACTER VARYING,
    constraint MPA_PK
        primary key (ID)
);

create table if not exists FILMS
(
    ID           INTEGER auto_increment,
    NAME         CHARACTER VARYING,
    DESCRIPTION  CHARACTER VARYING,
    RELEASE_DATE DATE,
    DURATION     INTEGER,
    RATE         CHARACTER VARYING,
    MPA_ID       INTEGER,
    constraint FILMS_PK
        primary key (ID),
    constraint FILMS_FOREIGN_KEY_MPA
        foreign key (MPA_ID) references MPA(ID)
);

create table if not exists FILM_GENRE
(
    FILM_ID  INTEGER not null,
    GENRE_ID INTEGER not null,
    constraint FILM_GENRE_PK
        primary key (FILM_ID, GENRE_ID),
    constraint FILM_GENRE_TO_FILM_FK
        foreign key (FILM_ID) references FILMS (ID) ON DELETE CASCADE,
    constraint FILM_GENRE_TO_USER_FK
        foreign key (GENRE_ID) references GENRES (ID) ON DELETE CASCADE
);

create table if not exists USERS
(
    ID        INTEGER auto_increment,
    EMAIL     CHARACTER VARYING,
    LOGIN     CHARACTER VARYING,
    NAME      CHARACTER VARYING,
    BIRTHDAY DATE,
    constraint USERS_PK
        primary key (ID)
);

create table if not exists FRIENDSHIP
(
    USER_ID   INTEGER not null,
    FRIEND_ID INTEGER not null,
    constraint FRIENDSHIP_PK
        primary key (USER_ID, FRIEND_ID),
    constraint FRIENDSHIP_USER_FK
        foreign key (USER_ID) references USERS(ID) ON DELETE CASCADE,
    constraint FRIENDSHIP_FRIEND_FK
        foreign key (FRIEND_ID) references USERS(ID) ON DELETE CASCADE
);

create table if not exists LIKES
(
    USER_ID INTEGER not null,
    FILM_ID INTEGER not null,
    constraint LIKES_PK
        primary key (USER_ID, FILM_ID),
    constraint LIKES_USER_FK
        foreign key (USER_ID) references USERS(ID) ON DELETE CASCADE,
    constraint LIKES_FILMS_FK
        foreign key (FILM_ID) references FILMS(ID) ON DELETE CASCADE
);