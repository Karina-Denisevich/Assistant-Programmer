package com.github.karina_denisevich.app.web.converter.entity_to_dto;

import com.github.karina_denisevich.app.datamodel.LinesInfo;
import com.github.karina_denisevich.app.web.dto.LinesInfoDto;
import org.springframework.core.convert.converter.Converter;

public class LinesInfoToDto implements Converter<LinesInfo, LinesInfoDto> {

    @Override
    public LinesInfoDto convert(final LinesInfo linesInfo) {
        LinesInfoDto linesInfoDto = new LinesInfoDto();

        linesInfoDto.setFilePath(linesInfo.getFilePath());
        linesInfoDto.setFirstLine(linesInfo.getFirstLine());
        linesInfoDto.setLastLine(linesInfo.getLastLine());
        linesInfoDto.setText(linesInfo.getText());

        return linesInfoDto;
    }
}