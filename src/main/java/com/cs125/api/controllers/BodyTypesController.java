package com.cs125.api.controllers;

import com.cs125.api.entities.BodyType;
import com.cs125.api.models.NewBodyTypeDto;
import com.cs125.api.services.interfaces.BodyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;
import java.util.List;

@RestController
@RequestMapping("/body-types")
public class BodyTypesController {

    @Autowired
    BodyTypeService bodyTypeService;

    @GetMapping
    public Response getAllBodyTypes() {
        List<BodyType> bodyTypes = bodyTypeService.getAllBodyTypes();
        return Response.ok(bodyTypes).build();
    }

    @PostMapping
    public Response addNewBodyType(@RequestBody NewBodyTypeDto newBodyTypeDto) {
        bodyTypeService.addBodyType(newBodyTypeDto);
        return Response.ok().build();
    }
}
