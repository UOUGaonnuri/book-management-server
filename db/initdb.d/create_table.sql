create table Book (
    'id' bigint not null auto_increment,
    'createdDate' datetime(6),
    'modifiedDate' datetime(6),
    'author' varchar(255) not null,
    'content' varchar(255) not null,
    'deleted' bit not null,
    'fileName' varchar(255) not null,
    'fileUrl' varchar(255) not null,
    'isbn' integer not null,
    'publisher' varchar(255) not null,
    'stock' integer not null,
    'title' varchar(255) not null,
    primary key ('id')
) engine=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table BorrowBook (
    'id' bigint not null auto_increment,
    'createdDate' datetime(6),
    'modifiedDate' datetime(6),
    'borrowDate' datetime(6) not null,
    'expirationDate' datetime(6) not null,
    'expired' bit not null,
    'BOOK_ID' bigint,
    'MEMBER_ID' bigint,
     primary key ('id')
) engine=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table Member (
    'id' bigint not null auto_increment,
    'createdDate' datetime(6),
    'modifiedDate' datetime(6),
    'email' varchar(50) not null,
    'password' varchar(100) not null,
    'role' varchar(255) DEFAULT 'ROLE_USER',
    'username' varchar(20) not null,
    primary key ('id'),
    UNIQUE KEY `UK_user_email` (`email`),
    UNIQUE KEY `UK_user_name` (`username`)
) engine=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table ZzimBook (
    'id' bigint not null auto_increment,
    'createdDate' datetime(6),
    'modifiedDate' datetime(6),
    'deleted' bit not null,
    'BOOK_ID' bigint,
    'MEMBER_ID' bigint,
    primary key ('id')
) engine=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;