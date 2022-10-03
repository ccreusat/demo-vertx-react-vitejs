package io.vertx.starter;

import java.util.HashSet;
import java.util.Set;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start() {
    
    Router router = Router.router(vertx);
    Route messageRoute = router.get("/api/message"); // (1)
    messageRoute.handler(rc -> {
      rc.response().end("Hello React from Vert.x!"); // (2)
    });

    router.get().handler(StaticHandler.create()); // (3)

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(3001);

  }

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx(); // (1)
    vertx.deployVerticle(new MainVerticle()); // (2)
  }

}
