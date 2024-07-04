package com.otunba.hng_proj1;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/api/hello")
public class Controller {

    private final GeoLocationService geoLocationService;

    public Controller(GeoLocationService geoLocationService) {
        this.geoLocationService = geoLocationService;
    }

    @GetMapping
    public ResponseEntity<?> greetings(@RequestParam(name= "visitor_name", defaultValue = "Anonymous") String visitor,
                                       @RequestHeader(value = "X-Forwarded-For", required = false) String forwardedFor,
                                       HttpServletRequest request){
        String ipAddress = forwardedFor == null ? request.getRemoteAddr() : forwardedFor.split(",")[0];
        if("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)){
            ipAddress = "8.8.8.8";
        }
        String finalIpAddress = ipAddress;
        return ResponseEntity.ok(new Object(){
           public String client_ip = finalIpAddress;
           public String location = geoLocationService.getGeoLocation(finalIpAddress);
           public String greeting = "Hello " + visitor + "! the temperature is 11 degree Celsius in " + location;
       });

    }
}
