package com.thirdpartyapi.thirdpartyapi.controller;

import com.thirdpartyapi.thirdpartyapi.entity.CustomMessage;
import com.thirdpartyapi.thirdpartyapi.responseModal.Response;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @PostMapping("/externalUrl")
    public Response test(@RequestBody CustomMessage customMessage) {

        System.out.println("calling third api with message "+customMessage.getMessage());
        Response response=new Response();
        JSONObject jsonObject=new JSONObject(customMessage);
        System.out.println(jsonObject);
        if(jsonObject.optString("message").equals("hey")) {
            System.out.println("Success");
            response.setResponseCode("200");
            response.setResponseMessage("Correct msg");
            response.setResponseData(new JSONObject(customMessage).toMap());
            return response;
        }
        else {
            System.out.println("Failed");
            response.setResponseCode("401");
            response.setResponseMessage("Wrong msg");
            response.setResponseData(new JSONObject(customMessage).toMap());
            return response;
        }

    }

}
