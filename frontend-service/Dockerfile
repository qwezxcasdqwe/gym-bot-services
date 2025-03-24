# Первый этап: сборка приложения
FROM node:16 AS build

RUN getent group node || groupadd -g 1000 node && \
    getent passwd node || useradd -r -u 1000 -g node -m node

# Устанавливаем рабочую директорию в контейнере
WORKDIR /app

# Копируем package.json и package-lock.json для установки зависимостей
COPY ./package*.json ./

# Очищаем кэш npm для оптимизации
RUN npm cache clean --force

# Устанавливаем все зависимости
RUN npm install

# Копируем все файлы приложения в контейнер
COPY . .

RUN npm install typescript@4.9.5 --save-dev
RUN npm install
RUN npm run build


# Второй этап: использование Nginx для раздачи статики
FROM nginx:alpine

# Копируем статические файлы, собранные на первом этапе
COPY --from=build /app/build /usr/share/nginx/html
