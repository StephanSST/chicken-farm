# chicken-farm

## Summary

This code will run on a Raspberry PI in a chicken barn. There are scales under the laying boxes that detect weight changes and computes the event (like chicken in, chicken-out-left-egg etc). These events will be sent by Whatsapp.

## TODO List

### Epic v1: Chicken scale recognizes weight change and sends message about state (which chicken, an egg) via Whatsapp

* Story: write tests and mock for scale service
* Story: respect box/UID of load cell
* Story: Integration test with whole process

* Story: get current weight and calculate the "state" (chicken, egg)
* Story: integrate Whatsapp and send messages
* Story: read data from scale and show by webservice (GET)


* Story: (done) change discovery webservice to use DO instead of strings
* Story: (done) create scale service that delivers current weight
* Story: (done) give calibrate webservice operation for load cell
* Story: (done) create structure with timer service pulling dummy scale service and show data via GET service
* Story: (done) install current Docker image on Raspberry PI and start it initially
* Story: (done) using Github Actions to automatic build and create an image
* Story: (done) create Webservice GET base


### Epic v2: Recognize chicken
* Story: manage chickens
* Story: integrate Prometheus
