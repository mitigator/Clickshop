FROM node:20-alpine
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
ENV NODE_OPTIONS=--openssl-legacy-provider
EXPOSE 4200
# Use shell form to ensure proper command execution
CMD npm start -- --host 0.0.0.0