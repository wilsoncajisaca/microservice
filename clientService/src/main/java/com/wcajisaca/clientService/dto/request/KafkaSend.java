package com.wcajisaca.clientService.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class KafkaSend implements Serializable {
    private Integer id;
    private String observacion;
}
