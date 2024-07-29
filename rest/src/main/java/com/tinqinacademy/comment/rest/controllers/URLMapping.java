package com.tinqinacademy.comment.rest.controllers;

public class URLMapping {
    //Hotel
    public final static String GET_COMMENTS = "/api/hotel/{roomId}/comment";
    public final static String PUBLISH_COMMENT = "/api/hotel/{roomId}/comment";
    public final static String CONTENT_UPDATE_COMMENT = "/api/hotel/comment/{commentId}";

    //System
    public final static String ADMIN_UPDATE_COMMENT = "/api/system/comment/{commentId}";
    public final static String DELETE_COMMENT = "/api/system/comment/{commentId}";
}
