package com.codecool.mrguinneapig.calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.POST })
@RestController
public class CalculatorController {

    @Autowired
    Calculator calculator;

    @GetMapping("/calculate")
    public Calculator calculator(@RequestParam("equation") String equation) {

        calculator.setEquation(equation);
        calculator.solve();

        return calculator;
    }
}
