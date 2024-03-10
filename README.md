# chicken-farm

## Summary

This code will run on a Raspberry PI in a chicken barn. There are scales under the laying boxes that detect weight changes and computes the event (like chicken in, chicken-out-left-egg etc). These events will be sent by Messenger.

## TODO List

### Epic v1: Chicken scale recognizes weight change and sends message about state (which chicken, an egg) via Messenger

* Story: check logs, observer and service log twice

* Story: (done) daily tare in the morning am 5 oclock
* Story: (done) more unit tests
* Story: (done) message when chicken leaves without an egg
* Story: (done) fix disabled test (happens because ScaleObserver shoots in between).
* Story: (done) move constants to entities (box and chicken)
* Story: (done) setting timezone (-Duser.timezone="Europe/Zurich")
* Story: (done) using constants for box entities (in tests)
* Story: (done) responses of restful serivces reworked
* Story: (done) detect chicken from weight
* Story: (done) Switch to Slack
* Story: (done) tare for both boxes
* Story: (done) maybe distinct discovey result
* Story: (done) keep state of box to prevent multiple messages
* Story: (done) using Lombok to log
* Story: (done) respect both boxes in Schedule
* Story: (done) ENV VAR for "enabled" and Schedule "fixedRate"
* Story: (done) write tests and mock for scale service (testing)
* Story: (done) get current weight and calculate the "state" (chicken, egg)
* Story: (done) env values via application.yaml
* Story: (done) integrate Threema and send messages
* Story: (done) read data from scale and show by webservice (GET)
* Story: (done) change discovery webservice to use DO instead of strings
* Story: (done) create scale service that delivers current weight
* Story: (done) give calibrate webservice operation for load cell
* Story: (done) create structure with timer service pulling dummy scale service and show data via GET service
* Story: (done) install current Docker image on Raspberry PI and start it initially
* Story: (done) using Github Actions to automatic build and create an image
* Story: (done) create webservice GET base


### Epic v2: advanced features
* Story: algo for more precise weight results
* Story: Integration test with whole process (testing)
* Story: UI for all webservices
* Story: persist chicken weight, box weight, uid-box-mapping to file
* Story: integrate Prometheus
* Story: more precise way to get current weight.
* Story: event that second egg gets layed
