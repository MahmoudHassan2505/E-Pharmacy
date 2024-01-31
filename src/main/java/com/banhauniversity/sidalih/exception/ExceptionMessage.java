package com.banhauniversity.sidalih.exception;

public enum ExceptionMessage {
    ID_Not_Found("Element not found with this id"),
    ID_is_Exist("id is Exist id"),
    Invalid_Credential("Invalid Credential"),
    Patient_Exceeded_Price_Limit("patient exceeded 350L.E"),
    Patient_Exceeded_Useage_Limit("patient exceeded Useage Limit"),
    Negative_Number("amount is negative"),
    CANNOT_CREATE_USER("can't create User"),
    Not_Enough_Amount("not Enough amount");

    String message;
    ExceptionMessage(String message){
        this.message=message;
    }
}
