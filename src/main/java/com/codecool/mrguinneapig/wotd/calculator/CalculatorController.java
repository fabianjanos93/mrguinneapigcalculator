package com.codecool.mrguinneapig.wotd.calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
