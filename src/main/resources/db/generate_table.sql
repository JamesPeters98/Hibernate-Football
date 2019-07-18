create table ATTACK_WEIGHTS
(
    ID           INTEGER not null
        primary key,
    POSITIONTYPE INTEGER,
    WEIGHT       DOUBLE
);

create table DEFENCE_WEIGHTS
(
    ID           INTEGER not null
        primary key,
    POSITIONTYPE INTEGER,
    WEIGHT       DOUBLE
);

create table FORMATIONS
(
    ID        INTEGER not null
        primary key,
    FORMATION VARCHAR(255),
    POS1      INTEGER,
    POS10     INTEGER,
    POS2      INTEGER,
    POS3      INTEGER,
    POS4      INTEGER,
    POS5      INTEGER,
    POS6      INTEGER,
    POS7      INTEGER,
    POS8      INTEGER,
    POS9      INTEGER
);

create table NATIONALITY
(
    ID          INTEGER not null
        primary key,
    COUNTRY     VARCHAR(255),
    NATIONALITY VARCHAR(255)
);

create table LEAGUES
(
    ID         INTEGER not null
        primary key,
    DIVISION   INTEGER,
    LEAGUENAME VARCHAR(255),
    COUNTRY    INTEGER,
    constraint FK21VPVBQGQC29FOPX9VQ5TN1PW
        foreign key (COUNTRY) references NATIONALITY
);

create table PLAYER_STATS
(
    ID               INTEGER not null
        primary key,
    ACCELERATION     INTEGER,
    AGGRESSION       INTEGER,
    AGILITY          INTEGER,
    BALANCE          INTEGER,
    BALL_CONTROL     INTEGER,
    COMPOSURE        INTEGER,
    CROSSING         INTEGER,
    CURVE            INTEGER,
    DRIBBLING        INTEGER,
    FINISHING        INTEGER,
    FK_ACCURACY      INTEGER,
    GK_DIVING        INTEGER,
    GK_HANDLING      INTEGER,
    GK_KICKING       INTEGER,
    GK_POSITIONING   INTEGER,
    GK_REFLEXES      INTEGER,
    HEADING_ACCURACY INTEGER,
    INTERCEPTIONS    INTEGER,
    JUMPING          INTEGER,
    LONG_PASSING     INTEGER,
    LONG_SHOTS       INTEGER,
    MARKING          INTEGER,
    PENALTIES        INTEGER,
    POSISTIONING     INTEGER,
    REACTIONS        INTEGER,
    SHORT_PASSING    INTEGER,
    SHOT_POWER       INTEGER,
    SLIDING_TACKLE   INTEGER,
    SPRINT_SPEED     INTEGER,
    STAMINA          INTEGER,
    STANDING_TACKLE  INTEGER,
    STRENGTH         INTEGER,
    VISION           INTEGER,
    VOLLEYS          INTEGER
);

create table POSITIONTYPE
(
    ID               INTEGER not null
        primary key,
    ACCELERATION     DOUBLE,
    AGGRESSION       DOUBLE,
    AGILITY          DOUBLE,
    BALANCE          DOUBLE,
    BALL_CONTROL     DOUBLE,
    COMPOSURE        DOUBLE,
    CROSSING         DOUBLE,
    CURVE            DOUBLE,
    DRIBBLING        DOUBLE,
    FINISHING        DOUBLE,
    FK_ACCURACY      DOUBLE,
    GK_DIVING        DOUBLE,
    GK_HANDLING      DOUBLE,
    GK_KICKING       DOUBLE,
    GK_POSITIONING   DOUBLE,
    GK_REFLEXES      DOUBLE,
    HEADING_ACCURACY DOUBLE,
    INTERCEPTIONS    DOUBLE,
    JUMPING          DOUBLE,
    LONG_PASSING     DOUBLE,
    LONG_SHOTS       DOUBLE,
    MARKING          DOUBLE,
    PENALTIES        DOUBLE,
    POSISTIONING     DOUBLE,
    POSITION         VARCHAR(255),
    REACTIONS        DOUBLE,
    SHORT_PASSING    DOUBLE,
    SHOT_POWER       DOUBLE,
    SLIDING_TACKLE   DOUBLE,
    SPRINT_SPEED     DOUBLE,
    STAMINA          DOUBLE,
    STANDING_TACKLE  DOUBLE,
    STRENGTH         DOUBLE,
    VISION           DOUBLE,
    VOLLEYS          DOUBLE
);

