package com.shapegames.validation

import com.shapegames.exceptions.ValidationException
import com.shapegames.model.TemperatureUnits


fun validateIntegerParam(param:String?, paramName:String):Int{
    param?.let {
        try {
            return it.toInt()
        } catch (e:Exception){
            throw ValidationException("Wrong data type $paramName -> Integer expected")
        }
    } ?: throw ValidationException("Data type $paramName is not set")
}

fun validateFloatParam(param:String?, paramName:String):Float{
    param?.let {
        try {
            return it.toFloat()
        } catch (e:Exception){
            throw ValidationException("Wrong data type $paramName -> Float expected")
        }
    } ?: throw ValidationException("Data type $paramName is not set")
}

fun validateIntegerListParam(param:List<String>?, paramName:String):List<Int>{
    if(!param.isNullOrEmpty()){
        try {
            return param.map { it.toInt() }.toList()
        } catch (e:Exception){
            throw ValidationException("Wrong data type $paramName -> Integer expected")
        }
    } else {
        throw ValidationException("Expected at least one parameter set: $paramName")
    }
}

fun validateTemperatureUnitsParam(param:String?, paramName:String): TemperatureUnits {
    param?.let {
        try {
            return TemperatureUnits.valueOf(it.uppercase())
        } catch (e:Exception){
            throw ValidationException("Wrong data type $paramName -> Integer expected")
        }
    } ?: throw ValidationException("Data type $paramName is not set")
}
