package com.wcajisaca.accountService.mapper;

import com.wcajisaca.accountService.dtos.MovementsDTO;
import com.wcajisaca.accountService.entities.Movements;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MovementMapper {
    MovementMapper INSTANCE = Mappers.getMapper(MovementMapper.class);

    MovementsDTO toMovementsDTO(Movements movements);

    Movements toMovements(MovementsDTO movementsDTO);
}
