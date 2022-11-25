package com.shapegames.exceptions

class DataLoadException(desc: String) :
    Exception("Not able to load data: $desc") {
}