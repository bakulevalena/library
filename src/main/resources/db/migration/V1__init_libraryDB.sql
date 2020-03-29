create sequence hibernate_sequence start 1 increment 1;

create table author (
        id int8 not null,
        author_name varchar(255) not null,
        primary key (id)
);

create table author_books (
        author_id int8 not null,
        books_id int8 not null
);

create table book (
        id int8 not null,
        creation_date timestamp,
        book_title varchar(255) not null,
        author_id int8, primary key (id)
);

alter table if exists author_books
    add constraint UK_fxksjqa1a5dnqf0egcdxlrcna
    unique (books_id);

alter table if exists author_books
    add constraint FKr514ej8rhei197wx3nrvp0qie
    foreign key (books_id) references book;

alter table if exists author_books
    add constraint FKfvabqdr9njwv4khjqkf1pbmma
    foreign key (author_id) references author;

alter table if exists book
    add constraint FKklnrv3weler2ftkweewlky958
    foreign key (author_id) references author;
