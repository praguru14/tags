package com.tag.backend.model;

import lombok.*;
import netscape.javascript.JSObject;

@Getter
@Setter
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LocationPayload {
    private String latitude;
    private String longitude;

}
