# Demo Vertx + React

How to start a backend with Vertx and a React frontend with ViteJS.

## References

- [Vert.x How To](https://how-to.vertx.io/single-page-react-vertx-howto/)
- [Vert.x Docs](https://vertx.io/docs/3.9.13)

## Prerequisite

- [NodeJS](https://nodejs.org/en/)
- [HTTPie](https://httpie.io/)

## Install

```bash
git clone git@github.com:ccreusat/demo-vertx-react-vitejs.git
```

Go to `src/main/frontend` and install React

```bash
yarn
```

### Backend

Inside root folder, start Vertx on Port `3001` with Gradle

```bash
./gradlew run
```

For this example, `/api/message` will send this message:

> Hello React from Vert.x!

You can check if route is working fine with:

- HTTPie in your terminal

```bash
http :3001/api/message
```

- Using route in your browser

```bash
http://localhost:3001/api/message
```

> Hello React from Vert.x!

### Frontend

Start ViteJS localhost on Port `3000` inside `src/main/frontend`

```bash
yarn dev
```

You should see `Hello React from Vert.x!` inside your client app.

### Server Proxy

ViteJS Server will listen the Port `3001` on `/api` route:

```bash
server: {
    proxy: {
      "/api": "http://localhost:3001",
    },
    host: "0.0.0.0",
    port: 3000,
    open: true,
}
```

[Server Options](https://vitejs.dev/config/server-options.html#server-proxy)

## Build

The build task will create the frontend application and add the bundle to: `build/classes/java/main/webroot`

```bash
./gradlew run
```

Browse to `http://localhost:3001` and you should see the build version.
