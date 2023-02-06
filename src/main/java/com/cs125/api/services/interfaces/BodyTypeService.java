package com.cs125.api.services.interfaces;

import com.cs125.api.entities.BodyType;
import com.cs125.api.models.NewBodyTypeDto;

import java.util.List;

public interface BodyTypeService {
    List<BodyType> getAllBodyTypes();
    void addBodyType(NewBodyTypeDto newBodyTypeDto);
}
