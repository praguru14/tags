package com.tag.backend.model;

import lombok.*;

@Getter
@Setter
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Ip2LocationResponse {
    private String ip;
    private String country_code;
    private String country_name;
    private String region_name;
    private String city_name;
    private double latitude;
    private double longitude;
    private String zip_code;
    private String time_zone;
    private String asn;
    private String as;
    private boolean is_proxy;

}
