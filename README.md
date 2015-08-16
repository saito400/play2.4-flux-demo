Demo application of play 2.4 and slick 3
=================================

# Setup

## install mysql

$ brew install mysql

$ mysql.server start

## create database

$ mysql -u root 

mysql> drop database playdemo; create database playdemo;

## How to update Slick table object

$ activator

[play2.4demo] $ runMain tools.Generator

# npms

## install npm modules
$ npm install

## build JavaScript files
$ npm build

## gulp watch
$ npm watch

# start server

$ activator run


