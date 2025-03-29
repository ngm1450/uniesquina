# Etapa 1: Build da aplicação Angular
FROM node:18 AS build

WORKDIR /app
COPY . .
RUN npm install
RUN npm run build -- --configuration production

# Etapa 2: Servir a aplicação com NGINX
FROM nginx:alpine
COPY --from=build /app/dist/uniesquina-frontend/ /usr/share/nginx/html
EXPOSE 80
