# chicken-farm

## Summary

This code will run on a Raspberry PI in a chicken barn. There are scales under the laying boxes that detect weight changes and computes the event (like chicken in, chicken-out-left-egg etc). These events will be sent by Messenger.

## TODO List

### Epic v1: Chicken scale recognizes weight change and sends message about state (which chicken, an egg) via Messenger

* check logs; observer and service log twice
* write tests and mocks for mqtt client
* taring am Morgen
* implement calibrate
* make ChickenService.initBoxes() save for new Chickens
** Convert Chicken to Enum
** initBoxes anhand der ENUM Liste
* log messures to file or Prometheus


* (done) remove Tinkerforge from Dockerfile
* (done) daily tare in the morning am 5 oclock
* (done) more unit tests
* (done) message when chicken leaves without an egg
* (done) fix disabled test (happens because ScaleObserver shoots in between).
* (done) move constants to entities (box and chicken)
* (done) setting timezone (-Duser.timezone="Europe/Zurich")
* (done) using constants for box entities (in tests)
* (done) responses of restful serivces reworked
* (done) detect chicken from weight
* (done) Switch to Slack
* (done) tare for both boxes
* (done) maybe distinct discovey result
* (done) keep state of box to prevent multiple messages
* (done) using Lombok to log
* (done) respect both boxes in Schedule
* (done) ENV VAR for "enabled" and Schedule "fixedRate"
* (done) write tests and mock for scale service (testing)
* (done) get current weight and calculate the "state" (chicken, egg)
* (done) env values via application.yaml
* (done) integrate Threema and send messages
* (done) read data from scale and show by webservice (GET)
* (done) change discovery webservice to use DO instead of strings
* (done) create scale service that delivers current weight
* (done) give calibrate webservice operation for load cell
* (done) create structure with timer service pulling dummy scale service and show data via GET service
* (done) install current Docker image on Raspberry PI and start it initially
* (done) using Github Actions to automatic build and create an image
* (done) create webservice GET base


### Epic v2: advanced features
* algo for more precise weight results
* Integration test with whole process (testing)
* UI for all webservices
* persist chicken weight, box weight, uid-box-mapping to file
* integrate Prometheus
* more precise way to get current weight.
* event that second egg gets layed
