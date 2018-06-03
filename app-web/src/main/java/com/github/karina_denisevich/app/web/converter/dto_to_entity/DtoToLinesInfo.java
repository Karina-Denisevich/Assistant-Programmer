package com.github.karina_denisevich.app.web.converter.dto_to_entity;

import com.github.karina_denisevich.app.datamodel.LinesInfo;
import com.github.karina_denisevich.app.web.dto.LinesInfoDto;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

public class DtoToLinesInfo implements Converter<LinesInfoDto, LinesInfo> {

    @Override
    public LinesInfo convert(final LinesInfoDto linesInfoDto) {
        LinesInfo linesInfo = new LinesInfo();

        linesInfo.setId(linesInfoDto.getId());
        linesInfo.setFilePath(linesInfoDto.getFilePath());
        linesInfo.setFirstLine(linesInfoDto.getFirstLine());
        linesInfo.setLastLine(linesInfoDto.getLastLine());
        linesInfo.setText(linesInfoDto.getText());
        linesInfo.setCreatedAt(new Date());
        linesInfo.setUpdatedAt(new Date());

        return linesInfo;
    }
}