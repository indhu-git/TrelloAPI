package com.trello.api;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Assignment {
    String baseURI = "https://api.trello.com/1/";
    public static Map<String, String> queryParams = new HashMap<>();
    public static Map<String, String> headers = new HashMap<>();

    public static String boardId ="";

    public static String listId1 ="";
    public static String listId2 ="";
    public static String listId3 ="";

    public static String list1Card = "";
    public static String list2Card = "";
    public static String list3Card = "";

    @Test
    public void createBoard() {
        queryParams.put("key", "3f2e4eca21e892561fdf169d912fd7ab");
        queryParams.put("token", "5e5dfafaf970000aed474bacdf1ed68cae1bc37a1b10471d19faabbc8fca5510");
        headers.put("Content-Type","application/json" );
        headers.put("Accept","application/json" );
        boardId = given().when()
                .headers(headers)
                .queryParams(queryParams)
                .queryParams("name", "Board for Automation")
                .post(baseURI + "boards")
                .then()
                .statusCode(200)
                .extract()
                .path("id");
    }


    @Test(dependsOnMethods = "createBoard")
    public void createList(){

        listId1 = given()
                .headers(headers)
                .queryParams(queryParams)
                .queryParams("name", "To Do")
                .post(baseURI+"boards/"+boardId+"/lists")
                .then()
                .extract()
                .path("id");

        listId2 = given()
                .headers(headers)
                .queryParams(queryParams)
                .queryParams("name", "Doing")
                .post(baseURI+"boards/"+boardId+"/lists")
                .then()
                .extract()
                .path("id");

        listId3 = given()
                .headers(headers)
                .queryParams(queryParams)
                .queryParams("name", "Done")
                .post(baseURI+"boards/"+boardId+"/lists")
                .then()
                .extract()
                .path("id");
    }

    @Test(dependsOnMethods = "createList")
    public void createCard(){
        String uri = "https://api.trello.com/1/cards";

        list1Card = given()
                .headers(headers)
                .queryParams(queryParams)
                .queryParams("name", "Card for To Do list")
                .queryParams("idList", listId1)
                .post(uri)
                .then()
                .extract()
                .path("id");

        list2Card = given()
                .headers(headers)
                .queryParams(queryParams)
                .queryParams("name", "Card for Doing list")
                .queryParams("idList", listId2)
                .post(uri)
                .then()
                .extract()
                .path("id");
        list3Card = given()
                .headers(headers)
                .queryParams(queryParams)
                .queryParams("name", "Card for Done List")
                .queryParams("idList", listId3)
                .post(uri)
                .then()
                .extract()
                .path("id");
    }


    @Test(dependsOnMethods = "createCard")
    public void updateCardName(){

        System.out.println("https://api.trello.com/1/cards/"+listId1);
        given()
                .headers(headers)
                .queryParams(queryParams)
                .queryParams("name", "Updated card name for To Do list")
                .put("https://api.trello.com/1/cards/"+list1Card)
                .then()
                .log()
                .all()
                .statusCode(200);
        given()
                .headers(headers)
                .queryParams(queryParams)
                .queryParams("name", "Updated card name for Doing list")
                .put("https://api.trello.com/1/cards/"+list2Card)
                .then()
                .statusCode(200);
        given()
                .headers(headers)
                .queryParams(queryParams)
                .queryParams("name", "Updated card name for Done List")
                .put("https://api.trello.com/1/cards/"+list3Card)
                .then()
                .statusCode(200);
    }

//    @Test(dependsOnMethods = "createCard")
//    public void moveCardToDoing(){
//        given()
//                .headers(headers)
//                .queryParams(queryParams)
//                .queryParams("name", "Updated card name for To Do list")
//                .put("https://api.trello.com/1/cards/"+list1Card)
//                .then()
//                .log()
//                .all()
//                .statusCode(200);
//        given()
//                .headers(headers)
//                .queryParams(queryParams)
//                .queryParams("name", "Updated card name for Doing list")
//                .put("https://api.trello.com/1/cards/"+list2Card)
//                .then()
//                .statusCode(200);
//        given()
//                .headers(headers)
//                .queryParams(queryParams)
//                .queryParams("name", "Updated card name for Done List")
//                .put("https://api.trello.com/1/cards/"+list3Card)
//                .then()
//                .statusCode(200);
//    }

//    @AfterClass(alwaysRun = true)
//    public void deleteBoard(){
//
//        given().queryParams(queryParams)
//                .delete(baseURI+"boards/"+boardId)
//                .then()
//                .statusCode(200);
//    }
}
