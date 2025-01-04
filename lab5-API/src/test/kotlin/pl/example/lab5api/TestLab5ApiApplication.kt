package pl.example.lab5api

import org.springframework.boot.fromApplication


fun main(args: Array<String>) {
    fromApplication<Lab5ApiApplication>().run(*args)
}
