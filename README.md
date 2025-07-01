# Currículo com IA - backend

## Requisitos
- Java 17+
- Maven
- Conta supabase

## Configurações

- arquivo .env com (info na conta supabase):
````
SUPABASE_URL=seu-projeto
SUPABASE_SERVICE_KEY=sua-key
JWT_SECRET=seu-jwt
````

## Run
- porta padrão: 8081
````
./mvnw spring-boot:run
````

## Endpoints
- GET "/api/profile"