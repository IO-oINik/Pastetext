package ru.edu.pasteservice.mappers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import ru.edu.pasteservice.models.DTOs.responses.PasteResponse;
import ru.edu.pasteservice.models.PasteMeta;

@RequiredArgsConstructor
@Component
public class PasteMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    public void customizationMapper() {
        TypeMap<PasteMeta, PasteResponse> propertyMapper = modelMapper.createTypeMap(PasteMeta.class, PasteResponse.class);
        propertyMapper.addMapping(src -> src.getOwner().getUsername(), PasteResponse::setAuthor);
    }

    public PasteResponse toResponse(PasteMeta pasteMeta) {
        return modelMapper.map(pasteMeta, PasteResponse.class);
    }
}
