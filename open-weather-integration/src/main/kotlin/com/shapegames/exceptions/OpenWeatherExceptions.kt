package com.shapegames.exceptions

class OpenWeatherConnectionException (e:java.lang.Exception) :
    Exception("Open Weather connection problem", e) {
}

class OpenWeatherResponseException :
    Exception("Can not resolve response") {
}