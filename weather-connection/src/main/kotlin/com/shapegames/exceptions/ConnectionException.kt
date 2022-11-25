package com.shapegames.exceptions

class ConnectionException (desc: String) :
    Exception("Something is wrong with the connection: $desc") {
}