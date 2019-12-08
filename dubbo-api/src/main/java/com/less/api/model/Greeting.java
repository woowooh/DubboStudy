package com.less.api.model;

import lombok.Data;
import java.io.Serializable;


@Data
public class Greeting implements Serializable {
    String message;
}