create table RATING_WEIGHTS
(
    ID                 INTEGER not null
        primary key,
    ATTACK_ATTACKER    DOUBLE,
    ATTACK_CENTREBACK  DOUBLE,
    ATTACK_FULLBACK    DOUBLE,
    ATTACK_GOALKEEPER  DOUBLE,
    ATTACK_MIDFIELDER  DOUBLE,
    DEFENCE_ATTACKER   DOUBLE,
    DEFENCE_CENTREBACK DOUBLE,
    DEFENCE_FULLBACK   DOUBLE,
    DEFENCE_GOALKEEPER DOUBLE,
    DEFENCE_MIDFIELDER DOUBLE
);

create table POSITIONS
(
    ID               INTEGER not null
        primary key,
    ACCELERATION     DOUBLE,
    AGGRESSION       DOUBLE,
    AGILITY          DOUBLE,
    BALANCE          DOUBLE,
    BALL_CONTROL     DOUBLE,
    COMPOSURE        DOUBLE,
    CROSSING         DOUBLE,
    CURVE            DOUBLE,
    DRIBBLING        DOUBLE,
    FINISHING        DOUBLE,
    FK_ACCURACY      DOUBLE,
    GK_DIVING        DOUBLE,
    GK_HANDLING      DOUBLE,
    GK_KICKING       DOUBLE,
    GK_POSITIONING   DOUBLE,
    GK_REFLEXES      DOUBLE,
    HEADING_ACCURACY DOUBLE,
    INTERCEPTIONS    DOUBLE,
    JUMPING          DOUBLE,
    LONG_PASSING     DOUBLE,
    LONG_SHOTS       DOUBLE,
    MARKING          DOUBLE,
    PENALTIES        DOUBLE,
    POSISTIONING     DOUBLE,
    POSITION         VARCHAR(255),
    REACTIONS        DOUBLE,
    SHORT_PASSING    DOUBLE,
    SHOT_POWER       DOUBLE,
    SLIDING_TACKLE   DOUBLE,
    SPRINT_SPEED     DOUBLE,
    STAMINA          DOUBLE,
    STANDING_TACKLE  DOUBLE,
    STRENGTH         DOUBLE,
    VISION           DOUBLE,
    VOLLEYS          DOUBLE,
    POSITIONTYPE     INTEGER,
    constraint FKOLO1YGK05ORW4P25N06ONEEEY
        foreign key (ID) references RATING_WEIGHTS,
    constraint FKWYJLEF97L8X4JOD61MUANHUN
        foreign key (POSITIONTYPE) references POSITIONTYPE
);

create table TEAMS
(
    ID       INTEGER not null
        primary key,
    LEAGUEID INTEGER,
    NAME     VARCHAR(255),
    constraint FKDCL1BAT2MJM6OLCIP0FCVJ6P9
        foreign key (LEAGUEID) references LEAGUES
);

create table PLAYERS
(
    ID          INTEGER not null
        primary key,
    AGE         INTEGER,
    GROWTH      INTEGER,
    NAME        VARCHAR(255),
    NATIONALITY INTEGER,
    OVERALL     INTEGER,
    PHOTO       VARCHAR(255),
    POSITION_ID INTEGER,
    TEAMID      INTEGER,
    constraint FK5I4KMCCO2KQ2OPEVFIRQ0AK7V
        foreign key (ID) references PLAYER_STATS,
    constraint FKJCOY8EU3RHJEEIYAAON8NP803
        foreign key (POSITION_ID) references POSITIONS,
    constraint FKLRP7UMU5G84KBE1EXJ0IQQNGA
        foreign key (NATIONALITY) references NATIONALITY,
    constraint FKR1TOJ39KHG7JRA2TCR5YCJP7O
        foreign key (TEAMID) references TEAMS
);

create table PLAYER_RATINGS
(
    POSITION_ID   INTEGER not null,
    ID            INTEGER not null,
    ATTACKRATING  DOUBLE,
    DEFENCERATING DOUBLE,
    RATING        DOUBLE,
    primary key (POSITION_ID, ID),
    constraint FKR7ORO18E86JELSOUHWPVJ99HQ
        foreign key (ID) references PLAYERS
);


