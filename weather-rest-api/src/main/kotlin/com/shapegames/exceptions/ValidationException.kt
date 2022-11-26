package com.shapegames.exceptions

class ValidationException(desc: String) :
    Exception("Validation exception: $desc") {
}