package dev.thiagosouto.blockchain.domain.exception

class InternetNotAvailableException(exception: Exception) : Exception(exception)

class UnexpectedErrorException(exception: Exception) : Exception(exception)
