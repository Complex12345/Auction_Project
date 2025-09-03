FROM postgres:latest

LABEL authors="rayya"

# Expose PostgreSQL default port
EXPOSE 5432

# Default user, password, and database
ENV POSTGRES_USER=rayya
ENV POSTGRES_PASSWORD=secretpassword
ENV POSTGRES_DB=User
