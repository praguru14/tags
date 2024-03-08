package com.tag.backend.services;

import com.tag.backend.model.Ip2LocationResponse;
import com.tag.backend.utils.UtilsFile;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class LocationService {

    @Autowired
    private RestTemplate restTemplate;

    public String getClientIp(){
         if (RequestContextHolder.getRequestAttributes() == null) {
             return "0.0.0.0";
         }

         HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
         for (String header : UtilsFile.IP_HEADER_CANDIDATES) {
             String ipList = request.getHeader(header);
             if (ipList != null && ipList.length() != 0 && !"unknown".equalsIgnoreCase(ipList)) {
                 String ip = ipList.split(",")[0];
                 return "Ip is :" + ip;
             }
         }
         String ip = request.getRemoteAddr();
         if (ip.equals("0:0:0:0:0:0:0:1")) ip = "127.0.0.1";
         return ip;
     }


     public Ip2LocationResponse ipData(String ip){
        String url = "https://api.ip2location.io/?key=D69BB3EB12A8F7258925E5BFBB37C150&ip="+ip;
         return restTemplate.getForObject(url, Ip2LocationResponse.class);
     }
}
