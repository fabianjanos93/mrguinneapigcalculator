package com.codecool.mrguinneapig.calculator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.POST })
@RestController
public class CalculatorController {

    @Autowired
    Calculator calculator;

    @PostMapping("/calculate")
    public Calculator calculator(@RequestBody String body) throws ParseException {

        JSONParser parser = new JSONParser();
        JSONObject request = (JSONObject) parser.parse(body);

        calculator.setEquation((String) request.get("name"));
        calculator.solve();

        return calculator;
    }
}
