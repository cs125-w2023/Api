package com.cs125.api.services;

import com.cs125.api.entities.BodyType;
import com.cs125.api.models.NewBodyTypeDto;
import com.cs125.api.repositories.BodyTypeRepository;
import com.cs125.api.services.interfaces.BodyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BodyTypeServiceImpl implements BodyTypeService {
    @Autowired
    BodyTypeRepository bodyTypeRepository;

    public List<BodyType> getAllBodyTypes() {
        return bodyTypeRepository.findAll();
    }

    public void addBodyType(NewBodyTypeDto newBodyTypeDto) {

        // save new type
        BodyType newBodyType = new BodyType();
        newBodyType.setType(newBodyTypeDto.getType());
        bodyTypeRepository.save(newBodyType);
    }
}
