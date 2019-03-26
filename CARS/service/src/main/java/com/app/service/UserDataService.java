package com.app.service;

import com.app.exceptions.ExceptionCode;
import com.app.exceptions.MyException;
import com.app.service.enums.SortType;

import java.math.BigDecimal;
import java.util.Scanner;

public class UserDataService {
    private Scanner scanner = new Scanner(System.in);

    public int getInt(String message) {
        System.out.println(message);
        String text = scanner.nextLine();
        if (!text.matches("\\d+")) {
            throw new MyException(ExceptionCode.USER_INPUT, "Int value is not valid: " + text);
        }
        return Integer.parseInt(text);
    }

    public SortType getSortType() {
        System.out.println("Enter sort type:");
        System.out.println("1 - model");
        System.out.println("2 - price");
        System.out.println("3 - color");
        System.out.println("4 - mileage");
        String text = scanner.nextLine();
        if (!text.matches("[1-4]")) {
            throw new MyException(ExceptionCode.USER_INPUT, "Sort type option number is not valid: " + text);
        }
        return SortType.values()[Integer.parseInt(text) - 1];
    }

    public boolean isOrderDescending() {
        System.out.println("Sort cars descending?");
        System.out.println("1 - yes");
        System.out.println("2 - no");
        String text = scanner.nextLine();
        if (!text.matches("[1-2]")) {
            throw new MyException(ExceptionCode.USER_INPUT, "Sort type option number is not valid: " + text);
        }
        return text.equals("1");
    }

    public Long getMileage() {
        System.out.println("Enter minimum mileage");
        String mileage = scanner.nextLine();
        if (!mileage.matches("\\d+")) {
            throw new MyException(ExceptionCode.USER_INPUT, "Mileage is not valid: " + mileage);
        }
        return Long.valueOf(mileage);
    }

    public BigDecimal getFirstPrice() {
        System.out.println("Enter minimum price:");
        String text = scanner.nextLine();
        if (!text.matches("\\d+")) {
            throw new MyException(ExceptionCode.USER_INPUT, "First price is not valid: " + text);
        }
        return BigDecimal.valueOf(Long.parseLong(text));
    }

    public BigDecimal getSecondPrice() {
        System.out.println("Enter maximum price:");
        String text = scanner.nextLine();
        if (!text.matches("\\d+")) {
            throw new MyException(ExceptionCode.USER_INPUT, "Second price is not valid: " + text);
        }
        return BigDecimal.valueOf(Long.parseLong(text));
    }

    public void close() {
        if (scanner != null) {
            scanner.close();
            scanner = null;
        }
    }
}
