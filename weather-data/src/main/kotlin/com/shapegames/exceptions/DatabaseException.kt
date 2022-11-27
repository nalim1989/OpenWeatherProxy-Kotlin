package com.shapegames.exceptions

class DatabaseException(desc: String) :
    Exception("Could not complete database action: $desc") {
}