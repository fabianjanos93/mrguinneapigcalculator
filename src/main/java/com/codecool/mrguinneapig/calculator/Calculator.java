package com.codecool.mrguinneapig.calculator;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class Calculator {

    private String equation;
    private boolean solvable = true;
    private float answer = 0;

    private String[] numbers = new String[]{"0","1","2","3","4","5","6","7","8","9"};
    private String[] operators = new String[]{"*","/","+","-"};

    public Calculator() {
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public float getAnswer() {
        return answer;
    }

    public boolean isSolvable() {
        return solvable;
    }

    public boolean isNumeric(String number) {
        int commaCounter = 0;
        ArrayList<String> temp = butcher(number);
        for(int i =0 ; i< temp.size();i++) {
            if ((!contains(temp.get(i),numbers) && !temp.get(i).equals("."))
                    || (commaCounter>0 && temp.get(i).equals("."))){
                return false; // nem szám és az első "."
            }
            else if (temp.get(i).equals(".")) {
                commaCounter++;
            }
        }
        return !(commaCounter == 1 && temp.size() == 1);
    }

    public void setEquation(String equation) {
        this.equation = equation.replace(" ","");
    }

    private void setSolvable(boolean solvable){
        this.solvable = solvable;
    }

    public float solve() {

        this.solvable = true;

        ArrayList<String> partEquation = butcher(equation);

        formalize(partEquation);

        if(braceletSolvable(partEquation)) {
            return solveRecStep(partEquation);
        }
        return 0;
    }

    public void formalize(ArrayList<String> toFormat) {
        for(int i = 1 ; i<toFormat.size(); i++) {
            if (toFormat.get(i).equals("(") && isNumeric(toFormat.get(i-1))) {
                toFormat.add(i,"*");
            }
        }
    }

    private ArrayList<String> butcher(String equation) {
        String[] arrayedEquation = equation.split("");
        ArrayList<String> partEquation = new ArrayList<>();
        partEquation.addAll(Arrays.asList(arrayedEquation));
        return partEquation;
    }

    public float solveRecStep(ArrayList<String> arrayedEquation) {
        int i=0;
        while(i<arrayedEquation.size()) {
            if( arrayedEquation.get(i).equals("(") ){
                int closingIndex = getClosingBraceletIndex(arrayedEquation,i);
                ArrayList<String> recursiveArrayList = new ArrayList<>();
                recursiveArrayList.addAll(arrayedEquation.subList(i+1,closingIndex));
                float replacement = solveRecStep(recursiveArrayList);
                int j = i;
                while(j <= closingIndex) {
                    arrayedEquation.remove(i);
                    j++;
                }
                arrayedEquation.add(i,Float.toString(replacement));
            }
            i++;
        }
        return solveSimple(arrayedEquation);
    }

    private boolean braceletSolvable(ArrayList<String> arrayedEquation){
        int braceletCounter = 0;
        for(String character: arrayedEquation) {
            if( character.equals("(")) {
                braceletCounter++;
            } else if(character.equals(")")) {
                braceletCounter--;
            }
            if (braceletCounter<0){
                solvable = false;
                return false;
            }
        }
        if (braceletCounter != 0){
            setSolvable(false);
        }
        return solvable;
    }

    private int getClosingBraceletIndex(ArrayList<String> arrayedEquation,int start){
        int i=start+1;
        int bracelets = 1;
        while (bracelets!=0) {
            if(arrayedEquation.get(i).equals(")")) {
                bracelets--;
            } else if(arrayedEquation.get(i).equals("(")) {
                bracelets++;
            }
            i++;
        }
        return --i;
    }

    public boolean solvable(){
        ArrayList<String> partEquation = butcher(equation);
        return solvable(partEquation) && braceletSolvable(partEquation);
    }

    public boolean solvable(ArrayList<String> butcheredEquation) {

        if(!braceletSolvable(butcheredEquation)){
            return false;
        }
            if (butcheredEquation.stream().allMatch(element -> isNumeric(element) || contains(element,operators))) {
            boolean ans = true;
            ans = !(contains(butcheredEquation.get(0),operators) || contains(butcheredEquation.get(butcheredEquation.size()-1),operators));
            for (int i = 1; i < butcheredEquation.size()-1; i++) {
                if(!ans)
                    break;
                if ( contains(butcheredEquation.get(i),operators)) {
                    ans = isNumeric(butcheredEquation.get(i-1)) && isNumeric(butcheredEquation.get(i+1));
                }
            }
            if (!ans)
                setSolvable(ans);
            return ans;
        }
        setSolvable(false);
        return false;
    }

    public boolean contains(String contained, String[] containing){
        for (String element: containing) {
            if(element.equals(contained)){
                return true;
            }
        }
        return false;
    }

    public float solveSimple(ArrayList<String> partEquation){
        if (!solvable(partEquation))
            return 0;

        fromDigitToNumber(partEquation);

        int ans = 0;
        for(String operator: operators) {
            int i = 1;
            while (i < partEquation.size()) {
                if (partEquation.get(i).equals(operator)) {
                    float previousNumber = Float.parseFloat(partEquation.get(i - 1));
                    float nextNumber = Float.parseFloat(partEquation.get(i + 1));
                    switch (operator) {
                        case "*":
                            partEquation.set(i, (Float.toString(previousNumber * nextNumber)));
                            break;
                        case "/":
                            partEquation.set(i, (Float.toString(previousNumber / nextNumber)));
                            break;
                        case "+":
                            partEquation.set(i, (Float.toString(previousNumber + nextNumber)));
                            break;
                        case "-":
                            partEquation.set(i, (Float.toString(previousNumber - nextNumber)));
                            break;
                    }
                    partEquation.remove(i + 1);
                    partEquation.remove(i - 1);
                } else {
                    i++;
                }
            }
        }
        answer = Float.parseFloat(partEquation.get(0));
        return answer;
    }

    public void fromDigitToNumber(ArrayList<String> butcheredEquation) {
        int i = 0;
        while(i+1 < butcheredEquation.size()){
            if(isNumeric(butcheredEquation.get(i))) {
                int j = i + 1;
                while ( i+1 < butcheredEquation.size()){
                    if (!isNumeric(butcheredEquation.get(j))) {
                        break;
                    }
                    butcheredEquation.set(i, butcheredEquation.get(i)+butcheredEquation.get(j));
                    butcheredEquation.remove(j);
                }
            }
            i++;
        }
    }
}
