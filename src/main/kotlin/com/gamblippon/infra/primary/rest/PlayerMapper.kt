package com.gamblippon.infra.primary.rest

import com.gamblippon.domain.model.Player

class PlayerMapper {

    companion object {
        fun Player.toDto() : PlayerDto = PlayerDto(nickname, id)
        fun PlayerDto.toDomain(): Player = Player(nickname, id)
    }
}