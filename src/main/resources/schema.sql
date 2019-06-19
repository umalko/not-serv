CREATE DATABASE "notification-service-db";

CREATE TABLE public.unanswered_chats(
    chat_id varchar(255) NOT NULL,
    received_at bigint
);