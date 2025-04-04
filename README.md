# 🔐 JWT Authentication with Spring Security

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)

This project is a practical example of authentication and authorization using **JWT (JSON Web Token)** with **Spring Security**. It demonstrates how to protect endpoints based on different user scopes (`COMMON` and `ADMIN`).

## Technologies Used

- Java 21
- Spring Boot
- Spring Security
- JWT
- Maven
- MySQL

## Features

- New user registration
- Authentication with JWT token response
- Role-based authorization (`COMMON`, `ADMIN`)
- Protected `/dashboard` endpoint with dynamic content based on user scope

## Application Flow

### Obtain Token

- **POST `/auth/register`**  
  Registers a new user. Returns a JWT token upon completion.

or

- **POST `/auth/login`**  
  Logs in with email and password. Returns a valid JWT token.

### Test Scope

- **GET `/dashboard`**  
  Protected endpoint:
    - `COMMON` user: views only their own information.
    - `ADMIN` user: views information of all users.

## Security

- JWT authentication in the header: `Authorization: Bearer <token>`
- Security filters configured with Spring Security
- Token validation on each protected request

## Testing

You can test the API using the [bruno](https://www.usebruno.com/) tool with the provided [JSON file](./Bruno.json).

## Configuration

### Encryption Keys

This project uses asymmetric encryption, so you need to follow these steps to configure the public and private keys:

1. Open the terminal in the `/src/main/resources` directory
2. Create the private key:
```sh
   openssl genrsa > private.pem
```
3. Create the public key:
```sh
   openssl rsa -in private.pem -pubout -out public.pem
```
These keys will be used to sign and validate JWT tokens, ensuring greater security in authentication.
If you have any questions, [this website](https://cryptotools.net/rsagen) provides a good explanation of how to generate RSA keys.

### Database

In `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## License

This project is licensed under the MIT License.

