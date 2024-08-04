package com.wcajisaca.challenge.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class KafkaSend implements Serializable {
    private Integer id;
    private String observacion;
}
